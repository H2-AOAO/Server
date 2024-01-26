package kr.sesac.aoao.server.user.service;

import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface UserService {

	User signUp(SignUpRequest signUpRequest);

	User login(LoginRequest loginRequest);

	UserProfileResponse getProfile(String username, Long userId);

	void duplicatedEmail(String email);

	void duplicationNickname(String nickname);

	void updateNickname(UserCustomDetails userDetails, UserNicknameUpdateRequest request);

	void deleteUser(Long userId);
}
