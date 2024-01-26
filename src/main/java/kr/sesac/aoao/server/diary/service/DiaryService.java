package kr.sesac.aoao.server.diary.service;

import org.springframework.stereotype.Service;

import kr.sesac.aoao.server.diary.controller.dto.request.DiaryCreateRequest;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryUpdateRequest;
import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryResponse;

@Service
public interface DiaryService {

	GetDiaryResponse getDiaryInfo(Long userId, String date);

	Long createDiary(Long userId, DiaryCreateRequest request);

	void updateDiary(Long userId, Long diaryId, DiaryUpdateRequest request);

	void deleteDiary(Long userId, Long diaryId);

}