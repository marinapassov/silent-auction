package auction.requestHandler;

import auction.entity.Auction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents command to print out a list of items for auction
 */
public class ListItemsAdminCommand extends Command{

    @Override
    protected void setCommandPrefix() {
        commandPrefix = "LIST_ITEMS";
    }

    @Override
    protected void setCommandPattern() {
       //commandPattern = Pattern.compile(commandPrefix);

    }

    @Override
    public String toString() {
        return commandPrefix;
    }

    @Override
    public String execute(String commandText, Auction auctionInstance) {
        String commandResponse;

        //if was able to parse correctly
        if (commandText.equals(commandPrefix))
        {
            commandResponse = auctionInstance.listItemsAdmin();
        } else
        {
            commandResponse = getInvalidCommandText();
        }

        return commandResponse;
    }

}
