// Update Message.java
import java.util.Random;
import org.json.JSONObject; // Add this import

public class Message {
    private final String messageId;
    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;
    private final String recipientPhoneNumber;
    private final String senderPhoneNumber;
    private final String messagePayload;
    private final String timestamp;
    private boolean messageSent;
    private boolean messageReceived;
    private boolean messageRead;
    
    public Message(String messagePayload, String recipientPhoneNumber, String senderPhoneNumber) {
        this.messagePayload = messagePayload;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.senderPhoneNumber = senderPhoneNumber;
        this.messageSent = false;
        this.messageReceived = false;
        this.messageRead = false;
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.messageId = generateMessageId();
        messageCounter++;
    }
    
    private String generateMessageId() {
        Random random = new Random();
        return String.format("%010d", random.nextInt(1000000000));
    }
    
    public boolean checkMessageLength() {
        return messagePayload.length() <= 250;
    }
    
    public boolean checkRecipientCell() {
        return recipientPhoneNumber != null && 
               recipientPhoneNumber.matches("^\\+\\d{10,15}$");
    }
    
    public String createMessageHash() {
        String[] words = messagePayload.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length-1] : firstWord;
        
        return (messageId.substring(0, 2) + ":" + messageCounter + ":" + 
                firstWord.toUpperCase() + lastWord.toUpperCase());
    }
    
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                this.messageSent = true;
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }
    
    public String printMessageDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + recipientPhoneNumber + "\n" +
               "Message: " + messagePayload;
    }
    
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    public JSONObject storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("messageHash", createMessageHash());
        json.put("recipient", recipientPhoneNumber);
        json.put("sender", senderPhoneNumber);
        json.put("message", messagePayload);
        json.put("timestamp", timestamp);
        json.put("status", messageSent ? "SENT" : "STORED");
        return json;
    }
    
    // Getters
    public String getMessageId() { return messageId; }
    public String getRecipientPhoneNumber() { return recipientPhoneNumber; }
    public String getSenderPhoneNumber() { return senderPhoneNumber; }
    public String getMessagePayload() { return messagePayload; }
    public String getTimestamp() { return timestamp; }
    public boolean isMessageSent() { return messageSent; }
    public boolean isMessageReceived() { return messageReceived; }
    public boolean isMessageRead() { return messageRead; }
}
