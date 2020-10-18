package br.com.viniciustestezup.nossobancodigital.compartilhado.interfaces;

import org.springframework.web.multipart.MultipartFile;

@FunctionalInterface
public interface FilesUploader {
    String upload(MultipartFile file, String key);
}
