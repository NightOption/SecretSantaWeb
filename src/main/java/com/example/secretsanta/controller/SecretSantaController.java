package com.example.secretsanta.controller;

import com.example.secretsanta.dto.SecretSantaRequest;
import com.example.secretsanta.service.SecretSantaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes the API endpoint for creating
 * a Secret Santa draw.
 *
 * Receives a list of participants (name + email), delegates the
 * pairing and email sending to {@link SecretSantaService}, and
 * returns a simple success response.
 */
@RestController
@RequestMapping("/api/secret-santa")
@CrossOrigin // allow calls from the frontend if it is served from a different origin
public class SecretSantaController {

    private final SecretSantaService secretSantaService;

    public SecretSantaController(SecretSantaService secretSantaService) {
        this.secretSantaService = secretSantaService;
    }

    /**
     * Creates a Secret Santa pairing and sends emails to all participants.
     *
     * @param request JSON payload containing the list of participants.
     *                Validation is applied via {@link jakarta.validation.Valid}.
     * @return 200 OK if the emails were successfully queued/sent.
     */
    @PostMapping
    public ResponseEntity<String> createSecretSanta(@Valid @RequestBody SecretSantaRequest request) {
        secretSantaService.generateAndSend(request);
        return ResponseEntity.ok("Emails sent");
    }
}