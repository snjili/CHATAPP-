import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ChatApplication {
    private Map<String, Login> users;  // Map phone number to user
    private Map<String, List<Message>> messages;  // Map phone number to list of messages
    private Login currentUser = null;
    
    public ChatApplication() {
        users = new HashMap<>();
        messages = new HashMap<>();
    }
    
    public void runMessageMenu() {
        if (currentUser == null) {
            System.out.println("You must be logged in to access messages.");
            return;
        }
        
        System.out.println("Welcome to QuickChat");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n===== Message Menu =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    sendMessagesFlow(scanner);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private void sendMessagesFlow(Scanner scanner) {
        System.out.print("How many messages do you want to send? ");
        int numMessages;
        try {
            numMessages = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }
        
        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n=== Message " + (i+1) + " ===");
            System.out.print("Enter recipient phone number: ");
            String recipientNumber = scanner.nextLine();
            
            // Check if recipient exists
            if (!users.containsKey(recipientNumber)) {
                System.out.println("Recipient not found. Please try again.");
                i--; // Retry this message
                continue;
            }
            
            System.out.print("Enter message: ");
            String messageText = scanner.nextLine();
            
            Message message = new Message(messageText, recipientNumber, 
                                      currentUser.getCellPhoneNumber());
            
            if (!message.checkMessageLength()) {
                System.out.println("Please enter a message of less than 250 characters.");
                i--; // Retry this message
                continue;
            }
            
            if (!message.checkRecipientCell()) {
                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.");
                i--; // Retry this message
                continue;
            }
            
            System.out.println("\n" + message.printMessageDetails());
            
            System.out.println("\nChoose an option:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Your choice: ");
            
            int sendChoice;
            try {
                sendChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Message will be disregarded.");
                sendChoice = 2;
            }
            
            String result = message.sentMessage(sendChoice);
            System.out.println(result);
            
            if (sendChoice == 1) {
                // Initialize message lists if they don't exist
                messages.computeIfAbsent(currentUser.getCellPhoneNumber(), k -> new ArrayList<>());
                messages.computeIfAbsent(recipientNumber, k -> new ArrayList<>());
                
                // Add to sender's and recipient's message lists
                messages.get(currentUser.getCellPhoneNumber()).add(message);
                messages.get(recipientNumber).add(message);
                
                // Show in JOptionPane
                JOptionPane.showMessageDialog(null, 
                    "Message Details:\n" + message.printMessageDetails(),
                    "Message Sent",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Store in JSON file
                try (FileWriter file = new FileWriter("messages.json", true)) {
                    file.write(message.storeMessage().toString() + "\n"1);
                } catch (IOException e) {
                    System.out.println("Error storing message: " + e.getMessage());
                }
            }
        }
        
        System.out.println("\nTotal messages sent in this session: " + 
                          Message.returnTotalMessages());
    }
    
    // Add your registerUser and login methods here
    public boolean registerUser(String username, String password, String firstName, 
                              String lastName, String cellPhoneNumber) {
        Login newUser = new Login(username, password, firstName, lastName, cellPhoneNumber);
        String registrationStatus = newUser.registerUser();
        if (registrationStatus.equals("Registration successful!")) {
            users.put(cellPhoneNumber, newUser);
            messages.put(cellPhoneNumber, new ArrayList<>());
            return true;
        }
        System.out.println(registrationStatus);
        return false;
    }
    
    public boolean login(String username, String password) {
        for (Login user : users.values()) {
            if (user.getUsername().equals(username)) {
                boolean loginSuccess = user.loginUser(username, password);
                if (loginSuccess) {
                    currentUser = user;
                    System.out.println(user.returnLoginStatus());
                    return true;
                } else {
                    System.out.println("Username or password incorrect, please try again.");
                    return false;
                }
            }
        }
        System.out.println("User not found. Please register first.");
        return false;
    }
    
    public static void main(String[] args) {
        ChatApplication app = new ChatApplication();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n===== Chat Application =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Access Messages");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter username (must contain underscore and max 5 chars): ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password (min 8 chars, uppercase, number, special char): ");
                    String password = scanner.nextLine();
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter cell phone number (format: +27XXXXXXXXX): ");
                    String cellPhoneNumber = scanner.nextLine();
                    app.registerUser(username, password, firstName, lastName, cellPhoneNumber);
                    break;
                case "2":
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    app.login(loginUsername, loginPassword);
                    break;
                case "3":
                    app.runMessageMenu();
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}