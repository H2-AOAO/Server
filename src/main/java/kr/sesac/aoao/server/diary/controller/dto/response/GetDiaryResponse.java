package kr.sesac.aoao.server.diary.controller.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetDiaryResponse {

	private final Long id;
	private final String content;
	private final LocalDateTime date;

}
