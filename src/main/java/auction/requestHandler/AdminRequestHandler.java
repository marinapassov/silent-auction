package auction.requestHandler;

import auction.entity.Auction;

import java.util.HashMap;

public class AdminRequestHandler extends RequestHandler {

    public AdminRequestHandler(Auction auctionInstance) {
        super(auctionInstance);
    }

    @Override
    protected void initialiseMenuOptions() {
        //initializes commands
        menuCommands = new HashMap<>();

        Command currCommand = new AddItemCommand();
        menuCommands.put(currCommand.getCommandPrefix(), currCommand);
        currCommand = new ListItemsAdminCommand();
        menuCommands.put(currCommand.getCommandPrefix(), currCommand);
    }
}
