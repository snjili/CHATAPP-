/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    void testCheckMessageLength() {
        Message shortMsg = new Message("Short", "+27123456789", "+27123456788");
        assertTrue(shortMsg.checkMessageLength());
        
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 300; i++) longMsg.append("a");
        Message tooLongMsg = new Message(longMsg.toString(), "+27123456789", "+27123456788");
        assertFalse(tooLongMsg.checkMessageLength());
    }
    
    @Test
    void testCheckRecipientCell() {
        Message validMsg = new Message("Test", "+27123456789", "+27123456788");
        assertTrue(validMsg.checkRecipientCell());
        
        Message invalidMsg1 = new Message("Test", "27123456789", "+27123456788");
        assertFalse(invalidMsg1.checkRecipientCell());
        
        Message invalidMsg2 = new Message("Test", "+2712345678", "+27123456788");
        assertFalse(invalidMsg2.checkRecipientCell());
    }
    
    @Test
    void testCreateMessageHash() {
        Message msg = new Message("Hi Mike, can you join us for dinner tonight", 
                                 "+27123456789", "+27123456788");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("^\\d{2}:\\d+:HI[A-Z]+$"));
    }
    
    @Test
    void testSentMessage() {
        Message msg = new Message("Test", "+27123456789", "+27123456788");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
        assertEquals("Press 0 to delete message.", msg.sentMessage(2));
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }
}