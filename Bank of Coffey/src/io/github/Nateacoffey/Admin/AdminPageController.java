package io.github.Nateacoffey.Admin;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import io.github.Nateacoffey.Sorting.BubbleSort;
import io.github.Nateacoffey.Sorting.InsertionSort;
import io.github.Nateacoffey.Sorting.MergeSort;
import io.github.Nateacoffey.Sorting.QuickSort;
import javafx.event.ActionEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdminPageController implements Initializable {
	
	DatabaseUsersInformation allUserInformation;
	
	@FXML private ChoiceBox<String> sortType;
	@FXML private ChoiceBox<String> category;
	
	@FXML private TextArea sortedDatabaseDisplay;
	@FXML private TextField timeDisplay;
	
	private List<DatabaseUsersInformation> databaseInformation = new ArrayList<DatabaseUsersInformation>(50);
	DatabaseUsersInformation[] copiedArray;
	
	public void searchSort(ActionEvent e) {
		
		sortedDatabaseDisplay.setText("");
		
		//convert arraylist to an array before manipulation and timing
		ArrayListToArray toArray = new ArrayListToArray();
		
		copiedArray = toArray.toArray(databaseInformation);
		
		String categoryChoice = category.getValue();
		String sortTypeChoice = sortType.getValue();
		
		
		if(categoryChoice != null && sortTypeChoice != null && databaseInformation.size() > 0) {
			long startTime = System.nanoTime();
			
			switch(sortTypeChoice) {
				case "Bubble Sort":
					BubbleSort bubbleSort = new BubbleSort();
					copiedArray = bubbleSort.sort(copiedArray, categoryChoice);
					break;
				case "Merge Sort":
					MergeSort mergeSort = new MergeSort();
					copiedArray = mergeSort.sort(copiedArray, categoryChoice);
					break;
				case "Insertion Sort":
					InsertionSort insertionSort = new InsertionSort();
					copiedArray = insertionSort.sort(copiedArray, categoryChoice);
					break;
				case "Quick Sort":
					QuickSort quickSort = new QuickSort();
					copiedArray = quickSort.sort(copiedArray, categoryChoice);
					break;
				default://no sort
					break;
			};
			
			
			//end time before the print
			long endTime = System.nanoTime();
			
			//turn to microseconds
			long duration = (endTime - startTime) / 1000;
			
			int arrayLength = copiedArray.length;
			
			for(int i = 0; i < arrayLength; i++) {
				sortedDatabaseDisplay.setText(sortedDatabaseDisplay.getText() + copiedArray[i].toString());
			}
			
			timeDisplay.setText("Time elapsed: " + duration + "μs");
			
		} else if(databaseInformation.size() < 1) {
			sortedDatabaseDisplay.setText("Database is empty. Please create new accounts.");
		} else {
			sortedDatabaseDisplay.setText("Please select a category and sort type");
		}
		
	}
	
	
	public void fillDatabaseArray(ResultSet rs) throws SQLException {
		rs.next();
		
		while(rs.next()) {
			allUserInformation = new DatabaseUsersInformation(rs.getInt("AMOUNT_OF_ACCOUNTS"),
												rs.getString("FIRST_NAME"),
												rs.getString("LAST_LOGIN"),
												rs.getString("STATE"),
												rs.getInt("ZIP_CODE") );
			
			databaseInformation.add(allUserInformation);
		}
		
	}
	
	public void toLogInScreen(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/io/github/Nateacoffey/Application/LogInScreen.fxml"));
		Parent root = loader.load();
		
		Scene accountPageScene = new Scene(root);
		
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		//empties user data before switching scenes
		databaseInformation = null;
		copiedArray = null;
		
		window.setScene(accountPageScene);
		window.show();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//sorting types
		ObservableList<String> sortList = FXCollections.observableArrayList();
		
		sortList.removeAll(sortList);
		String[] sortNames = {	"No Sort",
								"Bubble Sort",
								"Insertion Sort",
								"Merge Sort",
								"Quick Sort"};
		
		sortList.addAll(sortNames);
		
		sortType.getItems().addAll(sortList);
		
		
		//columns to sort by
		ObservableList<String> columnList = FXCollections.observableArrayList();
		
		columnList.removeAll(columnList);
		String[] columnNames = {"Amount of Accounts", "First Name", "Latest Login", "State", "Zip Code"};
		
		columnList.addAll(columnNames);
		
		category.getItems().addAll(columnList);
	}
	
}
