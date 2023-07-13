
/***************************************************************
Student Name: Alex Reichel
File Name: App.java
Assignment number Project 02

Other comments regarding the file - description of why it is there, etc.
***************************************************************/
package uwf.spellchecker;
import javafx.application.Application;
import java.io.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 * JavaFX App
 */
public class App extends Application {
	
	
	/**
	Hash set container containing all dictionary words
    */
	public    HashSet<String> set ;
	
	/**
	Array List containing all miss spelled words
    */
	public ArrayList<String> missSpell;
	
	/**
	Array List containing all suggested words
    */
	public ArrayList<String> suggestWords;
	

	/**
	Function used to save any text to a specified file
	@param String represents content user wanted saved to a file
	@param File represents file that you want text saved too
    */
	 private void saveTextToFile(String content, File file) {
		 
	        try {
	            PrintWriter writer;
	            writer = new PrintWriter(file);
	            writer.println(content);
	            writer.close();
	        } catch (IOException ex) {
	            
	        }
	    }
	 
	 /**
		Function replaces the char with a specified index 
		@param String represents string that will be converted to char array
		@param ch represents char getting replaced
		@param index represents the index of the char array that you want switched
	  */
	 public String replaceCharUsingCharArray(String str, char ch, int index) {
		 
		    char[] chars = str.toCharArray();
		    chars[index] = ch;
		    return String.valueOf(chars);
		    
		}   
	 
	 
	 /**
		Function that retrieves all words from the dictionary file and adds them to the hash container
	    */
	 public void hashDictionary() {
		 
		 set = new HashSet<String>(); 
		 String word="";
			try {
				
			      File myObj = new File("dictionary.txt");
			      Scanner myReader = new Scanner(myObj);
			      
			      myReader.useDelimiter(" |\n");
			      
			      	int i=1;
			      while (myReader.hasNext()) {
			    	  	i++;
			      		word = myReader.next().trim().toLowerCase();
			      		set.add(word);
			      	}
			      
			
			} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	 }

	 
	 /**
		Function that removes a char based off its index 
	    */
	public static String charRemoveAt(String str, int p) {  
          return str.substring(0, p) + str.substring(p + 1);  
       }  
	 
	 
	/**
	Function that gets all miss spelled words and creates suggestions based off various string permutations 
	Then adds suggested words that could be the possible words
    */
	public void createSuggest() {
		String temp3="";
		String suggest2="";
		 suggestWords = new ArrayList<String>(); 
		
		
		String suggest="";
		String wrongWord="";
		for(int i= 0; i< missSpell.size();i++) {
			
			wrongWord = missSpell.get(i);
			
	        
	        for(int j=0;j<wrongWord.length();j++) {
		        
	        	//certain char off
	        	 for(int l=97;l<123;l++) {
	 	        	 char temp = (char)l;
	 	        	 suggest = replaceCharUsingCharArray(wrongWord, temp, j);
	 	        	 //System.out.println(suggest);
	 	        	 if(set.contains(suggest)) {
	 	        		suggestWords.add(suggest);
	 	        	 }else {
	 	        		 
	 	        	 }
	 	        	
	 	        }
	        	 
	        	 //char missing last element
	        	 for(int o=97;o<123;o++) {
	        		 char temp2 = (char)o;
	        		 suggest2 = wrongWord + temp2;
	        		 if(set.contains(suggest2)) {
		 	        		suggestWords.add(suggest2);
		 	        	 }else {
		 	        		 
		 	        	 }
	 	        }
	        	 
	        	
	        	 String temp2 = wrongWord; 
	        	 temp3 = charRemoveAt(wrongWord, j);
	        	 if(set.contains(temp3)){
	        		 suggestWords.add(temp3);
	        	 }else {
	        		 temp3 = temp2;
	        	 }
	        	 
	        	 char temp4[];
	        	 String temp5="";
	        	 for(int p=0;p<wrongWord.length();p++) {
	        		 	temp4 = swap(wrongWord, j, p);
	        		 	temp5 = new String(temp4);
	        		 	if(set.contains(temp5)) {
	        		 		//System.out.println(temp5);
	        		 		suggestWords.add(temp5);
	        		 	}
	        	 }
	        	 
	        	
	        }     
	        
		}
		
	}
	
	
	
	
	/**
	Function that swaps 2 char in the char array
	@param String represents string to be converted to chr array
	@param int represents first element you want switched
	@param int represents second element you want switched
    */
   public static char[] swap(String str, int i, int j)
	    {
	        char ch[] = str.toCharArray();
	        char temp = ch[i];
	        ch[i] = ch[j];
	        ch[j] = temp;
	        return ch;
	    }
	
	
   
   /**
	Function that prints all possible suggestions
   */
    public String printSuggestions() {
		String suggest1="";
		String suggest="";
		
		Set<String> set = new HashSet<>(suggestWords);
		suggestWords.clear();
		suggestWords.addAll(set);
		
		
		if(missSpell.size() == 0) {
			return "No misspelled words";
		}
		
			for(int i=0;i<suggestWords.size();i++) {
					suggest = suggestWords.get(i);
					suggest1 += suggest + "\n";
					
			}
	return suggest1;
		}
	
    
    
    /**
	Main start function that launches all of java fx creating all of the various features required by the project
    */
    @Override
    public void start(Stage stage) {
    	
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
     
        MenuBar menuBar = new MenuBar();   
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        
        MenuItem openFileItem = new MenuItem("Open");
        MenuItem saveItem =new MenuItem("Save");  
        MenuItem exitItem = new MenuItem("Exit"); 
        fileMenu.getItems().addAll(openFileItem,saveItem, exitItem);
        
        MenuItem spellCheck = new MenuItem("SpellCheck");
        editMenu.getItems().addAll(spellCheck);
        
        menuBar.getMenus().addAll(fileMenu, editMenu);
      
        
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setScrollTop(500);
        textArea.setPrefHeight(500);
    	textArea.setPrefWidth(500);
    
        
 
       FileChooser fileChooser = new FileChooser();
       String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
       fileChooser.setInitialDirectory(new File(currentPath));
      
     
       BorderPane root = new BorderPane();
       root.setTop(menuBar);
       Scene scene = new Scene(root, 350, 200);
       
       root.setCenter(textArea);
       
       missSpell = new ArrayList<String>();
       
       EventHandler<ActionEvent> spellCheckEvent = new EventHandler<ActionEvent>() {
       
     	 
     	            public void handle(ActionEvent e)
     	            {
     	            	String s ="";
     	            	hashDictionary();
     	            	String Content = textArea.getText();
     	            	s = Content.toLowerCase();
     	            	String[] splited = s.split("\\s+");
     	            	int size = splited.length;
     	            	String word ="";
     	            	int misspell =0;
     	            	String message="";
     	            	for (int i = 0; i <size; i++) { 
     	            		word  = splited[i];
     	            		
     	            		
     	            		if(word.charAt(word.length()-1) =='.' || word.charAt(word.length()-1) ==',') {
     	       				
     	       				word = charRemoveAt(word, word.length()-1);
     	       				
     	            		}
     	          
     	                   
     	                   	if(set.contains(word)) {
     	                   		//System.out.println(word + "Was in the dictionary");
     	                   	}else {
     	                   		misspell ++;
     	                   
     	                   	missSpell.add(word);
     	                   Alert alert = new Alert(AlertType.INFORMATION);
     	                   alert.setTitle("Information Dialog");
     	                   alert.setContentText(word + " Was not in the dictionary");
     	                   message += word + " Was not in the dictionary" + "\n";
     	                   //alert.showAndWait();
     	                   	}
     	                   		   
     	               } 
     	            	
     	            	createSuggest();
     	           	   Alert alert = new Alert(AlertType.INFORMATION);
	                   alert.setTitle("Information Dialog");
	                   alert.setContentText(message + "\n" + "Total words misspelled: " + misspell + "\n" + "Sugesstions include: " + "\n" + printSuggestions());
	                   alert.showAndWait();
     	            	
     	             }
     	            
     	        };
     	        
       
       
       
      Label label1 = new Label("no files selected");
      EventHandler<ActionEvent> openEvent = new EventHandler<ActionEvent>() {
    	 
    	  
    	  String mega="";
    	 
    	            public void handle(ActionEvent e)
    	            {
    	 
    	            	
    	                File file = fileChooser.showOpenDialog(stage);               	
    	                if (file != null) {
    	    	                	
    	    	               try (Scanner scanner = new Scanner(file)) {

    	    	                       while (scanner.hasNext()) {
    	    	                            mega +=scanner.next() + "\n" ;
    	    	                        }
    	    	                        
    	    	                        
    	    	                        textArea.appendText(mega);
    	    	                        //System.out.println(mega);
    	    	                        label1.setText(file.getAbsolutePath()+ "  selected");
    	    	                        
    	    	                } catch (FileNotFoundException e2) {
    		                        e2.printStackTrace();
    		                    }   
    	             
    	                	}
    	            }
    	        };
    	        
    	        
    	        EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
    	        
    	      	            public void handle(ActionEvent e)
    	      	            {
    	      	            
    	      	            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    	      	            fileChooser.getExtensionFilters().add(extFilter);
    	      	 
    	      	            //Show save file dialog
    	      	            File file = fileChooser.showSaveDialog(stage);
    	      	 
    	      	            if (file != null) {
    	      	            	
    	      	            	
    	      	                saveTextToFile(textArea.getText(), file);
    	      	            }
    	      	            }
    	      	               
    	      	            
    	      	        };
    	        
    	      
    	      	      exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
    	              // When user click on the Exit item
    	              exitItem.setOnAction(new EventHandler<ActionEvent>() {
    	       
    	                  @Override
    	                  public void handle(ActionEvent event) {
    	                      System.exit(0);
    	                  }
    	              });
    	 
    	        
    	        
    	openFileItem.setOnAction(openEvent);
    	saveItem.setOnAction(saveEvent);
    	
    	spellCheck.setOnAction(spellCheckEvent);
    	
        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        stage.setScene(scene);
      
        stage.show();
    }
    
    

    
    public static void main(String[] args) {
    	
        launch();
        
    }

}