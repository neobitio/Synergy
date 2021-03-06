package shared.business_entities;

import shared.remote_business_interfaces.RemoteChatInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Nicolai Onov on 5/10/2017.
 */
public class Chat implements BusinessEntity {
    private ArrayList<String> messages;

    public Chat(RemoteChatInterface remoteChat){
        this.messages = new ArrayList<>();

        ArrayList<String> messagesFromServer = null;
        try {
            messagesFromServer = remoteChat.getMessages();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for(String message: messagesFromServer){
            this.messages.add(message);
        }
    }

    public Chat() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public ArrayList<String> getListOfMessages(){
        return this.messages;
    }
}
