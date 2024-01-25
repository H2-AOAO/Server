package kr.sesac.aoao.server.diary.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryCreateRequest;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryUpdateRequest;
import kr.sesac.aoao.server.diary.controller.dto.response.GetDiaryResponse;
import kr.sesac.aoao.server.diary.exception.DiaryErrorCode;
import kr.sesac.aoao.server.diary.repository.DiaryEntity;
import kr.sesac.aoao.server.diary.repository.DiaryJpaRepository;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 최정윤
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService{

	private final DiaryJpaRepository diaryJpaRepository;
	private final UserJpaRepository userRepository;
	private final DiaryJpaRepository diaryRepository;

	private GetDiaryResponse result(DiaryEntity diary){
		return new GetDiaryResponse(
			diary.getUser().getId(),
			diary.getContent(),
			diary.getDate()
		);
	}

	/**
	 * 다이어리 작성
	 * @since 2024.01.23
	 * @return saveDiary
	 * @author 최정윤
	 */
	@Override
	public Long createDiary(Long userId, Long date, DiaryCreateRequest request) {
		UserEntity savedUser = findUserById(userId);

		DiaryEntity diaryEntity = new DiaryEntity(
			request.getContent(),
			request.getDate(),
			savedUser
		);
		return diaryJpaRepository.save(diaryEntity).getId();
	}

	/**
	 * 다이어리 조회
	 * @since 2024.01.123
	 * @parameter userId
	 * @return GetDiaryResponse
	 * @author 최정윤
	 */
	@Override
	public GetDiaryResponse getDiaryInfo(Long userId) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		DiaryEntity diary = diaryRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DiaryErrorCode.NO_DIARY));
		return result(diary);
	}

	/**
	 * 다이어리 수정
	 * @since 2024.01.23
	 * @return updateDiary
	 * @author 최정윤
	 */

	@Override
	public void updateDiary(Long userId, Long diaryId, DiaryUpdateRequest request) {
		UserEntity savedUser = findUserById(userId);
		DiaryEntity savedDiary = findDiaryById(diaryId);

		savedDiary.diaryUpdate(savedUser, request.getContent());
	}


	/**
	 * 다이어리 삭제
	 * @since 2024.01.23
	 * @return deleteDiary
	 * @author 최정윤
	 */
	@Override
	public void deleteDiary(Long userId, Long diaryId) {
		UserEntity savedUser = findUserById(userId);
		DiaryEntity savedDiary = findDiaryById(diaryId);

		savedDiary.validateUserIsWriter(savedUser);
		diaryJpaRepository.deleteById(savedDiary.getId());
	}

	private UserEntity findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
	}

	private DiaryEntity findDiaryById(Long diaryId) {
		return diaryRepository.findById(diaryId)
			.orElseThrow(() -> new ApplicationException(DiaryErrorCode.NO_DIARY));
	}

}
