package chatRoom.signup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import chatRoom.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpFragmentController {
	@FXML TextField username;
	@FXML PasswordField password; 
	@FXML Text userNameTakenMessage;
	@FXML Text errorMessage;
	
	@FXML public void onSignUp() {
		
		
		
		String requestedUsername = username.getText();
		String requestedPassword = password.getText();
		String httpReq = "POST addUser "+requestedUsername+"_"+requestedPassword;
		Main.out.println(httpReq);
		Main.out.flush();
		try {
			String serverResponse = Main.in.readLine().replaceAll("[^A-Za-z0-9]", "");
			if(serverResponse==null) {
				errorMessage.setText("Something wrong happened");
			}
			else if(serverResponse.equals("UsernameTaken")) {
				userNameTakenMessage.setText("Username taken");
			}		
			else if(serverResponse.equals("UsernameAdded")) {
				System.out.println("User successfully added");
				errorMessage.setText("Account successfully created... /n click signin to enter");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void onSignIn() {
		try {
			Main.showLoginPage();
		} catch (IOException e) {
			System.err.println("error loading signin menu "+e );
		}
	}
}
