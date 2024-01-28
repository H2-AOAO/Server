package kr.sesac.aoao.server.user.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageResponse {
	private final String profile;
	private final String nickname;
	private final String email;
	private final boolean myself;

	// 투두
	private final String month; // 현재 달
	private final int monthSumTodo; // 이번달 총 투두
	private final int sumTodo; // 이번달 투두 완료
	private final String today; // 오늘 정보
}
