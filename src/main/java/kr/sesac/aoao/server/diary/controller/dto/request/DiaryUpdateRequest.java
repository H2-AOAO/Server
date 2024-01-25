package kr.sesac.aoao.server.diary.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryUpdateRequest {
	private final String content;
	private final boolean flag;
}