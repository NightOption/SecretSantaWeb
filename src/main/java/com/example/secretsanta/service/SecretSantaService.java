package com.example.secretsanta.service;

import com.example.secretsanta.dto.SecretSantaRequest;
import com.example.secretsanta.dto.SecretSantaRequest.Participant;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Core logic for running a Secret Santa draw.
 *
 * This service:
 *  - validates the list of participants
 *  - generates a random permutation where no one is assigned to themselves
 *  - sends an email to each participant with their assigned receiver
 */
@Service
public class SecretSantaService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    public SecretSantaService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Runs a complete Secret Santa draw:
     *  1. Validates the participant list
     *  2. Generates a valid pairing (no self-assignments)
     *  3. Sends an email to every participant with their assignment
     *
     * @param request the incoming request containing the participants.
     *                The list is assumed to be validated at the controller level,
     *                but we still enforce minimal safety checks here.
     */
    public void generateAndSend(SecretSantaRequest request) {
        List<Participant> participants = new ArrayList<>(request.getParticipants());

        if (participants.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 participants");
        }

        // Generate a shuffled list of receivers until no one is assigned to themselves.
        List<Participant> receivers;
        int attempts = 0;
        do {
            receivers = new ArrayList<>(participants);
            Collections.shuffle(receivers, random);
            attempts++;
            if (attempts > 1000) {
                // Defensive guard to avoid potential infinite loops in edge cases
                throw new IllegalStateException("Could not generate a valid Secret Santa pairing");
            }
        } while (!isValidPermutation(participants, receivers));

        // Once we have a valid permutation, send an email to each giver.
        for (int i = 0; i < participants.size(); i++) {
            Participant giver = participants.get(i);
            Participant receiver = receivers.get(i);
            sendEmail(giver, receiver);
        }
    }

    /**
     * Checks that the permutation is valid:
     * each participant must be paired with someone else (no self-assignments).
     *
     * @param givers    original participant list.
     * @param receivers shuffled receiver list.
     * @return true if every participant is assigned to a different email.
     */
    private boolean isValidPermutation(List<Participant> givers, List<Participant> receivers) {
        for (int i = 0; i < givers.size(); i++) {
            if (givers.get(i).getEmail().equals(receivers.get(i).getEmail())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sends a single Secret Santa assignment email to the given participant.
     *
     * @param giver    the participant who will be the Secret Santa.
     * @param receiver the person they have been assigned to.
     */
    private void sendEmail(Participant giver, Participant receiver) {
        String subject = "Your Secret Santa Assignment 🎁";
        String text = String.format(
                "Hi %s,\n\n" +
                        "You are the Secret Santa for: %s\n\n" +
                        "Keep it secret, keep it fun!\n\n" +
                        "- Secret Santa Bot",
                giver.getName(),
                receiver.getName()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(giver.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}