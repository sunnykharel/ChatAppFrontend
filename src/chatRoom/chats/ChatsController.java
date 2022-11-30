package chatRoom.chats;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatsController {
	@FXML VBox ongoingChats;
	
	//you will have to add to ongoing chats while you are here
	//and load each one
	
	
	//make a request to the server here to load all possible 
	//chats and have 1 button for each chat. Clicking each button will 
	//go to the handler with the respective parameters
	
	
	/*
	 * have a borderpane for the chats chats menu
	 * the borderpane also has a back button to go back to
	 * the chat menu
	 * 
	 * somehow: have a way to go back to the 
	 * 
	 */
	
	//need to have 5 chats showing up.
	//if you click one of the chats,
	//you will be directed into a new scene 2
	
	
	public void addTextToChat() {
		ongoingChats.getChildren().add(new TextField());
	}

}
