package de.ruben.csvupload.message;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResponseMessageTest {

    @Test
    public void testResponseMessageCreation() {
        // Arrange
        String message = "Test message";

        // Act
        ResponseMessage responseMessage = new ResponseMessage(message);

        // Assert
        assertEquals(message, responseMessage.getMessage());
    }
}
