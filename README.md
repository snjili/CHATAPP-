# QuickChat Application

A Java-based chat application that allows users to register, login, and send messages with various validations and features.

## Features

- User registration with validation:
  - Username must contain underscore and be ≤5 characters
  - Password complexity requirements (8+ chars, uppercase, number, special char)
  - Cell phone number validation (international format)
- Secure login functionality
- Message system with:
  - Character limit (250 chars)
  - Recipient validation
  - Message status tracking (sent/received/read)
  - Message ID and hash generation
- JSON message storage
- Graphical message confirmation (JOptionPane)
- Menu-driven interface

## Requirements

- Java 11 or higher
- Maven (for building and testing)

## Installation

1. Clone the repository:
   https://github.com/snjili/CHATAPP-
