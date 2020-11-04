package auction.entity;


import application.PropertiesHolder;
import auction.requestHandler.BidderRequestHandler;
import auction.requestHandler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the auction entity
 */
public class Auction extends Thread{

    private AuctionItemsHandler auctionItemsHandler;
    private ServerSocket serverSocket;
    private boolean isAuctionRunning = false;
    
    public Auction() { }

    /**
     * Creates the item list array and starts the thread to start the auction
     */
    public void startAuction(){
        auctionItemsHandler = new AuctionItemsHandler();
        this.start();
    }

    /**
     * Sets boolean to stop auction and stops the thread
     */
    public void stopAuction(){
        isAuctionRunning = false;
        this.interrupt();//interrupts serverSocket.accept() blocking call
    }

    /**
     * Executed in a thread
     */
    public void run(){

        final int AUTO_BID_FIXED_RATE = 300000;
        Timer autoBidTimer = new Timer();
        AutomaticBot autoBot = new AutomaticBot();

        //creates menu handler
        RequestHandler requestHandler = new BidderRequestHandler(this);

        isAuctionRunning = true;
        Socket serverClientSocket;
        ClientSocketListener clientThread;

        autoBidTimer.scheduleAtFixedRate(autoBot, 0, AUTO_BID_FIXED_RATE);

        //The auction thread is listening for a bidder/administrator connection socket
        while (isAuctionRunning){
            try{
                //Call accept() to receive the next connection
                serverClientSocket = serverSocket.accept();
                clientThread = new ClientSocketListener(serverClientSocket, requestHandler);
                clientThread.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        //closing down the auction
        autoBot.cancel();
        autoBidTimer.cancel();
    }

    /**
     * Tries to create a server socket connection to start the auction
     * @throws IOException if serverSocket fails
     */
    public void tryConnectServerSocket() throws IOException {
        PropertiesHolder propertiesHolderInst = PropertiesHolder.getPropertiesHolderInst();

        serverSocket = new ServerSocket(propertiesHolderInst.getPropertyInt("socketPort"));
    }

    /**
     * Adds a new item to auction item list
     * @param itemId item identifier in items hashmap
     * @param desc item description
     * @param startPrice item start price
     * @param startAuctionDate item start auction date
     * @param endAuctionDate item end auction date
     * @return response text
     */
    public String addNewItem(String itemId, String desc, double startPrice, LocalDateTime startAuctionDate, LocalDateTime endAuctionDate)
    {
        auctionItemsHandler.addNewItem(itemId, desc, startPrice, startAuctionDate,  endAuctionDate);
       
        return "New item has been added to auction";
    }

    /**
     * wrapper method for list item command.
     * First authorises the user and if authorised then prints out the list
     * @param bidderId bidder id
     * @param authToken bidder auth token
     * @return string with list of items or an error message
     */
    public String listItemsWrapper(String bidderId, String authToken)
    {
        String responseMessage;

        //checking authorisation
        if (checkBidderAuthorisation(bidderId,authToken))
            responseMessage = auctionItemsHandler.listItems();
        else
            responseMessage = "ERROR: Invalid token";

        return responseMessage;
    }

    /**
     * wrapper method for place bid on item command.
     * First authorises the user and if authorised then prints out the list
     * @param bidderId bidder id that want to put a bid
     * @param authToken bidder auth token
     * @param itemId item id that the bidder wants to bid on
     * @param bidPrice the new price
     * @return string response or an error message
     */
    public String placeBidWrapper(String bidderId, String authToken, String itemId, double bidPrice)
    {
        String responseMessage;

        //checking authorisation
        if (checkBidderAuthorisation(bidderId, authToken))
            responseMessage = auctionItemsHandler.placeBid(bidderId, itemId, bidPrice);
        else
            responseMessage = "ERROR: Invalid token";

        return responseMessage;
    }

    /**
     * Checks bidder authorisation
     * @param bidderId bidder id
     * @param authToken bidder auth token
     * @return true is valid
     */
    private boolean checkBidderAuthorisation(String bidderId, String authToken)
    {
        //TODO: implement
        return true;
    }

    /**
     * this class raises price of an item every x time
     */
    class AutomaticBot extends TimerTask {

        @Override
        public void run() {

            final int MAX_UP_BID = PropertiesHolder.getPropertiesHolderInst().getPropertyInt("botMaxPriceRaise");
            final String BIDDER_ID = "BOT";

            int randomIndex;
            double newPrice;
            Random randomInstance;

            AuctionItem currItem;

            int numOfItems = auctionItemsHandler.getNumberOfAuctionItems();

            //if the list of items has items
            if (numOfItems > 0) {

                randomInstance = new Random();
                randomIndex = randomInstance.nextInt(numOfItems);
                currItem = auctionItemsHandler.getAuctionItemByKeyIndex(randomIndex);

                //raising the price by random amount
                newPrice = currItem.getCurrPrice() + randomInstance.nextInt(MAX_UP_BID);

                auctionItemsHandler.placeBid(BIDDER_ID, currItem.getBidderId(), newPrice);
            }
        }
    }
}
