package auction.entity;

import auction.requestHandler.AdminRequestHandler;
import auction.requestHandler.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class represents the administrator role in the silent auction.
 * An administrator can add items to the auction and also shut down the auction
 */
public class Administrator {

    private Auction auctionInstance;

    /**
     * Creates the auction socket and handles administrator commands
     */
    public void startAuction()
    {
        auctionInstance = new Auction();

        try {
            auctionInstance.tryConnectServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if all is good-> continue
        auctionInstance.startAuction();

        handleAdminConsole();//blocking, ends when admin writes "EXIT"
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
     */
    private void handleAdminConsole()
    {
        String adminRequest, requestResponse;

        RequestHandler adminRequestHandler  = new AdminRequestHandler(auctionInstance);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println(adminRequestHandler.getWelcomeMessage());

            // Continue until auction ends
            while (!adminRequestHandler.getIsToExit()) {
                adminRequest = bufferedReader.readLine();
                requestResponse = adminRequestHandler.handleRequest(adminRequest);

                // Checks if there is a response and it's not an empty string
                if (requestResponse != null && !requestResponse.isEmpty()) {
                    System.out.println(requestResponse);
                }
            }
            stopAuction();

        }catch(Exception ex){
            //write the exception to the screen
            //TODO: change text
            System.out.println(ex.getMessage());
        }finally{
            stopAuction();
        }
    }

    //TODO: need the admin to "wake up" every once in a while and go over the list of items that the auction ended and let the bidders know
}
