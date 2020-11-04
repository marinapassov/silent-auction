package auction.entity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuctionItemsHandler {

    final private Map<String, AuctionItem> auctionItems;

    public AuctionItemsHandler() {
        auctionItems = new HashMap<>();

        //TODO: start temp
        auctionItems.put("item1", new AuctionItem("item no 1", 0.0, LocalDateTime.now(),(LocalDateTime.now()).plusMinutes(5)));
        auctionItems.put("item2", new AuctionItem("item no 2", 0.0, LocalDateTime.now(),(LocalDateTime.now()).plusMinutes(5)));
        auctionItems.put("item3", new AuctionItem("item no 3", 0.0, LocalDateTime.now(),(LocalDateTime.now()).plusMinutes(5)));
        //end temp
    }

    /**
     * Adds a new item to auction item list
     * @param itemId item identifier in items hashmap
     * @param desc item description
     * @param startPrice item start price
     * @param startAuctionDate item start auction date
     * @param endAuctionDate item end auction date
     */
    public void addNewItem(String itemId, String desc, double startPrice, LocalDateTime startAuctionDate, LocalDateTime endAuctionDate)
    {
        auctionItems.put(itemId, new AuctionItem(desc, startPrice, startAuctionDate, endAuctionDate));

    }

    /**
     * Creates list of the auction items
     * @return string with list of items for auction
     */
    public String listItems()
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        //goes over all the auction items and gets their details
        auctionItems.forEach((itemId, currItem) ->
                printWriter.println("itemId: "+ itemId + currItem));

        return printWriter.toString();
    }

    public int getNumberOfAuctionItems()
    {
        return auctionItems.size();
    }

    public AuctionItem getAuctionItemByKey(String itemKey){
        return auctionItems.get(itemKey);
    }

    public AuctionItem getAuctionItemByKeyIndex(int keyIndex)
    {
        String itemKey =  (String)auctionItems.keySet().toArray()[keyIndex];
        return getAuctionItemByKey(itemKey);
    }

    /**
     * Placing bid on an item
     * @param bidderId bidder id that want to put a bid
     * @param itemId item id to bid on
     * @param bidPrice item price to bid
     * @return returns response of the request to put a new bid
     */
    public String placeBid(String bidderId, String itemId, double bidPrice)
    {
        AuctionItem currItem;
        String responseMessage;
        boolean isUpdated;

        currItem = auctionItems.get(itemId);

        if (currItem != null)
        {
            //check if the item is still in auction
            if (currItem.getEndAuctionDate().isAfter(LocalDateTime.now())) {

                isUpdated = currItem.setCurrPrice(bidPrice, bidderId);

                // suggested price is lower than current bidden price
                if (!isUpdated) {
                    responseMessage = "The current price is higher than bid price";
                } else {
                    responseMessage = "New price has been set";
                }
            }
            else
            {
                responseMessage = "Item auction has ended";
            }
        }
        else
        {
            responseMessage = "ERROR: Invalid item id";
        }

        return responseMessage;
    }




}
