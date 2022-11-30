package chatRoom;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.glass.ui.Window;
import com.sun.javafx.geom.Rectangle;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.geometry.Insets;
import chatRoom.chats.ChatsController;
import chatRoom.chats.ChatsGlobalConfig;

public class Main extends Application {
	

	//pause.setOnFinished(e -> window.hide());
	
	//textfield and a send button on the bottom of the scroll pane
	
	
	
	private static Stage primaryStage;
	public static BorderPane mainLayout; //revert back to borderpane
	public static BufferedReader in;
	public static PrintWriter out;
	public static ArrayList<String> ChatIDs;
	public static String username;
	public static ChatsGlobalConfig config;
    public String IPAddress;



    
    public static void main(String[] args) {
        launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
//		
//		
//		ImageView lambView = new ImageView();
//
//        ImageView meatView = new ImageView( );
//
//        Button button = new Button("Lamb,\nit's what's for dinner");
//        button.setContentDisplay(ContentDisplay.TOP);
//        button.setTextAlignment(TextAlignment.CENTER);
//        button.setFont(Font.font(16));
//
//        button.graphicProperty().bind(
//                Bindings.when(
//                        button.hoverProperty()
//                )
//                        .then(meatView)
//                        .otherwise(lambView)
//        );
//
//		
//        StackPane layout = new StackPane(button);
//        layout.setPadding(new Insets(30));
//
//        primaryStage.setScene(new Scene(layout));
//        primaryStage.show();
		Main.primaryStage= primaryStage;
		primaryStage.setTitle("Chat Room App");	
		showMainView();
		showLoginPage();
		//setUpNetworking();
		initializeVariables();	
		
//		int arr[] = {1,0,0,1,0,1};
//		int n = 6;
//		int i = 0;
//		int count = 0;
//		while(i< n && arr[i]!=1) {
//			i++;
//		}
//		
//		while(i<n) {
//			while(i<n && arr[i]!=1) {
//				count++;
//				i++;
//			}
//			count++;
//			i++;
//		}
//		System.out.println(count);
	}
	
	private void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainView.fxml"));
		//now BorderPane equals the MainView.FXML
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void showLoginPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("login/LoginFragment.fxml"));
		AnchorPane loginPage = loader.load();
		//put the mainItems into the center of the mainLayout
		mainLayout.setCenter(loginPage);
	}
	
	public static void showChatsMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("chats/ChatsMain.fxml"));
		TabPane chatsMenu = loader.load();
		//put the mainItems into the center of the mainLayout
		primaryStage.setScene(new Scene(chatsMenu));
		primaryStage.show();
		//mainLayout.setCenter(chatsMenu);
		
		//chatController.addTextToChat(); 
	}
	
	public static void showSignUpMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("signup/SignUpFragment.fxml"));
		AnchorPane signUpFragment = loader.load();
		//put the mainItems into the center of the mainLayout
		mainLayout.setCenter(signUpFragment);
	}
	
	public static void setUpNetworking(String ipAddress) {
		try {
			Socket socket = new Socket(ipAddress, 8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
//			String httpReq = "POST addUser aaa_bbb";
//			Main.out.println(httpReq);
//			//Main.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void initializeVariables() {
		ChatIDs = new ArrayList<String>();
	}
	
	public static void showChatMenu() {
		//make a vbox, add all of the chat names, have onClick capabilities for each of them
	}


	

}
