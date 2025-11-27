package com.example.secretsanta.controller;

import com.example.secretsanta.dto.SecretSantaRequest;
import com.example.secretsanta.service.SecretSantaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secret-santa")
@CrossOrigin // if you serve frontend from another origin, this helps
public class SecretSantaController {

    private final SecretSantaService secretSantaService;

    public SecretSantaController(SecretSantaService secretSantaService) {
        this.secretSantaService = secretSantaService;
    }

    @PostMapping
    public ResponseEntity<String> createSecretSanta(@Valid @RequestBody SecretSantaRequest request) {
        secretSantaService.generateAndSend(request);
        return ResponseEntity.ok("Emails sent");
    }
}