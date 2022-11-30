package chatRoom.chats;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import chatRoom.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/*
 * unimplemented edge cases:
 * Two users try to create a chat with the same name.
 * 
 */

public class AddChatWindowController implements Initializable {
	@FXML VBox currentUsersInChat;
	@FXML TextField requestedAdditionalUser;
	@FXML Button addUserButton;
	@FXML TextField chatName;
	@FXML Button nameChatButton;
	@FXML Button createChatButton;
	private int userCount;
	private Label chatLabel;
	private String usersInChat;
	private String chatNameString;
	public boolean NOTIFIED_OF_ADDCHAT_STATUS;
	private boolean ADD_CHAT_SUCCESS_MESSAGE;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Main.config.setAddChatWindowController(this);
		userCount = 0;
		chatLabel=new Label("Current Users in Chat");
        HBox hBox=new HBox();
        hBox.getChildren().add(chatLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().add(hBox);
		currentUsersInChat.setSpacing(10);
		usersInChat = "";
		usersInChat = Main.username;
		addUserToList(Main.username);
	}
	
	public void helloWorld() {
		System.out.println("Hellosfsdfworld!");
	}
	
	@FXML public void onNameChat() {
		chatNameString = chatName.getText();
		chatLabel = new Label(chatNameString);
        HBox hBox=new HBox();
        hBox.getChildren().add(chatLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().set(0, hBox);	
	}
	
	@FXML public void onCreateChat() {
		// you will make a request to create the chat.. if it fails, you just have to refresh..
		//basically don't mess up, because you cannot remove users from your list
		String httpReq = "POST createChat "+chatNameString+" "+usersInChat;
		Main.out.println(httpReq);
		Main.out.flush();
		//there's a problem here... you you now need to have the MAIN in reader somewhere else to 
		//redirect the traffic
		
		while(NOTIFIED_OF_ADDCHAT_STATUS==false) {			
		}//poll server to wait for notification
		NOTIFIED_OF_ADDCHAT_STATUS=false;
		if(this.ADD_CHAT_SUCCESS_MESSAGE==true) {
			//add the chat to the list of ChatIDs
			//display chat successfully added
			//close after a few seconds
		}
		else {
			//Say error in making the chat
		}
		
		
		try {
			String serverResponse = Main.in.readLine();//
		} catch(IOException e) {
			
		}
		
		
		
	}

	@FXML public void onAddUser(){
		//have to get a HTTP request from server to see if the user is 
		//currently in our system, if not throw error message
		String addUser = requestedAdditionalUser.getText();
		//first do some checking to see if the user is valid
		//and if the user is not already in the list
		
		addUserToList(addUser);
	}
	
	private void addUserToList(String username) {
		usersInChat+="_"+username;
		userCount++;
		Text text = new Text(""+userCount+". "+ username);
		HBox hBox=new HBox();
        hBox.getChildren().add(text);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().add(hBox);
		currentUsersInChat.setSpacing(10);
	}
	public void setSuccessMessage(boolean b) {
		this.ADD_CHAT_SUCCESS_MESSAGE=b;
	}

}
