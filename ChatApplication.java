// Update ChatApplication.java
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChatApplication {
    private Map<String, Login> users;
    private Map<String, List<Message>> messages;
    private Login currentUser = null;
    
    // Arrays for storing different types of messages
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIds = new ArrayList<>();
    
    public ChatApplication() {
        users = new HashMap<>();
        messages = new HashMap<>();
        loadTestData(); // Load test data when application starts
    }
    
    private void loadTestData() {
        // Test Data Message 1
        Message msg1 = new Message("Did you get the cake?", "+27834557896", "developer");
        msg1.sentMessage(1);
        storeMessageInArrays(msg1);
        
        // Test Data Message 2
        Message msg2 = new Message("Where are you? You are late! I have asked you to be on time.", "+27838884567", "developer");
        msg2.sentMessage(3); // Stored
        storeMessageInArrays(msg2);
        
        // Test Data Message 3
        Message msg3 = new Message("Yohoooo, I am at your gate.", "+27834484567", "developer");
        msg3.sentMessage(2); // Disregarded
        storeMessageInArrays(msg3);
        
        // Test Data Message 4
        Message msg4 = new Message("It is dinner time !", "0838884567", "developer");
        msg4.sentMessage(1); // Sent
        storeMessageInArrays(msg4);
        
        // Test Data Message 5
        Message msg5 = new Message("Ok, I am leaving without you.", "+27838884567", "developer");
        msg5.sentMessage(3); // Stored
        storeMessageInArrays(msg5);
    }
    
    private void storeMessageInArrays(Message message) {
        messageIds.add(message.getMessageId());
        messageHashes.add(message.createMessageHash());
        
        if (message.isMessageSent()) {
            sentMessages.add(message);
        } else {
            int choice = message.sentMessage(0).contains("delete") ? 2 : 3;
            if (choice == 2) {
                disregardedMessages.add(message);
            } else if (choice == 3) {
                storedMessages.add(message);
            }
        }
    }
    
    // Add this new method to handle message arrays
    public void runArrayOperationsMenu() {
        if (currentUser == null) {
            System.out.println("You must be logged in to access message operations.");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n===== Message Array Operations =====");
            System.out.println("1. Display sender and recipient of all sent messages");
            System.out.println("2. Display the longest sent message");
            System.out.println("3. Search for a message by ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete a message by hash");
            System.out.println("6. Display full report of sent messages");
            System.out.println("7. Back to main menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    displaySendersAndRecipients();
                    break;
                case "2":
                    displayLongestMessage();
                    break;
                case "3":
                    System.out.print("Enter message ID to search: ");
                    searchMessageById(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Enter recipient phone number: ");
                    searchMessagesByRecipient(scanner.nextLine());
                    break;
                case "5":
                    System.out.print("Enter message hash to delete: ");
                    deleteMessageByHash(scanner.nextLine());
                    break;
                case "6":
                    displayFullReport();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private void displaySendersAndRecipients() {
        System.out.println("\n=== Senders and Recipients of Sent Messages ===");
        for (Message msg : sentMessages) {
            System.out.println("Sender: " + msg.getSenderPhoneNumber() + 
                             " | Recipient: " + msg.getRecipientPhoneNumber());
        }
    }
    
    private void displayLongestMessage() {
        if (sentMessages.isEmpty()) {
            System.out.println("No sent messages available.");
            return;
        }
        
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessagePayload().length() > longest.getMessagePayload().length()) {
                longest = msg;
            }
        }
        
        System.out.println("\n=== Longest Sent Message ===");
        System.out.println("Message: " + longest.getMessagePayload());
        System.out.println("Length: " + longest.getMessagePayload().length() + " characters");
    }
    
    private void searchMessageById(String messageId) {
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(messageId)) {
                System.out.println("\n=== Message Found ===");
                System.out.println("Recipient: " + msg.getRecipientPhoneNumber());
                System.out.println("Message: " + msg.getMessagePayload());
                return;
            }
        }
        System.out.println("Message with ID " + messageId + " not found.");
    }
    
    private void searchMessagesByRecipient(String recipient) {
        List<Message> recipientMessages = new ArrayList<>();
        recipientMessages.addAll(sentMessages.stream()
            .filter(msg -> msg.getRecipientPhoneNumber().equals(recipient))
            .collect(Collectors.toList()));
        recipientMessages.addAll(storedMessages.stream()
            .filter(msg -> msg.getRecipientPhoneNumber().equals(recipient))
            .collect(Collectors.toList()));
        
        if (recipientMessages.isEmpty()) {
            System.out.println("No messages found for recipient " + recipient);
            return;
        }
        
        System.out.println("\n=== Messages for Recipient: " + recipient + " ===");
        for (Message msg : recipientMessages) {
            System.out.println("Message: " + msg.getMessagePayload());
        }
    }
    
    private void deleteMessageByHash(String hash) {
        for (Message msg : sentMessages) {
            if (msg.createMessageHash().equals(hash)) {
                sentMessages.remove(msg);
                System.out.println("Message \"" + msg.getMessagePayload() + "\" successfully deleted.");
                return;
            }
        }
        System.out.println("Message with hash " + hash + " not found.");
    }
    
    private void displayFullReport() {
        System.out.println("\n=== Full Report of Sent Messages ===");
        for (Message msg : sentMessages) {
            System.out.println("Message Hash: " + msg.createMessageHash());
            System.out.println("Recipient: " + msg.getRecipientPhoneNumber());
            System.out.println("Message: " + msg.getMessagePayload());
            System.out.println("Timestamp: " + msg.getTimestamp());
            System.out.println("----------------------------------");
        }
    }
    
    // Update the main menu to include the new option
    public static void main(String[] args) {
        ChatApplication app = new ChatApplication();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n===== Chat Application =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Access Messages");
            System.out.println("4. Message Array Operations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    // ... existing registration code ...
                    break;
                case "2":
                    // ... existing login code ...
                    break;
                case "3":
                    app.runMessageMenu();
                    break;
                case "4":
                    app.runArrayOperationsMenu();
                    break;
                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
    
    // ... rest of the existing code ...
}
