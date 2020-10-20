package br.com.viniciustestezup.nossobancodigital.shared.services;

import br.com.viniciustestezup.nossobancodigital.shared.interfaces.FilesUploader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploaderServiceImpl implements FilesUploader {

    @Override
    public String upload(MultipartFile file, String key) {
        System.out.println("Enviando arquivos...");
        String fileName = file.getOriginalFilename().replaceAll("\\s","_");
        return String.format("https://driver/%s_%s", key, fileName);
    }
}
