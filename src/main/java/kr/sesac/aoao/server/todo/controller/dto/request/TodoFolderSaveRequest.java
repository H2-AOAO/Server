package kr.sesac.aoao.server.todo.controller.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoFolderSaveRequest {

	private final String content;
	private final LocalDate date;
	private final Long paletteId;
}
