package auction.entity;

import auction.requestHandler.RequestHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Listener for client socket communication
 */
public class ClientSocketListener extends Thread {
    final private Socket serverClientSocket;
    final private RequestHandler requestHandler;

    ClientSocketListener(Socket inSocket, RequestHandler requestHandler){
        this.serverClientSocket = inSocket;
        this.requestHandler = requestHandler;
    }

    public void run(){

        DataInputStream inStream = null;
        DataOutputStream outStream = null;

        String clientMessage, serverMessage;

        try{
            inStream = new DataInputStream(serverClientSocket.getInputStream());
            outStream = new DataOutputStream(serverClientSocket.getOutputStream());

            printWelcomeMessage(outStream);

            while (!requestHandler.getIsToExit()){
                clientMessage = inStream.readUTF();
                serverMessage = requestHandler.handleRequest(clientMessage);

                if (serverMessage != null && !serverMessage.isEmpty()) {
                    outStream.writeUTF(serverMessage);
                    outStream.flush();
                }
            }
            inStream.close();
            outStream.close();
            serverClientSocket.close();
        }catch(Exception ex){
            //TODO: refine
            System.out.println(ex.getMessage());
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                serverClientSocket.close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    /**
     * prints out welcome message
     * @param outStream io stream to print to
     * @throws IOException thrown if we can't write or flush the io stream
     */
    public void printWelcomeMessage(DataOutputStream outStream) throws IOException {
        outStream.writeUTF(requestHandler.getWelcomeMessage());
        outStream.flush();
    }

}
