package kr.sesac.aoao.server.global.support.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResourceResponse {

    private final String resourceKey;

    private final String resourceUrl;
}
