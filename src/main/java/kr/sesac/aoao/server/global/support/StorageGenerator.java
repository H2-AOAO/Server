package kr.sesac.aoao.server.global.support;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 김유빈
 * @since 2024.01.27
 */
public interface StorageGenerator {

    String createPath();

    String createResourceName(MultipartFile resource);
}
