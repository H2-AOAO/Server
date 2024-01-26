package kr.sesac.aoao.server.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.item.controller.dto.request.ItemNumRequest;
import kr.sesac.aoao.server.item.controller.dto.response.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.response.UseItemNumResponse;
import kr.sesac.aoao.server.item.service.ItemService;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.22
 * @author 김은서
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	private final ItemService itemService;

	/**
	 * 아이템 정보 조회
	 * @since 2024.01.19
	 * @return ItemInfoResponse
	 * @author 김은서
	 */
	@GetMapping("/{itemId}")
	public ResponseEntity<ApplicationResponse<GetItemInfoResponse>> getItemInfo(@PathVariable Long itemId){
		GetItemInfoResponse itemInfoResponse = itemService.getItemInfo(itemId);
		return ResponseEntity.ok(ApplicationResponse.success(itemInfoResponse));
	}

	/**
	 * 아이템 사용 - 개수 조절
	 * @since 2024.01.22
	 * @return UseItemNumResponse
	 * @author 김은서
	 */
	@PostMapping("/num")
	public ResponseEntity<ApplicationResponse<UseItemNumResponse>> calItemNum(
		@AuthenticationPrincipal UserCustomDetails userDetails, @RequestBody ItemNumRequest useItem){
		UseItemNumResponse useItemNumResponse = itemService.calItemNum(userDetails,useItem);
		return ResponseEntity.ok(ApplicationResponse.success(useItemNumResponse));
	}
}
