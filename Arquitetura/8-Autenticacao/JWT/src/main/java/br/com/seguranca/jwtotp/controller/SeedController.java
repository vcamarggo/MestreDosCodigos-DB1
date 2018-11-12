package br.com.seguranca.jwtotp.controller;

import br.com.seguranca.jwtotp.dto.SeedDTO;
import br.com.seguranca.jwtotp.repository.SeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banking/semente")
public class SeedController {

    @Autowired
    SeedRepository seedRepository;

    @GetMapping(value = "/existe-ativa")
    public ResponseEntity hasSeed(@RequestParam("account") String account) {
        return seedRepository.findById(account).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity createSeed(@RequestBody SeedDTO seedDTO) {
        seedRepository.save(seedDTO);
        return ResponseEntity.ok().build();
    }
}
