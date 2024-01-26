package kr.sesac.aoao.server.global.support;

import org.springframework.web.multipart.MultipartFile;

import kr.sesac.aoao.server.global.support.dto.response.ResourceResponse;

/**
 * @author 김유빈
 * @since 2024.01.27
 */
public interface StorageConnector {

    ResourceResponse save(MultipartFile resource, String path, String resourceName);

    void delete(String resourceKey);
}
