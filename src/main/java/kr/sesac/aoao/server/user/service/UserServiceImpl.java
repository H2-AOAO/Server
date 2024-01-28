package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import kr.sesac.aoao.server.item.repository.UserItemJpaRepository;
import kr.sesac.aoao.server.point.repository.PointEntity;
import kr.sesac.aoao.server.point.repository.PointJpaRepository;
import kr.sesac.aoao.server.todo.repository.TodoEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserPasswordUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.MyPageResponse;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileUpdateResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.repository.Profile;
import kr.sesac.aoao.server.user.repository.ProfileRepository;
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
	private final ProfileRepository profileRepository;
	private final UserItemJpaRepository userItemJpaRepository;
	private final StorageConnector s3Connector;
	private final StorageGenerator s3Generator;

	private final TodoFolderJpaRepository todoFolderJpaRepository;

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
		userEntity.saveUserItems(userItems);
		userJpaRepository.save(userEntity);

		userItems = userItemJpaRepository.findAllByUser(userEntity);
		for(UserItemEntity userItem : userItems){
			long item_id = userItem.getId() % 5 == 0 ? 5 : userItem.getId() % 5;
			ItemEntity item = itemJpaRepository.findNonNullById(item_id);
			userItem.changeItem(item);
		}
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
	 * @return MyPageResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@Override
	public MyPageResponse getProfile(String username, Long userId) {
		UserEntity userEntity = userJpaRepository.findByEmail(username)
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL));
		boolean myself = userEntity.getId().equals(userId);

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
		String currentMonthString = currentDate.format(formatter);

		List<TodoFolderEntity> todoFoldersAboutMonth = todoFolderJpaRepository.findByMonthAndUser(
			currentDate.getYear(), currentDate.getMonthValue(), userEntity);

		int monthSumTodo = 0;
		int sumTodo = 0;
		int todaySum = 0;
		int todayFinish = 0;

		for (TodoFolderEntity todoFolder : todoFoldersAboutMonth) {
			monthSumTodo += todoFolder.getTodos().size();

			if (todoFolder.getSelectedDate().isEqual(currentDate)) {
				todaySum += todoFolder.getTodos().size();
				todayFinish += (int) todoFolder.getTodos().stream().filter(TodoEntity::isChecked).count();
			}

			sumTodo += (int) todoFolder.getTodos().stream().filter(TodoEntity::isChecked).count();
		}

		String today = String.format("%d / %d", todayFinish, todaySum);

		String profileUrl = (userEntity.getProfile() != null) ? userEntity.getProfile().getResourceUrl() : "no_profile";
		return new MyPageResponse(
			profileUrl,
			userEntity.getNickname(),
			userEntity.getEmail(),
			myself,
			currentMonthString,
			monthSumTodo,
			sumTodo,
			today
		);
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
		Profile savedProfile = savedUser.getProfile();
		if (savedProfile != null) {
			profileRepository.deleteById(savedProfile.getId());
			s3Connector.delete(savedProfile.getResourceKey());
		}

		ResourceResponse response = s3Connector.save(newProfile, s3Generator.createPath(), s3Generator.createResourceName(newProfile));
		Profile profile = new Profile(response.getResourceKey(), response.getResourceUrl());
		savedUser.updateProfile(profile);
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
		Profile savedProfile = savedUser.getProfile();
		if (savedProfile == null) {
			return;
		}
		savedUser.initProfile();
		profileRepository.deleteById(savedProfile.getId());
		s3Connector.delete(savedProfile.getResourceKey());
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
