package kr.sesac.aoao.server.diary.controller.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GetDiaryResponse {

	private final Long id;
	private final String content;
	private final LocalDateTime date;

	public GetDiaryResponse(Long id, String content, LocalDateTime date) {
		this.id = id;
		this.content = content;
		this.date = date;
	}
}
