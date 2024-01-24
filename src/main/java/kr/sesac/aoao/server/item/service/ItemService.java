package kr.sesac.aoao.server.item.service;

import kr.sesac.aoao.server.item.controller.dto.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.UseItemNumResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface ItemService {
	GetItemInfoResponse getItemInfo(Long id);

	UseItemNumResponse calItemNum(UserCustomDetails userDetails, Long itemId, String status);

}
