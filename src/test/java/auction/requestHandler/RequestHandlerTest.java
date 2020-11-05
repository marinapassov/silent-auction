package auction.requestHandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    //TODO: add test for bidder welcome message

    @Test
    void testGetWelcomeMessageAdministrator() {
        AdminRequestHandler adminRequestHandler = new AdminRequestHandler(null);
        String expectedAdminMessage = "\nWelcome! Please type one of the following commands\n\n" +
                "LIST_ITEMS\n" +
                "ADD_ITEM ID:<item id> D:<description> P:<start price> S:<start date in format dd/MM/yyyy HH:mm> " +
                "E:<end date in format dd/MM/yyyy HH:mm>\nEXIT\n";
        assertEquals(expectedAdminMessage, adminRequestHandler.getWelcomeMessage());
    }
}