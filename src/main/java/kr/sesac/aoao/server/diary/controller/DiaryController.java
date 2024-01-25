package kr.sesac.aoao.server.diary.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ApplicationResponse<Void>> createDiary(@RequestParam Long userId, @PathVariable Long date, @RequestBody DiaryCreateRequest request) {
		Long diaryId = diaryService.createDiary(userId, date, request);
		return ResponseEntity.created(URI.create("/diary/" + diaryId)).build();
	}

	/**
	 * 다이어리 정보 조회
	 * @since 2024.01.23
	 * @return getDiaryInfo
	 * @author 최정윤
	 * 완료
	 */
	@GetMapping("/")
	public ResponseEntity<ApplicationResponse<GetDiaryResponse>> getDiaryInfo(@RequestParam Long userId) {
		GetDiaryResponse userDiaryResponse = diaryService.getDiaryInfo(userId);
		return ResponseEntity.ok(ApplicationResponse.success(userDiaryResponse));
	}

	/**
	 * 다이어리 수정
	 * @since 2024.01.23
	 * @return updateDiary
	 * @author 최정윤
	 */
	@PostMapping("/{diaryId}")
	public ResponseEntity<ApplicationResponse<Void>> updateDiary(
		@RequestParam Long userId, @PathVariable Long diaryId,
		@RequestBody DiaryUpdateRequest request) {
		diaryService.updateDiary(userId, diaryId, request);
		return ResponseEntity.ok().build();
	}

	/**
	 * 다이어리 삭제
	 * @since 2024.01.23
	 * @return deleteDiary
	 * @author 최정윤
	 */
	@DeleteMapping("/{diaryId}")
	public ResponseEntity<ApplicationResponse<Void>> deleteDiary(
		@RequestParam Long userId, @PathVariable Long diaryId) {
		diaryService.deleteDiary(userId, diaryId);
		return ResponseEntity.noContent().build();
	}

}
