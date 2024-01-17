package kr.sesac.aoao.server.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String message;

    public static ExceptionResponse from(String e) {
        return new ExceptionResponse(e);
    }
}
