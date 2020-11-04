package auction.entity;

import application.PropertiesHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Represents a bidder instance
 */
public class Bidder {

    final private String firstName, lastName, userName;
    private AuthorisationToken authToken;

    public Bidder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = firstName.charAt(0) + " " + lastName.charAt(0);
    }

    /**
     * Starts and manages a client socket with the auction
     * @param bufferedReader io stream to read communication
     * @param bufferedWriter io stream to write communication
     */
    public void startAuction(BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        DataInputStream socketInStream;
        DataOutputStream socketOutStream;
        Socket socket;
        boolean isToExit = false;
        String bidderRequest, requestResponse;
        PropertiesHolder propertiesHolderInst = PropertiesHolder.getPropertiesHolderInst();

        registerAuction();//getting valid token

        try{
            socket = new Socket(propertiesHolderInst.getPropertyString("ipAddress"),
                    propertiesHolderInst.getPropertyInt("socketPort"));
            socketInStream = new DataInputStream(socket.getInputStream());
            socketOutStream = new DataOutputStream(socket.getOutputStream());

            while (!isToExit) {
                bidderRequest = bufferedReader.readLine();

                socketOutStream.writeUTF(bidderRequest);
                socketOutStream.flush();

                requestResponse = socketInStream.readUTF();

                if (!requestResponse.isEmpty()) {

                    bufferedWriter.write(requestResponse);
                    bufferedWriter.flush();

                    if (bidderRequest.equals("EXIT"))
                        isToExit = true;

                }
            }
            socketInStream.close();
            socketOutStream.close();
            socket.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Calls a 3rd party application that manages registration to the auction
     */
    private void registerAuction(){
        int newAuthId;
        String newToken;
        LocalDateTime newTokenExpiry;

        //if we need to call 3rd party application to register again
        if (authToken == null || (authToken.getTokenExpiry()).isBefore(LocalDateTime.now()))
        {
            //TODO: call 3rd party to get token and expiry date and delete temp vars
            newAuthId = 123;
            newToken = "temp token";
            newTokenExpiry = (LocalDateTime.now()).plusMinutes(5);
            //end of temp

            authToken = new AuthorisationToken(newAuthId, newToken, newTokenExpiry);
        }
    }
}
