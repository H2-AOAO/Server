package kr.sesac.aoao.server.global.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import kr.sesac.aoao.server.global.support.dto.response.ResourceResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.01.27
 */
@RequiredArgsConstructor
@Component
public class S3Connector implements StorageConnector {

    private static final String DIRECTORY_REGEX = "/";

    private final S3Template s3Template;
    private final StorageProvider s3Provider;

    /**
     * S3 이미지 저장
     * @since 2024.01.27
     * @parameter MultipartFile, String, String
     * @return ResourceResponse
     * @author 김유빈
     */
    @Override
    public ResourceResponse save(MultipartFile resource, String path, String resourceName) {
        final String resourceKey = concatResourceKey(path, resourceName);
        final ByteArrayInputStream serializedResource = serialize(resource);
        final S3Resource savedResource = s3Template.upload(s3Provider.bucket(), resourceKey, serializedResource);
        return createResourceResponse(resourceKey, savedResource);
    }

    /**
     * S3 이미지 삭제
     * @since 2024.01.27
     * @parameter String
     * @author 김유빈
     */
    @Override
    public void delete(String resourceKey) {
        s3Template.deleteObject(s3Provider.bucket(), resourceKey);
    }

    private String concatResourceKey(String path, String resourceName) {
        final String splitPath = split(path);
        if (splitPath.equals("")) {
            return resourceName;
        }
        return String.join(DIRECTORY_REGEX, splitPath, resourceName);
    }

    private String split(String path) {
        final List<String> splitPaths = Arrays.stream(path.split(DIRECTORY_REGEX))
            .filter(split -> !split.equals(""))
            .toList();
        return String.join(DIRECTORY_REGEX, splitPaths);
    }

    private ByteArrayInputStream serialize(MultipartFile resource) {
        try {
            return new ByteArrayInputStream(resource.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private ResourceResponse createResourceResponse(String resourceKey, S3Resource resource) {
        try {
            final URL resourceURL = resource.getURL();
            return new ResourceResponse(resourceKey, resourceURL.toString());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
