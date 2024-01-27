package kr.sesac.aoao.server.diary.controller.dto.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetDiaryResponse {
	private final Long diaryId;
	private final String content;
	private final LocalDate date;

}