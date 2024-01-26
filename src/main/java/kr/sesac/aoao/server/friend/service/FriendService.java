package kr.sesac.aoao.server.friend.service;

import org.springframework.stereotype.Service;

import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryResponse;
import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddAcceptRequest;
import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddDenyRequest;
import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddRequest;
import kr.sesac.aoao.server.friend.controller.dto.response.GetFriendResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

@Service
public interface FriendService {
	GetDiaryResponse getDiaryInfo(UserCustomDetails userDetails);

	GetFriendResponse getFriendInfo(UserCustomDetails userDetails);

	void addFriend(Long id, Long friendId, FriendAddRequest request);

	void addFriendCancel(Long id, Long diaryId);

	void addFriendAccept(Long id, Long diaryId, FriendAddAcceptRequest request);

	void addFriendDeny(Long id, Long diaryId, FriendAddDenyRequest request);

	void deleteFriend(Long id, Long diaryId);
}
