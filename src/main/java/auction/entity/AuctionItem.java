package auction.entity;

import application.PropertiesHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents and item in an auction
 */
public class AuctionItem {

    final private String description;
    final private LocalDateTime startAuctionDate, endAuctionDate;

    final private String itemId;

    private double currPrice;
    private String bidderId = "";


    public AuctionItem(String itemId, String description, double startPrice, LocalDateTime startAuctionDate, LocalDateTime endAuctionDate) {
        this.itemId = itemId;
        this.description = description;
        this.currPrice = startPrice;
        this.startAuctionDate = startAuctionDate;
        this.endAuctionDate = endAuctionDate;
    }

    /**
     * Updates item's price to new price if the price is higher than current price
     * @param newPrice new price to update
     * @param newBidderId new bidder id to update
     * @return true if price was updated
     */
    public boolean setCurrPrice(double newPrice, String newBidderId) {

        boolean responseCode = true;

        //making sure only one thread gets the price and bids at a time
        synchronized (this)
        {
            if (currPrice >= newPrice)
                responseCode = false;
            else
            {
                currPrice = newPrice;
                bidderId = newBidderId;
            }
        }

        return responseCode;
    }

    public String getItemId() {
        return itemId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public String getDescription() {
        return description;
    }
    public LocalDateTime getStartAuctionDate() {
        return startAuctionDate;
    }

    public LocalDateTime getEndAuctionDate() {
        return endAuctionDate;
    }

    public String toString()
    {
        String dateTimeFormatStr = PropertiesHolder.getPropertiesHolderInst().getPropertyString("dateTimeFormat");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormatStr);
        return "#ITEM_ID: "+ itemId+ " #DESCRIPTION: " + description + " #PRICE: "+ currPrice + " #END_DATE: "+
                endAuctionDate.format(formatter);
    }
}
