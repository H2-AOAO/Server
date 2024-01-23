package kr.sesac.aoao.server.user.service;

import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.domain.User;

public interface UserService {

	User signUp(SignUpRequest signUpRequest);

	User login(LoginRequest loginRequest);
}
