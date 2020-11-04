package application;

import auction.entity.Administrator;
import auction.entity.Bidder;

import java.io.*;

public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Invalid command");
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

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        PropertiesHolder propertiesHolderInst = PropertiesHolder.getPropertiesHolderInst();
        int socketPort = propertiesHolderInst.getPropertyInt("socketPort");

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));


            if (args[0].equals("ADMIN"))
            {
                startAdminInstance(bufferedReader, bufferedWriter);//blocking call

            }else if (args[0].equals("BIDDER") && args.length == BIDDER_CMD_ARGS_NUM){

                startBidderInstance(bufferedReader, bufferedWriter, args[BIDDER_FIRST_NAME_ID],
                        args[BIDDER_LAST_NAME_IND]);//blocking call

            }else{
                bufferedWriter.write("Invalid command");
                bufferedWriter.flush();
                printMenuOptions();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIOStreams(bufferedReader, bufferedWriter);
        }
    }

    /**
     * Creates an ADMIN instance to conduct the auction
     * @param bufferedReader instance of buffered reader to get messages from user
     * @param bufferedWriter instance of buffered writer to write messages to user
     */
    public static void startAdminInstance(BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        Administrator adminInstance = new Administrator();
        adminInstance.startAuction(bufferedReader, bufferedWriter);//blocking call
    }

    /**
     * Creates a BIDDER instance to participate in the auction
     * @param bufferedReader instance of buffered reader to get messages from user
     * @param bufferedWriter instance of buffered writer to write messages to user
     * @param firstName bidder first name
     * @param lastName bidder last name
     */
    public static void startBidderInstance(BufferedReader bufferedReader, BufferedWriter bufferedWriter,
                                           String firstName, String lastName)
    {
        Bidder bidderInstance = new Bidder(firstName, lastName);
        bidderInstance.startAuction(bufferedReader, bufferedWriter);//blocking call
    }

    /**
     * Closes the IO streams
     * @param bufferedReader opened io stream to close
     * @param bufferedWriter opened io stream to close
     */
    public static void closeIOStreams(BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try
        {
            if (bufferedReader != null)
                bufferedReader.close();

            if (bufferedWriter !=null)
                bufferedWriter.close();
        }
        catch (IOException e) {
            //TODO: log the error
        }
    }

    /**
     * Prints to the screen available menu options
     */
    public static void printMenuOptions()
    {
        System.out.println("Please type one of the options:\nADMIN\nBIDDER <first_name> <last_name>");
    }
}
