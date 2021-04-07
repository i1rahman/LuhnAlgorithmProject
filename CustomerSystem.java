// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray




import java.util.Scanner;
// More packages may be imported in the space below
import java.io.*;

class CustomerSystem {

    static String allPostalCodeCache = null;
    public static void main(String[] args){
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below
        String customerCollection = "";
        int customerCounter = 1;
       
        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
		        // Any necessary variables may be added to this if section, but nowhere else in the code
                customerCollection += customerCounter++ + ", " + enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile(customerCollection);
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
    public static String enterCustomerInfo() {

        Scanner reader = new Scanner(System.in);

        System.out.println("Enter First Name: ");
        String firstName = reader.nextLine();

        System.out.println("Enter Last Name: ");
        String lastName = reader.nextLine();

        System.out.println("Enter City: ");
        String city = reader.nextLine();

        // Postal Code
        String postalCode = "";
        boolean valid = false;
        try {
            while (!valid) {
                System.out.println("Enter Postal Code: ");
                postalCode = reader.nextLine();
                valid = validatePostalCode(postalCode.substring(0,3));
                if (!valid)
                    System.out.println("That postal code is invalid.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // Credit Card 
        String creditCard = "";
        valid = false;
        while (!valid) {
            System.out.println("Enter Credit Card Number: ");
            creditCard = reader.nextLine();
            valid = validateCreditCard(creditCard);
            if (!valid)
                System.out.println("That Credit Card is invalid.");
        }

        return firstName + ", " + lastName + ", " + city + ", " + postalCode + ", " + creditCard + "\r\n";     
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    // public static boolean validatePostalCode(String userPostalCode) throws FileNotFoundException {  

    //     //TODO: Postal Code validation
    //     // Postal Code caching 

    //     // Reading file and saving to String
    //     FileReader file = new FileReader(".\\postal_codes.csv");
    //     Scanner scnr = new Scanner(file);

    //     String allPostalCodes = "";
        
    //     // https://stackoverflow.com/questions/42319341/how-do-i-start-scanning-from-the-second-row-of-my-csv-data-to-linked-list
    //     int lineNumber = 1;
    //     while(scnr.hasNextLine()) {

    //         String line = scnr.nextLine();

    //         if (lineNumber > 1)
    //             allPostalCodes += line.substring(0,4);

    //         lineNumber++;
        
    //     } 

    //     // Getting user input and comparing against postal codes

    //     return (allPostalCodes.indexOf(userPostalCode) > -1);

    // }

    public static boolean validatePostalCode(String userPostalCode) throws FileNotFoundException {  

        if (userPostalCode.length() < 3)
            return false;

        if (!Character.isAlphabetic(userPostalCode.charAt(0)) || !Character.isDigit(userPostalCode.charAt(1)) || !Character.isAlphabetic(userPostalCode.charAt(2))) 
            return false;

        if (allPostalCodeCache == null)
            allPostalCodeCache = loadPostalCodes();
        
        return (allPostalCodeCache.indexOf(userPostalCode) > -1);
    }


    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static boolean validateCreditCard(String creditCardNumber) {
        
        if (creditCardNumber.length() < 9)
            return false;
    
        int sum1 = 0;
        int sum2 = 0;
         
        for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
    
            char creditDigit = creditCardNumber.charAt(i);

            if (!Character.isDigit(creditDigit))
                return false;
    
            int creditDigitNumberical = Character.getNumericValue(creditDigit);
    
            if ((i + 1) % 2 == 0) { // even
                int doubleDigits = 2*creditDigitNumberical;
                int sumOfDigits = (doubleDigits/10) + (doubleDigits%10);
                sum2 += sumOfDigits;
            } else // odd
                sum1 += creditDigitNumberical;
        }
        return (sum1 + sum2)%10 == 0;
    }

    public static void generateCustomerDataFile(String customerCollectionData) {

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter filename and path of the CSV: ");
        String outputFilename = reader.nextLine();

        File outFile = new File(outputFilename);

        if (outFile.exists()) {
            System.out.print("File already exists, ok to overwrite (y/n)? ");
            if (!reader.nextLine().startsWith("y")) {
                return;
            }
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(outFile);
            printWriter.println(customerCollectionData);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (printWriter != null) 
                printWriter.close();
        }

    }
    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/

    public static String loadPostalCodes() throws FileNotFoundException {  

        // Reading file and saving to String
        FileReader file = new FileReader(".\\postal_codes.csv");
        Scanner scnr = new Scanner(file);

        String allPostalCodes = "";

        if (scnr.hasNextLine()) // skip the header
            scnr.nextLine(); 

        while(scnr.hasNextLine())
            allPostalCodes += scnr.nextLine().substring(0,4);

        return allPostalCodes;
    }

}