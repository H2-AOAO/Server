package kr.sesac.aoao.server.user.service;

import java.util.Map;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.domain.User;

public interface UserService {

	User signUp(SignUpRequest signUpRequest) throws Exception;

	String login(Map<String, String> users);

}