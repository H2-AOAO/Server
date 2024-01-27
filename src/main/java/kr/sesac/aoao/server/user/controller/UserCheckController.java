package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.DuplicatedEmailRequest;
import kr.sesac.aoao.server.user.controller.dto.request.DuplicatedNicknameRequest;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.01.24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/duplicated")
public class UserCheckController {

    private final UserService userService;

    /**
     * 이메일 중복확인 API
     *
     * @parameter DuplicatedEmailRequest
     * @return String
     * @author 이상민
     * @since 2024.01.22
     */
    @PostMapping("/email")
    public ResponseEntity<ApplicationResponse<String>> duplicationEmail(
        @Validated @RequestBody DuplicatedEmailRequest duplicatedEmailRequest) {
        userService.duplicatedEmail(duplicatedEmailRequest.getEmail());
        return ResponseEntity.ok(ApplicationResponse.success("사용가능한 이메일입니다."));
    }

    /**
     * 닉네임 중복확인 API
     *
     * @parameter DuplicatedNicknameRequest
     * @return String
     * @author 이상민
     * @since 2024.01.22
     */
    @PostMapping("/nickname")
    public ResponseEntity<ApplicationResponse<String>> duplicationNickname(
        @Valid @RequestBody DuplicatedNicknameRequest duplicatedNicknameRequest) {
        userService.duplicationNickname(duplicatedNicknameRequest.getNickname());
        return ResponseEntity.ok(ApplicationResponse.success("사용가능한 넥네임입니다."));
    }
}
