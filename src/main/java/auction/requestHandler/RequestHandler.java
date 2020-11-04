package auction.requestHandler;

import auction.entity.Auction;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

/**
 * Represents a handler to handle requests either from administrator instance or bidder instance
 */
abstract public class RequestHandler {

    private boolean isToExit;
    final private Auction auctionInstance;
    protected String availableCommandsMenuStr;

    protected Map<String, Command> menuCommands;

    abstract protected void initialiseMenuOptions();//to be implemented in a concrete class

    public RequestHandler(Auction auctionInstance)
    {
        this.auctionInstance = auctionInstance;
        initialiseMenuOptions();
        setAvailableCommandsMenuStr(); //creates a string to represent available commands
        isToExit = false;
    }

    /**
     * Returns whether to exit the auction
     * @return boolean to whether to exit the auction
     */
    public boolean getIsToExit()
    {
        return isToExit;
    }


    /**
     * Creates a string that is printed to the user
     * @return welcome string and available commands
     */
    public String getWelcomeMessage()
    {
        return "Welcome!\n"+printoutMenu();
    }

    /**
     *
     * @return string representation of the available commands
     */
    private String printoutMenu()
    {
        return availableCommandsMenuStr;
    }

    /**
     * This method should only run once to create a string representation of the available commands
     */
    private void setAvailableCommandsMenuStr()
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Collection<Command> commands = menuCommands.values();

        //goes over all the auction items and gets their details
        for (Command currCommand : commands) {
            printWriter.println(currCommand.toString());
        }

        //last option is EXIT
        printWriter.println("EXIT");

        //stores in a variable for future use
        availableCommandsMenuStr = printWriter.toString();

    }

    /**
     * Handles user's request
     * @param clientMessage the string coming for the user
     * @return response text after executing the request
     */
    public String handleRequest(String clientMessage){

        String commandName;
        String response = "";
        int spaceInd;
        Command currCommand;

        //checks if the bidder wants to exit
        if (clientMessage.equals("EXIT"))
        {
            isToExit = true;
            response = "GOODBYE";
        }else {
            //parses the request string
            if ((spaceInd = clientMessage.indexOf(' ')) > 0) {
                commandName = clientMessage.substring(0, spaceInd);
                currCommand = menuCommands.get(commandName);

                if (currCommand != null) {
                    response = currCommand.execute(clientMessage, auctionInstance);
                } else {
                    response = printoutMenu();
                }
            }
        }

        return response;
    }
}
