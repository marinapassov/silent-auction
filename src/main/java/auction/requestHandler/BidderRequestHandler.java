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

        menuCommands.put(ListItemsCommand.getCommandPrefix(), new ListItemsCommand());
        menuCommands.put(PlaceBidCommand.getCommandPrefix(), new PlaceBidCommand());

    }
}