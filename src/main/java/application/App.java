package application;

import auction.entity.Administrator;
import auction.entity.Bidder;

public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Invalid command\n");
            printMenuOptions();
        } else {
            parseAndExecuteCommand(args);
        }
    }

    /**
     * Parses args from user and executes the correct command
     *
     * @param args array of string arguments
     */

    public static void parseAndExecuteCommand(String[] args)
    {
        final int BIDDER_CMD_ARGS_NUM = 3, BIDDER_FIRST_NAME_ID = 1, BIDDER_LAST_NAME_IND=2;

        PropertiesHolder propertiesHolderInst = PropertiesHolder.getPropertiesHolderInst();
        int socketPort = propertiesHolderInst.getPropertyInt("socketPort");

        if (args[0].equals("ADMIN"))
        {
            startAdminInstance();//blocking call

        }else if (args[0].equals("BIDDER") && args.length == BIDDER_CMD_ARGS_NUM){

            startBidderInstance(args[BIDDER_FIRST_NAME_ID], args[BIDDER_LAST_NAME_IND]);//blocking call

        }else{
            System.out.println("Invalid command\n");

            printMenuOptions();
        }


    }

    /**
     * Creates an ADMIN instance to conduct the auction
     */
    public static void startAdminInstance()
    {
        Administrator adminInstance = new Administrator();
        adminInstance.startAuction();//blocking call
    }

    /**
     * Creates a BIDDER instance to participate in the auction
     * @param firstName bidder first name
     * @param lastName bidder last name
     */
    public static void startBidderInstance(String firstName, String lastName)
    {
        Bidder bidderInstance = new Bidder(firstName, lastName);
        bidderInstance.startAuction();//blocking call
    }

    /**
     * Prints to the screen available menu options
     */
    public static void printMenuOptions()
    {
        System.out.println("Please type one of the options:\nADMIN\nBIDDER <first_name> <last_name>");
    }
}
