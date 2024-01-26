package kr.sesac.aoao.server.friend.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 2024.01.25
 * @author 최정윤
 */
@Getter
@AllArgsConstructor
public class Friend {
	private final Long myFriend; // 친구 목록 아이디
	private final Long userId; // 유저 아이디
	private final Long FriendId; // 친구 유저 아이디
	private final LocalDateTime createdAt; // 일기 생성 일자
	private final LocalDateTime updateAt; // 일기 수정 일자
}
