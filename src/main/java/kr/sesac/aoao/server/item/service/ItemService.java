package kr.sesac.aoao.server.item.service;

import kr.sesac.aoao.server.item.controller.dto.request.ItemNumRequest;
import kr.sesac.aoao.server.item.controller.dto.response.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.response.UseItemNumResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface ItemService {
	GetItemInfoResponse getItemInfo(GetItemInfoResponse useItemInfo);

	UseItemNumResponse calItemNum(UserCustomDetails userDetails, ItemNumRequest useItem);

}
