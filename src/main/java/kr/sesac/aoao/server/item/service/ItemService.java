package kr.sesac.aoao.server.item.service;

import kr.sesac.aoao.server.item.controller.dto.request.ItemNumRequest;
import kr.sesac.aoao.server.item.controller.dto.response.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.response.UseItemNumResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
/**
 * @since 2024.01.22
 * @author 김은서
 */
public interface ItemService {
	GetItemInfoResponse getItemInfo(Long itemId);

	UseItemNumResponse calItemNum(UserCustomDetails userDetails, ItemNumRequest useItem);

}
