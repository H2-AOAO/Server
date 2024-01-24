package kr.sesac.aoao.server.diary.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 2024.01.23
 * @author 최정윤
 */
@Getter
@AllArgsConstructor
public class Diary {
	private final Long diaryId; // 일기 id
	private final String content; // 일기 내용
	private final LocalDateTime date; // 캘린더 설정 날짜
	private final Long userId; // 일기 작성자 id
	private final LocalDateTime createdAt; // 일기 생성 일자
	private final LocalDateTime updateAt; // 일기 수정 일자

}
