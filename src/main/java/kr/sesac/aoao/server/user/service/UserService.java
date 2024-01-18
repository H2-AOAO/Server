package kr.sesac.aoao.server.user.service;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;

public interface UserService {

	Long signUp(SignUpRequest signUpRequest) throws Exception;

}
