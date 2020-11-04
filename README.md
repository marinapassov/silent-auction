# Silent Auction Application

#### Table of Contents  
1. [Run And Build](#run-and-build)  
2. [Further Work](#further-work)  
3. [Basic Diagram](#basic-diagram)  
4. [Assumptions](#assumptions)  

This application is an implementation exercise to build an auction with two types of participants: 
Administrator (which adds items to bid) and bidder(s) (which can view the auctioned items and bid).
I have decided to start with simple implementation of server and client sockets.
When Administrator application starts up, it creates an Auction instance in a new thread that will manage the auction. 
The Administrator communicates with the auction using a request handler which listens for user commands and passes them to auction instance.
The Auction has a server socket which listens to connection requests for bidders, and for each client it creates a new client socket to listen to. 
The Bidder uses a request handler to pass user commands to auction.
The auction also has an auto-bot (extends TimerTask) that bids on available items each x time.

I created an abstract class for handling requests with two concrete classes: RequestHandler, AdministratorRequestHandler and BidderRequestHandler.

I utilized Command Factory design pattern for the user commands coming from Administrator and Bidder in order to enforce shared attributes set and expose expected methods from a command class type. 
In addition, it enables to easily add more possible commands in the future without touching code on existing classes.

### Run and Build
* Using Docker: TBC

* Using Java
	* Build: `./gradlew clean jar` on the build. This will build a jar under `libs`.
	* Run: <BR>	Administrator: `java -jar silent-auction-1.0.jar ADMIN`<BR>
			    Bidder: `java -jar silent-auction-1.0.jar BIDDER <first name> <last name>`

* Running Tests: TBC

### Further-Work
1. Utilize SpringBoot framework to add Restful services for Administrator and Bidder users
2. Fix 3rd party service for authorization

### Basic Diagram
<img src="https://github.com/marinapassov/silent-auction/raw/master/basic%20diagram.jpg"/>

### Assumptions
for simplicity, assuming the auction ends at the same day the item is listed