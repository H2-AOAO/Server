package kr.sesac.aoao.server.global.controller.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApplicationResponse<T> {

    private final boolean success;
    private final String message;
    private final T date;

    public static <T> ApplicationResponse<T> success(String message, T data) {
        return new ApplicationResponse<>(true, message, data);
    }

    public static <T> ApplicationResponse<T> fail(String message, T data) {
        return new ApplicationResponse<>(false, message, data);
    }
}
