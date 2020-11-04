package auction.entity;

import auction.requestHandler.AdminRequestHandler;
import auction.requestHandler.RequestHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class represents the administrator role in the silent auction.
 * An administrator can add items to the auction and also shut down the auction
 */
public class Administrator {

    private Auction auctionInstance;

    /**
     * Creates the auction socket and handles administrator commands
     * @param bufferedReader instance of buffered reader to get messages from user
     * @param bufferedWriter instance of buffered writer to write messages to user
     */
    public void startAuction(BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        auctionInstance = new Auction();

        try {
            auctionInstance.tryConnectServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if all is good-> continue
        auctionInstance.startAuction();

        handleAdminConsole(bufferedReader, bufferedWriter);//blocking, ends when admin writes "EXIT"
    }

    /**
     * Commands the auction instance to stop the auction
     */
    public void stopAuction()
    {
        auctionInstance.stopAuction();
    }

    /**
     * Handles administrator's requests
     * @param bufferedReader instance of buffered reader to get messages from user
     * @param bufferedWriter instance of buffered writer to write messages to user
     */
    private void handleAdminConsole(BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        String adminRequest, requestResponse;

        RequestHandler adminRequestHandler  = new AdminRequestHandler(auctionInstance);

        try{
            bufferedWriter.write(adminRequestHandler.getWelcomeMessage());
            bufferedWriter.flush();

            // Continue until auction ends
            while (!adminRequestHandler.getIsToExit()) {
                adminRequest = bufferedReader.readLine();
                requestResponse = adminRequestHandler.handleRequest(adminRequest);

                // Checks if there is a response and it's not an empty string
                if (requestResponse != null && !requestResponse.isEmpty()) {
                    bufferedWriter.write(requestResponse);
                    bufferedWriter.flush();
                }
            }
            stopAuction();

        }catch(Exception ex){
            //write the exception to the screen
            try {
                bufferedWriter.write(ex.getMessage());
                bufferedWriter.flush();
                stopAuction();
            } catch (IOException e) {
                //do nothing
                //TODO: log error
            }
        }finally{
            stopAuction();
        }
    }

    //TODO: need the admin to "wake up" every once in a while and go over the list of items that the auction ended and let the bidders know
}
