package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.global.support.StorageConnector;
import kr.sesac.aoao.server.global.support.StorageGenerator;
import kr.sesac.aoao.server.global.support.dto.response.ResourceResponse;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
import kr.sesac.aoao.server.item.repository.UserItemEntity;
import kr.sesac.aoao.server.point.repository.PointEntity;
import kr.sesac.aoao.server.point.repository.PointJpaRepository;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserPasswordUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileUpdateResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.repository.Resource;
import kr.sesac.aoao.server.user.repository.ResourceRepository;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.01.18
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

	private static final List<String> CONTENT_TYPES_OF_IMAGE = List.of(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE);

	private final PointJpaRepository pointJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final ItemJpaRepository itemJpaRepository;
	private final ResourceRepository resourceRepository;

	private final StorageConnector s3Connector;
	private final StorageGenerator s3Generator;

	/**
	 * 회원가입
	 *
	 * @return User
	 * @author 이상민
	 * @since 2024.01.18
	 */
	@Override
	public User signUp(SignUpRequest signUpRequest) {
		// signUpRequest -> user
		User user = new User(signUpRequest);
		duplicatedEmail(user.getEmail()); // 이메일 중복확인
		duplicationNickname(user.getNickname()); // 넥네임 중복확인
		if (!user.getPassword().equals(user.getCheckedPassword())) {  // 비밀번호 중복확인
			throw new ApplicationException(INVALID_PASSWORD);
		}

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.encodePassword(encodePassword);

		// 아이템 추가
		UserEntity userEntity = userJpaRepository.save(new UserEntity(user));
		pointJpaRepository.save(new PointEntity(userEntity));
		/**
		 * 사용자 아이템 객체 추가
		 * @author 김은서
		 * @since 2024.01.26
		 */
		List<UserItemEntity> userItems = new ArrayList<>();
		List<ItemEntity> itemEntityList = itemJpaRepository.findAll();
		for (ItemEntity item : itemEntityList) {
			UserItemEntity userItem = new UserItemEntity(userEntity, item, 0);
			userItems.add(userItem);
		}
		// for (int i = 1; i <= 5; i++) {
		// 	//ItemEntity itemEntity = itemJpaRepository.findById((long) i).orElse(null);
		// 	UserItemEntity userItem = new UserItemEntity(userEntity, itemEntityList.get(i), 0);
		// 	userItems.add(userItem);
		// }
		userEntity.saveUserItems(userItems);
		userJpaRepository.save(userEntity);


		return userEntity.toModel();
	}

	/**
	 * 로그인
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.19
	 */
	@Override
	@Transactional(readOnly = true)
	public User login(LoginRequest loginRequest) {
		User user = userJpaRepository.findByEmail(loginRequest.getEmail())
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL)).toModel();
		if (!user.checkPassword(passwordEncoder, loginRequest.getPassword())) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
		return user;
	}

	/**
	 * 프로필 조회
	 *
	 * @return UserProfileResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@Override
	public UserProfileResponse getProfile(String username, Long userId) {
		User user = userJpaRepository.findByEmail(username)
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL)).toModel();
		return new UserProfileResponse(user.getNickname(), user.getProfile());
	}

	@Override
	public void duplicatedEmail(String email) {
		Optional<UserEntity> user = userJpaRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new ApplicationException(EXISTENT_EMAIL);
		}
	}

	@Override
	public void duplicationNickname(String nickname) {
		Optional<UserEntity> user = userJpaRepository.findByNickname(nickname);
		if (user.isPresent()) {
			throw new ApplicationException(EXISTENT_NICKNAME);
		}
	}

	/**
	 * 닉네임 수정
	 * @since 2024.01.26
	 * @parameter UserCustomDetails, UserNicknameUpdateRequest
	 * @author 김유빈
	 */
	@Override
	public void updateNickname(UserCustomDetails userDetails, UserNicknameUpdateRequest request) {
		UserEntity savedUser = findUserById(userDetails.getUserEntity().getId());
		validatePasswordIsMatched(request.getPassword(), savedUser.getPassword());
		if (userJpaRepository.existsByNickname(request.getNickname())) {
			throw new ApplicationException(EXISTENT_NICKNAME);
		}
		savedUser.updateNickname(request.getNickname());
	}

	/**
	 * 비밀번호 수정
	 * @since 2024.01.26
	 * @parameter UserCustomDetails, UserPasswordUpdateRequest
	 * @author 김유빈
	 */
	@Override
	public void updatePassword(UserCustomDetails userDetails, UserPasswordUpdateRequest request) {
		UserEntity savedUser = findUserById(userDetails.getUserEntity().getId());
		validatePasswordIsMatched(request.getPassword(), savedUser.getPassword());
		if (!request.getNewPassword().equals(request.getCheckedPassword())) {
			throw new ApplicationException(INVALID_PASSWORD);
		}
		savedUser.updatePassword(passwordEncoder.encode(request.getNewPassword()));
	}

	/**
	 * 프로필 수정
	 * @since 2024.01.27
	 * @parameter UserCustomDetails, MultipartFile
	 * @author 김유빈
	 */
	@Override
	public UserProfileUpdateResponse updateProfile(UserCustomDetails userDetails, MultipartFile newProfile) {
		validateProfileIsImage(newProfile);

		UserEntity savedUser = findUserById(userDetails.getUserEntity().getId());
		Resource savedResource = savedUser.getResource();
		if (savedResource != null) {
			resourceRepository.deleteById(savedResource.getId());
			s3Connector.delete(savedResource.getResourceKey());
		}

		ResourceResponse response = s3Connector.save(newProfile, s3Generator.createPath(), s3Generator.createResourceName(newProfile));
		Resource resource = new Resource(response.getResourceKey(), response.getResourceUrl());
		savedUser.updateProfile(resource);
		return new UserProfileUpdateResponse(response.getResourceUrl());
	}

	/**
	 * 프로필 초기화
	 * @since 2024.01.27
	 * @parameter MultipartFile
	 * @author 김유빈
	 */
	@Override
	public void initProfile(UserCustomDetails userDetails) {
		UserEntity savedUser = findUserById(userDetails.getUserEntity().getId());
		Resource savedResource = savedUser.getResource();
		if (savedResource == null) {
			return;
		}
		savedUser.initProfile();
		resourceRepository.deleteById(savedResource.getId());
		s3Connector.delete(savedResource.getResourceKey());
	}

	@Override
	public void deleteUser(Long userId) {
		User user = findUserById(userId).toModel();
		userJpaRepository.delete(new UserEntity(user));
	}

	private UserEntity findUserById(Long userId) {
		return userJpaRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(NOT_EXIST));
	}

	private void validatePasswordIsMatched(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
	}

	private void validateProfileIsImage(MultipartFile file) {
		final String contentType = file.getContentType();
		if (!CONTENT_TYPES_OF_IMAGE.contains(contentType)) {
			throw new IllegalArgumentException(String.format("프로필은 PNG, JPG 형식만 가능합니다. [%s]", contentType));
		}
	}
}
