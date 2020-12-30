

 public class Main {
 
 public static void main(String[] args) { // TODO Auto-generated method stub
 SearchTree tree = new SearchTree(); //tree.add("egg");
 
		/*
		 * tree.add("11"); tree.add("6"); tree.add("19"); tree.add("4"); tree.add("8");
		 * tree.add("17"); tree.add("43"); tree.add("5"); tree.add("10");
		 * tree.add("31"); tree.add("49");
		 */
		
		 System.out.println(tree.add("M"));
		 System.out.println(tree.add("g"));
		 System.out.println(tree.add("u"));
		/*
		 * System.out.println(tree.add("C")); tree.add("K"); tree.add("O");
		 * tree.add("X"); tree.add("a"); tree.add("i"); tree.add("q"); tree.add("w");
		 */
		 System.out.println(tree.find("j"));
		 //System.out.println(tree.add("J"));
		 System.out.println(tree.find("g"));
		 
  //tree.add("koo"); //System.out.println(tree.add("g"));
  //System.out.println(tree.add("f")); //System.out.println(tree.add("e"));
  //System.out.println(tree.add("d")); //System.out.println(tree.add("c"));
  //System.out.println(tree.add("b")); //System.out.println(tree.add("a"));
  
  
		
		 //System.out.println(tree.find("lentil"));
		// System.out.println(tree.find("U"));
		 /* System.out.println(tree.find("lentil"));
		 * System.out.println(tree.find("apple")); System.out.println(tree.find("yam"));
		 */
  
  //System.out.println(tree.find("j"));
  //System.out.println(tree.find("   alice"));
  //System.out.println(tree.find("dave"));
  //System.out.println(tree.find("joe"));
  
  System.out.println(tree.printTree()); 
  //tree.reset();
 
 }
  
 }
 

/*
 * import java.io.*; import java.util.*;
 * 
 * public class Main {
 * 
 * // Retrieve data to the end of the line as an argument for a method call //
 * Include two special kinds of arguments: // "null" asks us to return no string
 * // "empty" asks us to return an empty string
 * 
 * private static String getEndingString(Scanner userInput ) { String
 * userArgument = null;
 * 
 * userArgument = userInput.nextLine(); userArgument = userArgument.trim();
 * 
 * // Include a "hack" to provide null and empty strings for testing if
 * (userArgument.equalsIgnoreCase("empty")) { userArgument = ""; } else if
 * (userArgument.equalsIgnoreCase("null")) { userArgument = null; }
 * 
 * return userArgument; }
 * 
 * // Main program to process user commands. // This method is not robust. When
 * it asks for a command, it expects all arguments to be there. // It is a
 * quickly-done test harness rather than a full solution for an assignment.
 * 
 * public static void main(String[] args) { // Command options
 * 
 * String addCommand = "add"; String findCommand = "find"; String resetCommand =
 * "reset"; String printCommand = "print"; String quitCommand = "quit";
 * 
 * // Define variables to manage user input
 * 
 * String userCommand = ""; String userArgument = ""; Scanner userInput = new
 * Scanner( System.in );
 * 
 * // Define the recommender that we will be testing.
 * 
 * SearchTree theTree = new SearchTree();
 * 
 * // Define variables to catch the return values of the methods
 * 
 * boolean booleanOutcome; Integer findOutcome;
 * 
 * // Let the user know how to use this interface
 * 
 * System.out.println("Commands available:"); System.out.println("  " +
 * addCommand + " <string to the end of line>"); System.out.println("  " +
 * findCommand + " <string to the end of line>"); System.out.println("  " +
 * resetCommand ); System.out.println("  " + printCommand );
 * System.out.println("  " + quitCommand);
 * 
 * // Process the user input until they provide the command "quit"
 * 
 * do { // Find out what the user wants to do userCommand = userInput.next();
 * 
 * Do what the user asked for.
 * 
 * if (userCommand.equalsIgnoreCase(addCommand)) { // Get the parameters.
 * 
 * userArgument = getEndingString( userInput );
 * 
 * // Call the method
 * 
 * booleanOutcome = theTree.add( userArgument ); System.out.println(userCommand
 * + " \""+userArgument+"\" outcome " + booleanOutcome ); } else if
 * (userCommand.equalsIgnoreCase(findCommand)) { // Get the parameters.
 * 
 * userArgument = getEndingString( userInput );
 * 
 * // Call the method
 * 
 * findOutcome = theTree.find( userArgument ); System.out.println(userCommand +
 * " \""+userArgument+"\" outcome " + findOutcome ); } else if
 * (userCommand.equalsIgnoreCase(resetCommand)) { // Clear the rest of the line
 * text.
 * 
 * userArgument = getEndingString( userInput );
 * 
 * // Call the method
 * 
 * theTree.reset( ); System.out.println(userCommand + " \""+userArgument+"\"" );
 * } else if (userCommand.equalsIgnoreCase(printCommand)) { // Clear the rest of
 * the line text.
 * 
 * userArgument = getEndingString( userInput );
 * 
 * // Call the method
 * 
 * String treeStructure; treeStructure = theTree.printTree( );
 * System.out.println(userCommand + " \""+userArgument+"\" outcome " );
 * System.out.println(treeStructure); } else if
 * (userCommand.equalsIgnoreCase(quitCommand)) { System.out.println (
 * userCommand ); } else { System.out.println ("Bad command: " + userCommand); }
 * } while (!userCommand.equalsIgnoreCase("quit"));
 * 
 * // The user is done so close the stream of user input before ending.
 * 
 * userInput.close(); } }
 */