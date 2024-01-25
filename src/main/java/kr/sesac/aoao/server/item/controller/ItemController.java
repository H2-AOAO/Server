package kr.sesac.aoao.server.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.item.controller.dto.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.UseItemNumResponse;
import kr.sesac.aoao.server.item.service.ItemService;
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
	@GetMapping("/")
	public ResponseEntity<ApplicationResponse<GetItemInfoResponse>> getItemInfo(Long id){
		GetItemInfoResponse itemInfoResponse = itemService.getItemInfo(id);
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
		@AuthenticationPrincipal UserCustomDetails userDetails, Long itemId, String status){
		UseItemNumResponse useItemNumResponse = itemService.calItemNum(userDetails,itemId, status);
		return ResponseEntity.ok(ApplicationResponse.success(useItemNumResponse));
	}
}
