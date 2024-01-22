package kr.sesac.aoao.server.item.service;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.item.controller.dto.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.UseItemNumResponse;

public interface ItemService {
	GetItemInfoResponse getItemInfo(Long id);

	UseItemNumResponse calItemNum(Long userId, Long itemId, String status);

}
