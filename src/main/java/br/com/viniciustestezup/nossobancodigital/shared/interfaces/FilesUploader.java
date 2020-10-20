package br.com.viniciustestezup.nossobancodigital.shared.interfaces;

import org.springframework.web.multipart.MultipartFile;

@FunctionalInterface
public interface FilesUploader {
    String upload(MultipartFile file, String key);
}
