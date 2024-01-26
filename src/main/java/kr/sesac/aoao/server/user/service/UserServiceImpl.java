package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
import kr.sesac.aoao.server.item.repository.UserItemEntity;
import kr.sesac.aoao.server.point.repository.PointEntity;
import kr.sesac.aoao.server.point.repository.PointJpaRepository;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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

	private final PointJpaRepository pointJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final ItemJpaRepository itemJpaRepository;

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
		for (int i = 1; i <= 5; i++) {
			ItemEntity itemEntity = itemJpaRepository.findById((long) i).orElse(null);
			UserItemEntity userItem = new UserItemEntity(userEntity, itemEntity, 0);
			userItems.add(userItem);
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
		if (!passwordEncoder.matches(request.getPassword(), savedUser.getPassword())) {
			throw new ApplicationException(UserErrorCode.INVALID_PASSWORD);
		}
		if (userJpaRepository.existsByNickname(request.getNickname())) {
			throw new ApplicationException(EXISTENT_NICKNAME);
		}
		savedUser.updateNickname(request.getNickname());
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
}
