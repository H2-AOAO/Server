
package kr.sesac.aoao.server.diary.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.diary.controller.dto.request.DiaryCreateRequest;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryUpdateRequest;
import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryResponse;
import kr.sesac.aoao.server.diary.service.DiaryService;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 최정윤
 */
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;

	/**
	 * 다이어리 작성
	 * @since 2024.01.23
	 * @return createDiary
	 * @author 최정윤
	 */
	@PostMapping
	public ResponseEntity<ApplicationResponse<String>> createDiary(@AuthenticationPrincipal UserCustomDetails userDetails,
		@RequestBody DiaryCreateRequest request) {
		Long diaryId = diaryService.createDiary(userDetails.getUserEntity().getId(), request);
		return ResponseEntity.ok(ApplicationResponse.success(diaryId + "번 다이어리가 생성되었습니다."));

	}

	/**
	 * 다이어리 정보 조회
	 * @since 2024.01.23
	 * @return getDiaryInfo
	 * @author 최정윤
	 * 완료
	 */
	@GetMapping
	public ResponseEntity<ApplicationResponse<GetDiaryResponse>> getDiaryInfo(
		@AuthenticationPrincipal UserCustomDetails userDetails, @RequestParam("date") String date) {
		GetDiaryResponse userDiaryResponse = diaryService.getDiaryInfo(userDetails.getUserEntity().getId(), date);
		return ResponseEntity.ok(ApplicationResponse.success(userDiaryResponse));
	}

	/**
	 * 다이어리 수정
	 * @since 2024.01.23
	 * @return updateDiary
	 * @author 최정윤
	 */
	@PostMapping("/{diaryId}")
	public ResponseEntity<ApplicationResponse<String>> updateDiary(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@PathVariable Long diaryId,
		@RequestBody DiaryUpdateRequest request) {
		diaryService.updateDiary(userDetails.getUserEntity().getId(), diaryId, request);
		return ResponseEntity.ok(ApplicationResponse.success(diaryId + "번 다이어리가 수정되었습니다."));
	}

	/**
	 * 다이어리 삭제
	 * @since 2024.01.23
	 * @return deleteDiary
	 * @author 최정윤
	 */
	@DeleteMapping("/{diaryId}")
	public ResponseEntity<ApplicationResponse<String>> deleteDiary(
		@AuthenticationPrincipal UserCustomDetails userDetails, @PathVariable Long diaryId) {
		diaryService.deleteDiary(userDetails.getUserEntity().getId(), diaryId);
		return ResponseEntity.ok(ApplicationResponse.success(diaryId + "번 다이어리가 삭제되었습니다."));
	}

}