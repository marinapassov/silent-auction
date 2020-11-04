package auction.requestHandler;

import auction.entity.Auction;

import java.util.regex.Pattern;

/**
 * Represents an abstract Command
 */
abstract public class Command {
    static protected String commandPrefix;
    static protected Pattern commandPattern;

    public Command(){
        setCommandPrefix();
        setCommandPattern();
    }

    /**
     * Sets command prefix
     */
    abstract protected void setCommandPrefix();

    /**
     * Sets command string pattern
     */
    abstract protected void setCommandPattern();

    /**
     * Converts command's pattern to string
     * @return string representation of the command pattern
     */
    abstract public String toString();

    /**
     * Executes the command
     * @param commandText string received from user
     * @param auctionInstance the auction instance that is being called to execute the command
     * @return command response
     */
    abstract public String execute(String commandText, Auction auctionInstance);

    /**
     * String represents invalid command text
     * @return invalid command string
     */
    public String getInvalidCommandText()
    {
        return "Invalid command";
    }

    /**
     * Returns command prefix that is used to identify the specific command
     * @return returns the prefix string
     */
    static public String getCommandPrefix()
    {
        return commandPrefix;
    }
}
