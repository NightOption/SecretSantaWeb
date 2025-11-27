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

@Service
public class SecretSantaService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    public SecretSantaService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void generateAndSend(SecretSantaRequest request) {
        List<Participant> participants = new ArrayList<>(request.getParticipants());

        if (participants.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 participants");
        }

        List<Participant> receivers;
        int attempts = 0;
        do {
            receivers = new ArrayList<>(participants);
            Collections.shuffle(receivers, random);
            attempts++;
            if (attempts > 1000) {
                throw new IllegalStateException("Could not generate a valid Secret Santa pairing");
            }
        } while (!isValidPermutation(participants, receivers));

        for (int i = 0; i < participants.size(); i++) {
            Participant giver = participants.get(i);
            Participant receiver = receivers.get(i);
            sendEmail(giver, receiver);
        }
    }

    private boolean isValidPermutation(List<Participant> givers, List<Participant> receivers) {
        for (int i = 0; i < givers.size(); i++) {
            if (givers.get(i).getEmail().equals(receivers.get(i).getEmail())) {
                return false;
            }
        }
        return true;
    }

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