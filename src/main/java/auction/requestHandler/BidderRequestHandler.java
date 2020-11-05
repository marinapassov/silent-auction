package auction.requestHandler;

import auction.entity.Auction;

import java.util.HashMap;

public class BidderRequestHandler extends RequestHandler {

    public BidderRequestHandler(Auction auctionInstance) {
        super(auctionInstance);
    }


    @Override
    protected void initialiseMenuOptions() {

        //initializes commands
        menuCommands = new HashMap<>();

        Command currCommand = new ListItemsCommand();
        menuCommands.put(currCommand.getCommandPrefix(), currCommand);
        currCommand = new PlaceBidCommand();
        menuCommands.put(currCommand.getCommandPrefix(), currCommand);

    }
}