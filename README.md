# 🎅 Secret Santa Generator (Windows 95 Edition)

A fun and fully functional **Secret Santa name generator** built with **Java Spring Boot** and a retro **Windows 95-style UI**.

Participants enter their **name + email**, and the app randomly assigns each person another participant as their Secret Santa, sending email notifications automatically.

The entire interface behaves like a Win95 application:
- draggable window
- minimize / maximize / close
- animated window transitions
- retro pixel icon on minimize
- “Check your inbox!” confirmation screen

Perfect for small groups, friends, family, or office events.

---

## 🖼️ Features

### ✨ Core Functionality
- Add any number of participants
- Robust Secret Santa pairing algorithm
- Ensures no one draws themselves
- One-click *Generate & Send*
- Emails delivered via SMTP

### 🪟 Win95 UI
- Movable draggable application window
- Animations for minimize, maximize, close
- Minimize → shrinks to bottom-left mail icon
- Close → click anywhere to reopen window
- Success screen styled like a classic message box
- Vaporwave-inspired background

### 📧 Email Delivery
- Fully integrated email sending via Spring Boot Mail
- Includes plug-and-play **MailHog** setup for safe local testing
- Optional real SMTP provider configuration

---

## 🚀 Getting Started

### 1. Requirements
- **Java 17+**
- **Maven**
- **MailHog** *(recommended for local development)*
- IntelliJ IDEA or any editor

---

## ▶️ Running the Application

Start the Spring Boot application:

```bash
mvn spring-boot:run
```
Use to enter website:
http://localhost:8080

This project is pre-configured for local email testing using MailHog.
MailHog Ports
•	SMTP server: localhost:1025
•	Web UI: http://localhost:8025

