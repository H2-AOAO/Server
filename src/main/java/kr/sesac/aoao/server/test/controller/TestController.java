package kr.sesac.aoao.server.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;

@RequestMapping("/test")
@RestController
public class TestController {

	public String test() {
		return "Hello world";
	}

	@GetMapping("/response")
	public ResponseEntity<ApplicationResponse<String>> testApplicationResponse() {
		return ResponseEntity.ok(ApplicationResponse.success("data"));
	}
}
