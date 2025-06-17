# Chat Application - QuickChat

## Overview

QuickChat is a Java-based chat application that allows users to register, login, send messages, and perform various message operations. The application includes features for message validation, storage, and reporting as specified in the assignment requirements.

## Features

### User Management
- User registration with validation for:
  - Username (must contain underscore and be ≤5 characters)
  - Password complexity (8+ chars, capital letter, number, special char)
  - Cell phone number format (+27 followed by 9 digits)
- User login and authentication

### Messaging
- Send messages with validation for:
  - Message length (≤250 characters)
  - Recipient phone number format
- Message status tracking (sent, stored, disregarded)
- Message storage in JSON format

### Message Operations
- Display sender and recipient of all sent messages
- Find and display the longest sent message
- Search for messages by message ID
- Search for all messages sent to a particular recipient
- Delete messages using message hash
- Display full report of all sent messages

### Data Management
- Maintains arrays for:
  - Sent messages
  - Disregarded messages
  - Stored messages
  - Message hashes
  - Message IDs
- Includes preloaded test data for demonstration and testing

## Getting Started

### Prerequisites
- Java JDK 8 or later
- org.json library for JSON handling

### Installation
1. Clone the repository or download the source files
2. Compile all Java files:
   ```
   javac *.java
   ```
3. Run the application:
   ```
   java ChatApplication
   ```

## Usage

1. **Main Menu Options**:
   - Register: Create a new user account
   - Login: Authenticate with existing credentials
   - Access Messages: Send and manage messages
   - Message Array Operations: Perform operations on message arrays
   - Exit: Quit the application

2. **Message Operations Menu**:
   - Send Messages: Compose and send new messages
   - Show recently sent messages: View message history (Coming Soon)

3. **Array Operations Menu**:
   - Display sender/recipient of sent messages
   - Display longest sent message
   - Search by message ID
   - Search by recipient
   - Delete by message hash
   - Display full report

## Test Data

The application comes preloaded with the following test messages:

1. **Message 1**:
   - Recipient: +27834557896
   - Message: "Did you get the cake?"
   - Status: Sent

2. **Message 2**:
   - Recipient: +27838884567
   - Message: "Where are you? You are late! I have asked you to be on time."
   - Status: Stored

3. **Message 3**:
   - Recipient: +27834484567
   - Message: "Yohoooo, I am at your gate."
   - Status: Disregarded

4. **Message 4**:
   - Recipient: 0838884567
   - Message: "It is dinner time !"
   - Status: Sent

5. **Message 5**:
   - Recipient: +27838884567
   - Message: "Ok, I am leaving without you."
   - Status: Stored

## Unit Tests

The application includes unit tests for:
- Verifying the sent messages array is correctly populated
- Displaying the longest message
- Searching for messages by recipient

To run tests, use your preferred JUnit test runner.

## Technical Details

### Classes
1. **Login**: Handles user authentication and validation
2. **Message**: Represents a message with all its properties and operations
3. **ChatApplication**: Main application class with all business logic
4. **ChatApp**: Alternate entry point (currently empty)

### Data Storage
- Messages are stored in memory during runtime
- Sent messages are also written to `messages.json` file

## Known Issues/Limitations
- The "Show recently sent messages" feature is marked as "Coming Soon"
- JSON file handling is basic and could be enhanced
- No persistent user database (all data is in-memory)

## Future Enhancements
- Implement message read/received status tracking
- Add persistent user database
- Enhance message search capabilities
- Implement group messaging

## Author

Sisipho
