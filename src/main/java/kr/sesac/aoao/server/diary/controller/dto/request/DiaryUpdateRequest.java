package kr.sesac.aoao.server.diary.controller.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryUpdateRequest {
	private final Long userId;
	private final LocalDateTime date;
	private final String content;
}