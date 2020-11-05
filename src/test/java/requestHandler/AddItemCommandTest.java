package requestHandler;

import auction.entity.Auction;
import auction.requestHandler.AddItemCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

class AddItemCommandTest {

    @Test
    void testToString() {
        AddItemCommand addItemInstance =  new AddItemCommand();
        String expectedResult = "ADD_ITEM ID:<item id> D:<description> P:<start price> " +
                "S:<start date in format dd/MM/yyyy HH:mm> E:<end date in format dd/MM/yyyy HH:mm>";
        String commandStr = addItemInstance.toString();
        assertEquals(expectedResult, commandStr);
    }

    @Test
    void testGetInvalidCommandText() {
        AddItemCommand addItemInstance =  new AddItemCommand();
        String expectedResult = "Invalid command\n";
        String commandStr = addItemInstance.getInvalidCommandText();
        assertEquals(expectedResult, commandStr);
    }


    @Test
    void testExecuteValidCommand() {
        AddItemCommand addItemInstance =  new AddItemCommand();
        String expectedResult = "New item has been added to auction";
        String request = "ADD_ITEM ID:item1 D:item1 P:1.0 S:16/09/2020 18:00 E:16/09/2020 18:30";
        Auction localAuctionInstance = Mockito.mock(Auction.class);
        Mockito.when(localAuctionInstance.addNewItem(anyString(),anyString(),anyDouble(),any(),any())).thenReturn("New item has been added to auction");
        String actualResponse = addItemInstance.execute(request, localAuctionInstance);
        assertEquals(expectedResult,actualResponse);
    }

    @Test
    void testExecuteInvalidCommand() {
        AddItemCommand addItemInstance =  new AddItemCommand();
        String expectedResult = addItemInstance.getInvalidCommandText();
        String actualResponse;
        String [] requestList = initialiseInvalidCommandStrings();
        Auction localAuctionInstance = Mockito.mock(Auction.class);
        Mockito.when(localAuctionInstance.addNewItem(anyString(),anyString(),anyDouble(),any(),any())).thenReturn("New item has been added to auction");


        for (String currRequest: requestList)
        {
            actualResponse = addItemInstance.execute(currRequest, localAuctionInstance);
            assertEquals(expectedResult,actualResponse,"Request:'"+currRequest+"'");
        }

    }

    //helper method
    String[] initialiseInvalidCommandStrings()
    {
        return new String[] {
                "ADD_ITEM",
                "ADD_ITEM1 ID:item1 D:item1 P:1.0 S:16/09/2020 18:00 E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D: P:1.0 S:16/09/2020 18:00 E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D:item1 P: S:16/09/2020 18:00 E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D:item1 P:1.0 S:16/9/2020 18:00 E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D:item1 P:1.0 S:16/09/2020 :00 E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D:item1 P:1.0 S:16/09/2020 18: E:16/09/2020 18:30",
                "ADD_ITEM ID:item1 D:item1 P:1.0 S:16/09/2020 18:00"
        };

    }
}