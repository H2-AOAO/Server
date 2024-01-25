package kr.sesac.aoao.server.diary.controller.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryCreateRequest {
	private final Long userId;
	private final LocalDateTime date;
	private final String content;
}
