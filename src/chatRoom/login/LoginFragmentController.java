package chatRoom.login;



/**
 * bugs: cannot handle a username of length 1
 * The error-response is not fully created.
 * 
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import chatRoom.Main;
import chatRoom.chats.ChatsController;
public class LoginFragmentController {
	@FXML TextField username;
	@FXML PasswordField password;
	@FXML Text errorMessage;
	
	@FXML TextField ipAddressText;
	
	@FXML public void onConnect() {
		Main.setUpNetworking(ipAddressText.getText());
	}
	
	
	@FXML public void onSignIn() {
		errorMessage.setText("");
	
		String loginName = username.getText();
		String loginPassword = password.getText();
		String httpReq = "GET login "+loginName+"_"+loginPassword;
		Main.out.println(httpReq);
		Main.out.flush();
		
		try {
			String serverResponse = Main.in.readLine();
System.out.println(serverResponse);
			StringTokenizer parse = new StringTokenizer(serverResponse);
			String message = parse.nextToken();
			
			if(message==null) {
				errorMessage.setText("Whoops... Something went wrong");
			} else if (message.equals("usernameNotFound")) {
				errorMessage.setText("The username you "
						+ "typed in does not match any account");
			} else if(message.equals("wrongPassword")) {
				errorMessage.setText("The password you've entered is incorrect");
			} else if(message.equals("loginAccepted")) {
				//do things to login
				Main.username= loginName;
				String chatIDs= parse.nextToken();
				if(!chatIDs.equals("noChats")) {
					Main.ChatIDs = new ArrayList<String>(Arrays.asList(chatIDs.split("_")));
					System.out.println(Main.ChatIDs);
				} else {
					Main.ChatIDs = new ArrayList<String>();
				}
				
				//go to the next page
				try {	
					Main.showChatsMenu();
				} catch (IOException e) {
					System.err.println("error loading chats menu "+e );
				}
				
			}
			
		} catch(IOException e) {
			System.err.println("error signing in ..."+ e);
			errorMessage.setText("Whoops... Something went wrong");
		}		
	}
	
	@FXML public void onSignUp() {
		try {
			Main.showSignUpMenu();
		} catch (IOException e) {
			System.err.println("error loading signup menu "+e );
		}
	}
	
	
}
