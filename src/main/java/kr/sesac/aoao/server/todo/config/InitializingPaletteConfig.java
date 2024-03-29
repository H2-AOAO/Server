package kr.sesac.aoao.server.todo.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import kr.sesac.aoao.server.todo.domain.Palette;
import kr.sesac.aoao.server.todo.repository.PaletteEntity;
import kr.sesac.aoao.server.todo.repository.PaletteJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.01.22
 */
@Configuration
@RequiredArgsConstructor
public class InitializingPaletteConfig implements ApplicationRunner {

	private final PaletteJpaRepository paletteJpaRepository;

	/**
	 * 어플리케이션 실행 시 팔레트 정보 초기화
	 *
	 * @parameter
	 * @author 김유빈
	 * @since 2024.01.22
	 */
	@Override
	public void run(ApplicationArguments args) {
		if (paletteJpaRepository.findAll().isEmpty()) {
			for (Palette palette : Palette.values()) {
				PaletteEntity paletteEntity = new PaletteEntity(palette.getColorCode());
				paletteJpaRepository.save(paletteEntity);
			}
		}
	}
}
