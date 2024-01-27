package kr.sesac.aoao.server.user.service;

import org.springframework.web.multipart.MultipartFile;

import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserPasswordUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.MyPageResponse;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileUpdateResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface UserService {

	User signUp(SignUpRequest signUpRequest);

	User login(LoginRequest loginRequest);

	MyPageResponse getProfile(String username, Long userId);

	void duplicatedEmail(String email);

	void duplicationNickname(String nickname);

	void updateNickname(UserCustomDetails userDetails, UserNicknameUpdateRequest request);

	void updatePassword(UserCustomDetails userDetails, UserPasswordUpdateRequest request);

	UserProfileUpdateResponse updateProfile(UserCustomDetails userDetails, MultipartFile newProfile);

	void initProfile(UserCustomDetails userDetails);

	void deleteUser(Long userId);
}
