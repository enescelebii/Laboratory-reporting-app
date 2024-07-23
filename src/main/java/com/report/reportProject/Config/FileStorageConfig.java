package com.report.reportProject.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStorageConfig {

    @Value("${file.upload-dir}")// application propertiesteki degeri alıyoruz
    private String uploadDir;

    @PostConstruct //çalışması gereken fonksiyonu belirler
    public void init() {// uygulama basladıgında otomatik çalışır
        File file = new File(uploadDir); // file nesnesi oluşturuldu
        if (!file.exists()) {
            file.mkdirs(); // dizin yoksa dizini oluşturur
        }
    }



}
