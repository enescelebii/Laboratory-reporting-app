package com.report.reportProject.Rest;

import com.report.reportProject.Entity.Laborant;
import com.report.reportProject.Entity.Report;
import com.report.reportProject.Services.LaborantService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/laborants")// zorunlu path veriyoruz
public class LaborantConroller {

    @Autowired// bir instance oluşturarak fonksiyonlarımızı kullanacagız
    private LaborantService laborantService;

    @GetMapping // default path kullanıyoruz yani /api/laborants
    public ResponseEntity<List<Laborant>> getLaborants() {
        List<Laborant> laborants = laborantService.getLaborants();
        return ResponseEntity.ok(laborants); // ResponseEntity http yanıtları için kullanılır .ok() fonksiyonu ise
        // 200 olan kod ile başarılı olarak işaretler
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laborant> getLaborantById(@PathVariable int id) {
        Laborant laborant = laborantService.getLaborantById(id);
        return ResponseEntity.ok(laborant); // 200 kodu ile basarılı olarak donucek
    }

    @PostMapping
    public ResponseEntity<Laborant> createLaborant(@RequestBody Laborant laborant) {
        laborantService.SaveLaborant(laborant);
        return ResponseEntity.ok(laborant);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Laborant> updateLaborant(@PathVariable int id, @RequestBody Laborant updatedLaborant) {
        // Mevcut Laborant'ı al
        Laborant existingLaborant = laborantService.getLaborantById(id);
        if (existingLaborant == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found döndürür
        }

        // Gelen laborant'ın ID'sini mevcut laborant'ın ID'si ile eşitler
        updatedLaborant.setId(id);

        // Mevcut laborant'taki sadece gerekli alanları günceller
        existingLaborant.setFirstName(updatedLaborant.getFirstName()); // İsim güncelleme
        existingLaborant.setLastName(updatedLaborant.getLastName()); // Soyisim güncelleme


        // Güncellenmiş laborant'ı kaydet
        Laborant savedLaborant = laborantService.SaveLaborant(existingLaborant);

        return ResponseEntity.ok(savedLaborant); // 200 OK ve güncellenmiş laborant'ı döndürür
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Laborant> deleteLaborant(@PathVariable int id) {
        laborantService.deleteLaborant(id);
        return ResponseEntity.noContent().build(); // NoContent 204 no content durumunu http ye gönderir .build() ise bunu gönderme paketi oluşturur
    }

}
