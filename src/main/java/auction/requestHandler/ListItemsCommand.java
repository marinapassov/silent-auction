package auction.requestHandler;

import auction.entity.Auction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents command to print out a list of items for auction
 */
public class ListItemsCommand extends Command{

    @Override
    protected void setCommandPrefix() {
        commandPrefix = "LIST_ITEMS";
    }

    @Override
    protected void setCommandPattern() {
        commandPattern = Pattern.compile(commandPrefix + " (\\w+) (\\w+)");

    }

    @Override
    public String toString() {
        return commandPrefix + " <bidder id> <token>";
    }

    @Override
    public String execute(String commandText, Auction auctionInstance) {
        Matcher matcher = commandPattern.matcher(commandText);
        String commandResponse;
        String bidderId, authToken;

        //if was able to parse correctly
        if (matcher.find())
        {
            bidderId = matcher.group(matchFieldIndex.BIDDER_ID.value);
            authToken = matcher.group(matchFieldIndex.AUTH_TOKEN.value);
            commandResponse = auctionInstance.listItemsWrapper(bidderId,authToken);
        } else
        {
            commandResponse = getInvalidCommandText();
        }

        return commandResponse;

    }

    enum matchFieldIndex{
        BIDDER_ID(1), AUTH_TOKEN(2);

        private int value;

        matchFieldIndex(int value){
            this.value = value;
        }
    }

}
