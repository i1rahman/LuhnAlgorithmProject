// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray




import java.util.Scanner;
// More packages may be imported in the space below
import java.io.*;

class CustomerSystem{
    public static void main(String[] args){
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below


        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
		        // Any necessary variables may be added to this if section, but nowhere else in the code
                enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile();
            }
            else{
                System.out.println("Please type in a valid option (A number from 1-9)");
            }

        } while (!userInput.equals(exitCondition));         // Exits once the user types 
        
        reader.close();
        System.out.println("Program Terminated");
    }
    public static void printMenu(){
        System.out.println("Customer and Sales System\n"
        .concat("1. Enter Customer Information\n")
        .concat("2. Generate Customer data file\n")
        .concat("3. Report on total Sales (Not done in this part)\n")
        .concat("4. Check for fraud in sales data (Not done in this part)\n")
        .concat("9. Quit\n")
        .concat("Enter menu option (1-9)\n")
        );
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static void enterCustomerInfo() {
        
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter First Name: ");
        String firstName = reader.nextLine();

        System.out.println("Enter Last Name: ");
        String lastName = reader.nextLine();

        System.out.println("Enter City: ");
        String city = reader.nextLine();

        System.out.println("Enter Postal Code: ");
        String postalCode = reader.nextLine();
        Boolean valid = validatePostalCode(postalCode.substring(0,3));

        while (valid == false) {
            System.out.println("That postal code is invalid. Enter Postal Code: ");
            postalCode = reader.nextLine();
            valid = validatePostalCode(postalCode.substring(0,3));
        }


        System.out.println("Enter Credit Card Number: ");
        String creditCard = reader.nextLine();
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static boolean validatePostalCode(String userPostalCode) throws FileNotFoundException {  

        // Reading file and saving to String
        FileReader file = new FileReader("C:/java/03_Luhn_Algorithm_Assignment/postal_codes.csv");
        Scanner scnr = new Scanner(file);

        String allPostalCodes = "";
        
        int lineNumber = 1;
        while(scnr.hasNextLine()) {

            String line = scnr.nextLine();

            if (lineNumber > 1) {

                String postalCode = line.substring(0,4); 

                allPostalCodes = allPostalCodes + postalCode;

            }

            lineNumber++;
        
        } 

        System.out.println(allPostalCodes);

        // Getting user input and comparing against postal codes

        int match = allPostalCodes.indexOf(userPostalCode);

        if (match == -1)
            return false;
        return true;


    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static void validateCreditCard() {
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static void generateCustomerDataFile() {
    }
    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/
}