package auction.requestHandler;

import application.PropertiesHolder;
import auction.entity.Auction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents command to add an item to the auction
 */
public class AddItemCommand extends Command{

    final private String DATE_TIME_FORMAT;

    public AddItemCommand()
    {
        super();
        PropertiesHolder propertiesHolderInst = PropertiesHolder.getPropertiesHolderInst();
        DATE_TIME_FORMAT = propertiesHolderInst.getPropertyString("dateTimeFormat");
    }

    @Override
    protected void setCommandPrefix() {
        commandPrefix = "ADD_ITEM";
    }

    @Override
    protected void setCommandPattern() {
       commandPattern = Pattern.compile(commandPrefix + " ID:(.+) D:(.+) P:(.+) S:(.+) E:(.+)");
    }

    @Override
    public String toString() {
        return commandPrefix + " ID:<item id> D:<description> P:<start price> S:<start date in format " + DATE_TIME_FORMAT +
                "> E:<end date in format "+ DATE_TIME_FORMAT + ">";
    }

    @Override
    public String execute(String commandText, Auction auctionInstance) {

        Matcher matcher = commandPattern.matcher(commandText);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String commandResponse;
        String desc, itemId;
        double startPrice;

        LocalDateTime startAuctionDate, endAuctionDate;

        //if was able to parse correctly
        if (matcher.find())
        {
            //any parsing error is treated as invalid command error
            try {
                itemId = matcher.group(1);
                desc = matcher.group(2);
                startPrice = Double.parseDouble(matcher.group(3));
                startAuctionDate = LocalDateTime.parse(matcher.group(4), formatter);
                endAuctionDate = LocalDateTime.parse(matcher.group(5), formatter);
                commandResponse = auctionInstance.addNewItem(itemId, desc, startPrice, startAuctionDate, endAuctionDate);
            }
            catch (Exception e)
            {
                commandResponse = getInvalidCommandText();
            }
        }
        else
        {
            commandResponse = getInvalidCommandText();
        }

        return commandResponse;
    }
}
