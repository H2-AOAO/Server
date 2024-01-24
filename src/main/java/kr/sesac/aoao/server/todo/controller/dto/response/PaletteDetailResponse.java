package kr.sesac.aoao.server.todo.controller.dto.response;

import kr.sesac.aoao.server.todo.repository.PaletteEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaletteDetailResponse {

    private final long paletteId;
    private final String colorCode;

    public static PaletteDetailResponse from(PaletteEntity palette) {
        return new PaletteDetailResponse(
            palette.getId(),
            palette.getColorCode()
        );
    }
}
