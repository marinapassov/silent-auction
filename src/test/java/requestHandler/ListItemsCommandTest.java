package requestHandler;

import auction.requestHandler.ListItemsCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListItemsCommandTest {

    @Test
    void testToString() {
        ListItemsCommand listItemsInstance =  new ListItemsCommand();
        String expectedResult = "LIST_ITEMS <bidder id> <token>";
        String commandStr = listItemsInstance.toString();
        assertEquals(expectedResult, commandStr);
    }

    @Test
    void testGetInvalidCommandText() {
        ListItemsCommand listItemsInstance =  new ListItemsCommand();
        String expectedResult = "Invalid command\n";
        String commandStr = listItemsInstance.getInvalidCommandText();
        assertEquals(expectedResult, commandStr);
    }

    @Test
    void testExecuteInvalidCommand() {
        ListItemsCommand listItemsInstance =  new ListItemsCommand();
        String expectedResult = listItemsInstance.getInvalidCommandText();
        String actualResponse;
        String [] requestList = initialiseInvalidCommandStrings();

        for (String currRequest: requestList)
        {
            actualResponse = listItemsInstance.execute(currRequest, null);
            assertEquals(expectedResult,actualResponse,"Request:'"+currRequest+"'");
        }

    }

    //helper method
    String[] initialiseInvalidCommandStrings()
    {
        return new String[] {
                "LIST_ITEMS1 <bidder_id> <token>",
                "LIST_ITEMS <token>",
                "LIST_ITEMS <bidder_id> ",
        };
    }
}