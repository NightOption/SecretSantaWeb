package com.example.secretsanta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Request payload for the Secret Santa API.
 *
 * This DTO contains the list of participants submitted by the frontend.
 * Each participant consists of:
 *  - name  (required, non-empty)
 *  - email (required, must be a valid email address)
 *
 * Validation annotations ensure the backend receives well-formed data
 * before the service layer attempts to generate pairings or send emails.
 */
public class SecretSantaRequest {

    @Size(min = 2, message = "Need at least 2 participants")
    private List<Participant> participants;

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Represents one participant in the Secret Santa draw.
     *
     * This inner class exists only to structure the JSON request body.
     * It is not used for persistence or business logic beyond transporting data.
     */
    public static class Participant {

        @NotBlank
        private String name;

        @NotBlank
        @Email
        private String email;

        public Participant() {}

        public Participant(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}