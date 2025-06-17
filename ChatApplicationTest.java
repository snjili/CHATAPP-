import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatApplicationTest {
    
    @Test
    public void testSentMessagesArrayPopulated() {
        ChatApplication app = new ChatApplication();
        List<Message> sentMessages = app.getSentMessages();
        
        assertTrue(sentMessages.stream()
            .anyMatch(msg -> msg.getMessagePayload().equals("Did you get the cake?")));
        assertTrue(sentMessages.stream()
            .anyMatch(msg -> msg.getMessagePayload().equals("It is dinner time !")));
    }
    
    @Test
    public void testDisplayLongestMessage() {
        ChatApplication app = new ChatApplication();
        String longest = app.getSentMessages().stream()
            .max((m1, m2) -> Integer.compare(m1.getMessagePayload().length(), m2.getMessagePayload().length()))
            .get()
            .getMessagePayload();
        
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }
    
    @Test
    public void testSearchMessagesByRecipient() {
        ChatApplication app = new ChatApplication();
        List<Message> messages = app.searchMessagesByRecipientTest("+27838884567");
        
        assertEquals(2, messages.size());
        assertTrue(messages.stream()
            .anyMatch(msg -> msg.getMessagePayload().equals("Where are you? You are late! I have asked you to be on time.")));
        assertTrue(messages.stream()
            .anyMatch(msg -> msg.getMessagePayload().equals("Ok, I am leaving without you.")));
    }
    
    //  methods in ChatApplication for testing
    public List<Message> getSentMessages() {
        return sentMessages;
    }
    
    public List<Message> searchMessagesByRecipientTest(String recipient) {
        List<Message> result = new ArrayList<>();
        result.addAll(sentMessages.stream()
            .filter(msg -> msg.getRecipientPhoneNumber().equals(recipient))
            .collect(Collectors.toList()));
        result.addAll(storedMessages.stream()
            .filter(msg -> msg.getRecipientPhoneNumber().equals(recipient))
            .collect(Collectors.toList()));
        return result;
    }
}
