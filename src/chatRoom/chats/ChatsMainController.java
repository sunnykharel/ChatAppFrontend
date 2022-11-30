package chatRoom.chats;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import chatRoom.Main;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;



/*
 * TODO: Be able to add new chats to the left screen whenever someone adds this user to the chat
 */
public class ChatsMainController implements Initializable{
	@FXML VBox chatsListingVBox;
	@FXML ScrollPane chatsListingScrollPane;
	@FXML VBox vBox1;
	@FXML VBox vBox2;
	@FXML Button button;
	@FXML ScrollPane scrollPane1;
	@FXML ScrollPane scrollPane2;
	//tab 2 variables
	@FXML VBox currentUsersInChat;
	@FXML TextField requestedAdditionalUser;
	@FXML Button addUserButton;
	@FXML TextField chatName;
	@FXML Button nameChatButton;
	@FXML Button createChatButton;
	@FXML Text serverResponseUpdate;
	@FXML Button restartCreateButton;
	
	private int userCount;
	private Label chatLabel;
	private String usersInChat;
	private String chatNameString;
	private boolean LOGGED_OFF;
	private String chatWindow1;
	private String chatWindow2;
	
	@FXML TextField enterMessage1;
	@FXML  TextField enterMessage2;
	@FXML  Button sendMessage1;
	@FXML  Button sendMessage2;
	@FXML  HBox sendMessageBar1;
	@FXML  HBox sendMessageBar2;
	
	@FXML TextField broadCastText;
	@FXML Button broadCastButton;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initialize tab1
		//set the title of vBoxes accordingly by adding a Text/Label to the Children
	
		chatWindow1=null;
		chatWindow2=null;
		LOGGED_OFF=false;
		vBox1.prefWidthProperty().bind(scrollPane1.widthProperty());
		vBox2.prefWidthProperty().bind(scrollPane2.widthProperty());
		chatsListingVBox.prefWidthProperty().bind(chatsListingScrollPane.widthProperty());
	
		//get all chats associated with the user and display
		for(int i = 0; i< Main.ChatIDs.size(); i++) {
			addHBoxToChats(Main.ChatIDs.get(i));
		}
		
		Thread t = new Thread( new ChatsClientHandler());
		t.start();
		//start a thread here to handle the incoming traffic and redirect 
		
		//******************************************//
		//initialize tab 2
		
		userCount = 0;
		chatLabel=new Label("Current Users in Chat");
        HBox hBox=new HBox();
        hBox.getChildren().add(chatLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().add(hBox);
		currentUsersInChat.setSpacing(10);
		usersInChat = "";
		addUserToList(Main.username);		
		}
//*********Tab 2 Methods**********************************************************	
	@FXML public void onBroadCast(){
		String httpReq = "BROADCAST "+Main.username+" :"+this.broadCastText.getText();
		Main.out.println(httpReq);
		Main.out.flush();
		broadCastText.setText("");
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
		serverResponseUpdate.setText("Waiting for server to respond...");

		//send a post request to the server, and the server
		//the usernames will have to be registered in the filesystem
		//This will create a chat and a Corresponding Chat subject	
		//String httpReq = "POST addNewChat "+Main.username+" other users";
		// you will make a request to create the chat.. if it fails, you just have to refresh..
		//basically don't mess up, because you cannot remove users from your list
		String httpReq = "POST createChat "+chatNameString+"_"+usersInChat;
		System.out.println(httpReq);
		Main.out.println(httpReq);
		Main.out.flush();
	}

	/**
	 * the Method only sends the HTTP request, 
	 * handling the response is done in the created thread
	 */
	@FXML public void onAddUser(){
		//have to get a HTTP request from server to see if the user is 
		//currently in our system, if not throw error message
		String addUser = requestedAdditionalUser.getText();
		//first do some checking to see if the user is valid
		//and if the user is not already in the list		
		addUserToList(addUser);
	}
	
	private void addUserToList(String username) {
		if(usersInChat.length()>1) {
			usersInChat+="_"+username;
		}
		else {
			usersInChat+=username;
		}
		userCount++;
		Text text = new Text(""+userCount+". "+ username);
		HBox hBox=new HBox();
        hBox.getChildren().add(text);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().add(hBox);
		currentUsersInChat.setSpacing(10);
		System.out.println(usersInChat);
	}
	
	@FXML public void onRestartCreate(){
		clearChatCreation();
	}
	
	public void clearChatCreation() {
		this.currentUsersInChat.getChildren().clear();
		userCount = 0;
		chatLabel=new Label("Current Users in Chat");
        HBox hBox=new HBox();
        hBox.getChildren().add(chatLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.prefWidthProperty().bind(currentUsersInChat.widthProperty());
		currentUsersInChat.getChildren().add(hBox);
		currentUsersInChat.setSpacing(10);
		usersInChat = "";
		addUserToList(Main.username);
	}
	

//**************************TAB 1 methods	*********************************************************************************
	
//	@FXML TextField enterMessage1;
//	@FXML  TextField enterMessage2;
//	@FXML  Button sendMessage1;
//	@FXML  Button sendMessage2;
//	@FXML  HBox sendMessageBar1;
//	@FXML  HBox sendMessageBar2;
	
	@FXML public void onSendMessage1(){
		String message = enterMessage1.getText();
		enterMessage1.setText("");
		//POST sendMessage chatName message
		this.addRightMsgToVBox(Main.username+"\n"+message, vBox1);
		String httpReq = "POST sendMessage "+this.chatWindow1+" "+message;
		System.out.println(httpReq);
		Main.out.println(httpReq);
		Main.out.flush();
	}
	
	@FXML public void onSendMessage2() {
		String message = enterMessage2.getText();
		enterMessage2.setText("");
		//POST sendMessage chatName message
		this.addRightMsgToVBox(Main.username+"\n"+message, vBox2);
		String httpReq = "POST sendMessage "+this.chatWindow2+" "+message;
		Main.out.println(httpReq);
		Main.out.flush();
	}
	
	@FXML
	public void addLine() {

		addRightMsgToVBox("HelloWorld! \n Hello World", vBox1);
		addLeftMsgToVBox("HelloUniv3rse!", vBox1);
	}

	public void addRightMsgToVBox(String msg, VBox vBox) {
		Label label=new Label(msg);
        HBox hBox=new HBox();
        hBox.getChildren().add(label);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);
	}
	
	public void addLeftMsgToVBox(String msg, VBox vBox) {
		  Label label=new Label(msg);
	        HBox hBox=new HBox();
	        hBox.getChildren().add(label);
	        hBox.setAlignment(Pos.BASELINE_LEFT);
	        vBox.getChildren().add(hBox);
	        vBox.setSpacing(10);
	}
	public void addHBoxToChats(String chatName) {		
		Button button = new Button(chatName);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font(16));
        /*
         * move contents of one chat to the other
         */
        button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				
				
				public void handle(ActionEvent arg0) {
					//this is the case where there are currently no chats
					if(!(button.getText().equals(chatWindow1) || button.getText().equals(chatWindow2))) {
						if(chatWindow1==null) {
							chatWindow1 = button.getText();
							//enter this chat 
							System.out.println(chatWindow1);		
						} else {
							//don't do anything if the chat is already opened.
							if(!button.getText().equals(chatWindow1) || !button.getText().equals(chatWindow2))
								//TODO: move all from vBox1 to vBox2
								vBox2.getChildren().addAll(vBox1.getChildren());
								vBox1.getChildren().clear();
								chatWindow2 = chatWindow1;
								chatWindow1= button.getText();
								System.out.println(chatWindow1+" "+chatWindow2);
						}
						Main.out.println("GET chatContents "+Main.username+"_"+chatWindow1);
						Main.out.flush();
				}			
				    /*
				     * steps required on server side: (user wants to enter chat)
				     * add user to the active members list of chatSubj
				     * send the file of data
				     * 
				     * on this side: move scenes around
				     */	
				}
        	   });
	
		HBox hBox = new HBox();
		hBox.getChildren().add(button);
        button.prefWidthProperty().bind(hBox.widthProperty());
        hBox.prefWidthProperty().bind(chatsListingVBox.widthProperty());
		hBox.setAlignment(Pos.CENTER);
		chatsListingVBox.getChildren().add(hBox);
		chatsListingVBox.setSpacing(20);	
	}

	//ChatsClientHandler class		
	public class ChatsClientHandler implements Runnable{

		@Override
		public void run() {
				//while loop to take in the input from server and act accordingly
				System.out.println("HelloThread!");
				try {
					String input;
					while( (input = Main.in.readLine()) != null) {
						System.out.println(input);
						StringTokenizer parse = new StringTokenizer(input);
						String message = parse.nextToken();
						
						if(message.equals("createChat")) {
							// createChat successMessage failureReason
							String successMessage = parse.nextToken();
							String failureReason = parse.nextToken();
							handleCreateChatMessage(successMessage, failureReason);
						} else if( message.equals("addedToChatNotification")) {
							handleAddedToChat(parse.nextToken());
						} else if( message.equals("initializeChat")) {
							handleInitializeChat(input);
						} else if( message.equals("newMessage")) {
							handleNewMessage(input);
						} else if( message.contentEquals("BROADCAST")) {
							handleBroadCast(input);
						}
							
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		public void handleBroadCast(String input) {
			Platform.runLater(() ->{
				
				//notification
				Label label = new Label(input);
				HBox hBox = new HBox();
				hBox.getChildren().add(label);
				hBox.setAlignment(Pos.CENTER);
				Stage s = new Stage();
				Scene scene = new Scene(hBox);
				s.setScene(scene);
				s.show();							
				PauseTransition delay = new PauseTransition(Duration.seconds(5));
				delay.setOnFinished( event -> s.close() );
				delay.play();
			});
		}
		
		
		public void handleNewMessage(String input) {
			System.out.println(input);
			StringTokenizer parse = new StringTokenizer(input);
			parse.nextToken();
			String chatName = parse.nextToken();
			String message = "";
			while(parse.hasMoreTokens()) {
				message+=" ";
				message+=parse.nextToken();
			}
			addLineToChat(message+" ", chatName);
		}
		
		/*
		 * this method will display the message on the 
		 */
		//initializeChat Chatname line
		public void handleInitializeChat(String input) {
			StringTokenizer parse = new StringTokenizer(input);
			parse.nextToken();
			String chatName = parse.nextToken();
			String message = "";
			while(parse.hasMoreTokens()) {
				message+=" "+parse.nextToken();
			}
			addLineToChat(message+" ", chatName);	
		}
		
		/**
		 * TODO: make this code readable
		 * have to call this message with an extra space added
		 * @param message
		 * @param chatName
		 */
		public void addLineToChat(String message, String chatName) {
			Platform.runLater(() ->{
				
				if(chatWindow1.equals(chatName)) {
					if(message.indexOf(':') != -1) {
						String userWhoSentMessage = message.substring(0, message.indexOf(':'));
						if(userWhoSentMessage.equals(Main.username)) {
							addRightMsgToVBox(message.substring(0, message.indexOf(':')+1)+ "\n"+
												message.substring(message.indexOf(':')+1), vBox1);
						} else {
							addLeftMsgToVBox(message.substring(0, message.indexOf(':')+1)+ "\n"+
									message.substring(message.indexOf(':')+1), vBox1);
						}
					}else addRightMsgToVBox(message, vBox1);
				} else if(chatWindow2.equals(chatName)) {
					if(message.indexOf(':') != -1) {
						String userWhoSentMessage = message.substring(0, message.indexOf(':')+1);
						if(userWhoSentMessage.equals(Main.username)) {
							addRightMsgToVBox(message.substring(0, message.indexOf(':')+1)+ "\n"+
									message.substring(message.indexOf(':')+1), vBox2);
						} else {
							addLeftMsgToVBox(message.substring(0, message.indexOf(':')+1)+ "\n"+
									message.substring(message.indexOf(':')+1), vBox2);
						}
					} else addRightMsgToVBox(message, vBox2);
				} else {
					System.out.println("User has selected chat and left before it could fully load");
				}			
			});		
		}
		
		public void handleAddedToChat(String chatName) {
			Platform.runLater(() ->{
				
				//notification
				Label label = new Label("You've been added to "+ chatName);
				HBox hBox = new HBox();
				hBox.getChildren().add(label);
				hBox.setAlignment(Pos.CENTER);
				Stage s = new Stage();
				Scene scene = new Scene(hBox);
				s.setScene(scene);
				s.show();							
				PauseTransition delay = new PauseTransition(Duration.seconds(5));
				delay.setOnFinished( event -> s.close() );
				delay.play();
				
				//add new chat to list
				addHBoxToChats(chatName);
			});
		}
		
		public void handleCreateChatMessage(String successMessage, String failureReason) {
			if(successMessage.equals("fail")){
				if(failureReason.equals("chatNameTaken")) {
					Platform.runLater(() -> {serverResponseUpdate.setText("Chat Name Taken... Retry with valid parameters");
					clearChatCreation();		  
					});
				} else if(failureReason.equals("invalidUsername")) {
					Platform.runLater(() -> {serverResponseUpdate.setText("Invalid username entered... Retry with valid usernames");
					clearChatCreation();  
					});
				}
			}else if(successMessage.equals("success")) {
				Platform.runLater(() -> {serverResponseUpdate.setText("Chat created successfully... Go to Chats menu to enter");
				clearChatCreation();  
				});
			}
			
		}
	}
}
