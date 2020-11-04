package auction.requestHandler;

import auction.entity.Auction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceBidCommand extends Command{
    @Override
    protected void setCommandPrefix() {
        commandPrefix = "PLACE_BID";
    }

    @Override
    protected void setCommandPattern() {
        commandPattern = Pattern.compile(commandPrefix + " (\\w+) (\\w+) (\\w+) (\\w+)");
    }

    @Override
    public String toString() {
        return commandPrefix + " <bidder id> <token> <item id> <bid price>";
    }

    @Override
    public String execute(String commandText, Auction auctionInstance) {
        Matcher matcher = commandPattern.matcher(commandText);
        String commandResponse;
        String bidderId, authToken, itemId;
        double bidPrice;

        //if was able to parse correctly
        if (matcher.find())
        {
            //any parsing error is treated as invalid command error
            try {
                bidderId = matcher.group(matchFieldIndex.BIDDER_ID.value);
                authToken = matcher.group(matchFieldIndex.AUTH_TOKEN.value);
                itemId = matcher.group(matchFieldIndex.ITEM_ID.value);
                bidPrice = Double.parseDouble(matcher.group(matchFieldIndex.BID_RPICE.value));
                commandResponse = auctionInstance.placeBidWrapper(bidderId, authToken, itemId, bidPrice);
            }
            catch (Exception e)
            {

                commandResponse = getInvalidCommandText();
            }
        } else
        {
            commandResponse = getInvalidCommandText();
        }

        return commandResponse;
    }

    enum matchFieldIndex{
        BIDDER_ID(1), AUTH_TOKEN(2),ITEM_ID(3), BID_RPICE(4);


        private int value;

        matchFieldIndex(int value){
            this.value = value;
        }
    }
}
