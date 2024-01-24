package kr.sesac.aoao.server.diary.service;

import org.springframework.stereotype.Service;

import kr.sesac.aoao.server.diary.controller.dto.request.DiaryCreateRequest;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryUpdateRequest;
import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryDetailResponse;
import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryResponse;

@Service
public interface DiaryService {

	GetDiaryResponse getDiaryInfo(Long userId);

	GetDiaryResponse getDiaryDetailInfo(Long userId);

	GetDiaryDetailResponse getDiaryDetail(Long diaryId);

	void diaryUpdate(Long userId, Long diaryId, DiaryUpdateRequest request);

	void deleteDiary(Long userId, Long diaryId);

	Long createDiary(Long date, DiaryCreateRequest request);
}
