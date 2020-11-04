package requestHandler;


import auction.requestHandler.PlaceBidCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceBidCommandTest {

    @Test
    void testToString() {
        PlaceBidCommand placeBidInstance = new PlaceBidCommand();
        String expectedResult = "PLACE_BID <bidder id> <token> <item id> <bid price>";
        String commandStr = placeBidInstance.toString();
        assertEquals(expectedResult, commandStr);
    }

    @Test
    void testGetInvalidCommandText() {
        PlaceBidCommand placeBidInstance = new PlaceBidCommand();
        String expectedResult = "Invalid command";
        String commandStr = placeBidInstance.getInvalidCommandText();
        assertEquals(expectedResult, commandStr);
    }

    @Test
    void testExecuteInvalidCommand() {
        PlaceBidCommand placeBidInstance = new PlaceBidCommand();
        String expectedResult = placeBidInstance.getInvalidCommandText();
        String actualResponse;
        String [] requestList = initialiseInvalidCommandStrings();

        for (String currRequest: requestList)
        {
            actualResponse = placeBidInstance.execute(currRequest, null);
            assertEquals(expectedResult,actualResponse,"Request:'"+currRequest+"'");
        }

    }

    //helper method
    String[] initialiseInvalidCommandStrings()
    {
        return new String[] {
                "PLACE_BID1 usr123 sfsf item1 9.5",
                "PLACE_BID",
                "PLACE_BID sfsf item1 9.5",
                "PLACE_BID usr123 sfsf item1 ",
        };
    }
}