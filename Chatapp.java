/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapp;

/**
 *
 * @author Sisipho
 */
public class Chatapp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
    }
    public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private boolean isLoggedIn;
    
    public Login() {
        this.isLoggedIn = false;
    }
    
    public Login(String username, String password, String firstName, String lastName, String cellPhoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isLoggedIn = false;
    }
    
    // Method to check if username meets the requirements
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        
        return username.contains("_") && username.length() <= 5;
    }
    
    // Method to check if password meets complexity requirements
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    // Method to check if cell phone number is correctly formatted
    // This uses a regular expression to validate the cell phone number format
    // Code generated using Claude AI
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }
        
        // Pattern matches numbers that start with +27 followed by exactly 9 digits
        String regex = "^\\+27\\d{9}$";
        return cellPhoneNumber.matches(regex);
    }
    
    // Method to register a user
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        return "Registration successful!";
    }
    
    // Method to authenticate a user
    public boolean loginUser(String username, String password) {
        if (this.username == null || this.password == null) {
            return false;
        }
        
        boolean isCorrect = this.username.equals(username) && this.password.equals(password);
        this.isLoggedIn = isCorrect;
        return isCorrect;
    }
    
    // Method to return login status
    public String returnLoginStatus() {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
    public class Message {
    private String messagePayload;
    private boolean messageSent;
    private boolean messageReceived;
    private boolean messageRead;
    private String recipientPhoneNumber;
    private String senderPhoneNumber;
    private String timestamp;
    
    public Message(String messagePayload, String recipientPhoneNumber, String senderPhoneNumber) {
        this.messagePayload = messagePayload;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.senderPhoneNumber = senderPhoneNumber;
        this.messageSent = false;
        this.messageReceived = false;
        this.messageRead = false;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
    
    public String getMessagePayload() {
        return messagePayload;
    }
    
    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }
    
    public boolean isMessageSent() {
        return messageSent;
    }
    
    public void setMessageSent(boolean messageSent) {
        this.messageSent = messageSent;
    }
    
    public boolean isMessageReceived() {
        return messageReceived;
    }
    
    public void setMessageReceived(boolean messageReceived) {
        this.messageReceived = messageReceived;
    }
    
    public boolean isMessageRead() {
        return messageRead;
    }
    
    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }
    
    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }
    
    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }
    
    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }
    
    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        String status = "";
        if (messageRead) {
            status = "READ";
        } else if (messageReceived) {
            status = "RECEIVED";
        } else if (messageSent) {
            status = "SENT";
        } else {
            status = "PENDING";
        }
        
        return "Message: " + messagePayload + " | Status: " + status + " | Time: " + timestamp;
        }
    }

    
}
