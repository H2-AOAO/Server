package kr.sesac.aoao.server.todo.controller.dto.response;

import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FolderDetailResponse {

    private final long folderId;
    private final String colorCode;
    private final String content;

    public static FolderDetailResponse from(TodoFolderEntity todoFolder) {
        return new FolderDetailResponse(
            todoFolder.getId(),
            todoFolder.getPalette().getColorCode(),
            todoFolder.getContent()
        );
    }
}
