package kr.sesac.aoao.server.diary.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryCreateRequest {
	private final String date;
	private final String content;
}