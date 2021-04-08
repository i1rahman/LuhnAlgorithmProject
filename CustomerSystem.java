// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray

import java.util.Scanner;
// More packages may be imported in the space below
import java.io.*;  // Used for reading and writing files.

/**
 * Date: April 8, 2021
 * Teacher: Mr. Ho
 * <p>
 * This program allows users to enter customer information such as first name, last name, city, postal code,
 * and credit card number. Postal codes are validated against a .CSV file containing all area codes around Canada,
 * and credit card numbers are validated using the Luhn Algorithm. Once the postal code and credit card are validated,
 * all the customer information is saved. This can be exported in the form of a .CSV file.
 * 
 * @author Ibrahim Rahman <341169092@gapps.yrdsb.ca>
 */

class CustomerSystem {

    /**
     * Main method. Allows users to enter customer information and generate a .CSV file with that information. 
     * @param args Command line arguements will be ignored. 
     */
    public static void main(String[] args){
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below

        /**
         * This variable stores all the customer information entered in the form of a CSV string. 
         */
        String customerCollection = "";

        /**
         * This counter variable increments to assign unique customer numbers to each customer. 
         */
        int customerCounter = 1;
       
        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
		        // Any necessary variables may be added to this if section, but nowhere else in the code

                /**
                *  Requirement 1.c: The system should automatically assign a unique customer number to each customer 
                *  starting with an id value of 1
                */
                customerCollection += customerCounter++ + ", " + enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return

                /**
                *  Pass the customerCollection to be saved to a file.
                */ 
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

    /**
     * Allows users to enter customer information.
     * 
     * @return String containing customer's first name, last name, city, postal code, and credit card number. 
     */
    public static String enterCustomerInfo() {

        /**
         * Constructs a new Scanner that produces values scanned from the specified input stream.
         */
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter First Name: ");

        /**
         * Asks the user to enter customer's first name. Stored the customer's first name. 
         */
        String firstName = reader.nextLine();

        System.out.println("Enter Last Name: ");

        /**
         * Stores the customer's last name. 
         */
        String lastName = reader.nextLine();

        System.out.println("Enter City: ");

        /**
         * Stores the customer's city. 
         */
        String city = reader.nextLine();

        /** --------------- Postal Code validation --------------- */

        /**
         * Holds the inputted postal code. 
         */
        String postalCode = "";

        /**
         * Assume the postal code input is invalid, until it's validated. 
         */
        boolean valid = false;

        try {

            // Ask the user to enter a postal code until a valid one is entered.
            while (!valid) {
                System.out.println("Enter Postal Code: ");
                postalCode = reader.nextLine();

                valid = validatePostalCode(postalCode);
                if (!valid)
                    System.out.println("That postal code is invalid.");
            }
        }
        // Catches all exceptions. 
        catch (Exception e) {
            // Prints the exception. 
            System.out.println(e.getMessage());
        }
        
        /** --------------- Credit Card validation --------------- */

        /**
         * Holds the inputted credit card number.
         */
        String creditCard = "";

        // Assume the credit card input is invalid, until it's validated.
        valid = false;

        // Ask the user to enter a credit card until a valid one is entered.
        while (!valid) {
            System.out.println("Enter Credit Card Number: ");
            creditCard = reader.nextLine();
            valid = validateCreditCard(creditCard);
            if (!valid)
                System.out.println("That Credit Card is invalid.");
        }

        // Returns a string as a concatenation of the first name, last name, city, postal code, and credit card number. 
        return firstName + ", " + lastName + ", " + city + ", " + postalCode + ", " + creditCard + "\r\n";     
    }
    /**
     * Validates postal code. 
     *
     * @param userPostalCode String of the user's inputted postal code.  
     * @return <code> false </code> if the psotal code is under a length of 3 
     *         doesn't exist; <code> true </code> if the postal code does exist. 
     * @throws FileNotFoundException Signals that an attempt to open a file denoted by a specified pathname has failed. 
     */
    public static boolean validatePostalCode(String userPostalCode) throws FileNotFoundException {  

        // Check that userPostalCode is an object. Then check if the length of the user's postal code is under 3. If either, return false. 
        // Requirement: 1.a.i.1: Must be at least 3 characters in length
        if (userPostalCode == null || userPostalCode.length() < 3)
            return false;

        // Requirement 1.a.i.2: The first 3 characters must match the postal codes loaded from the file
        userPostalCode = userPostalCode.substring(0,3);

        /**
         * Constructs a new Scanner that produces values scanned from the specified input stream.
         */
        Scanner scnr = new Scanner(new FileReader(".\\postal_codes.csv"));
        scnr.useDelimiter("\\|");
 
        // check if the file has at least one line. Assume it's the header. Read it, and throw it away.
        if (scnr.hasNextLine())
            scnr.nextLine();

        // While there's another line, this will continue to append the first for digits of a line
        // to the allPostalCodes string. 
        while(scnr.hasNextLine()) {
            String postalzip = scnr.next();

            // ignore case when comparing postal codes to the file.
            if (postalzip.equalsIgnoreCase(userPostalCode)) {
                return true;
            }                
            scnr.nextLine();
        }

        return false;
    }


    /**
     * Validates a credit card number using the Luhn Algorithm. 
     * 
     * @param creditCardNumber String of the user's inputted credit card number. 
     * @return <code> false </code> if the credit card length is uder 9, if a character in creditCardNumber is not a digit, or if (sum1 + sum2)%10 is not equal
     *         to 0; <code> true </code> otherwise. 
     */
    public static boolean validateCreditCard(String creditCardNumber) {
        
        // ensure the creditCardNumber string object has a value.
        if (creditCardNumber == null)
            return false;
        
        // replace the spaces in the credit card number before we check the length.
        creditCardNumber = creditCardNumber.replaceAll("\\s", "");

        // If the length of the credit card number is under 9, return false.
        // Requirement 1.b.i.1: Must be at least 9 digits in length
        if (creditCardNumber.length() < 9)
            return false;

        // Requirement 1.b.i.2: The digits must pass the Luhn algorithm.
        /**
         * Sum of the odd credit card digits.
         */
        int sum1 = 0;

        /**
         * Sum of the even credit card digits. 
         */
        int sum2 = 0;
        
        // For loop increments down from length of the credit card number. 
        for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
    
            // Gets character of the current credit card number. 
            char creditDigit = creditCardNumber.charAt(i);

            // If the character is not a digit, then the method will return false. 
            if (!Character.isDigit(creditDigit))
                return false;
    
            /**
             * Convert the character to an integer
             */
            int creditDigitNumerical = Character.getNumericValue(creditDigit);
    
            // If the position of the number is even
            if ((i + 1) % 2 == 0) {

                // double the even digits
                int doubleDigits = 2*creditDigitNumerical;

                // Sum the digits > 9. Get the first digit using div. Get the last digit using mod.
                // For numbers <= 9, the div portion will return 0 anyway, so it still works.
                int sumOfDigits = (doubleDigits/10) + (doubleDigits%10);

                // sum up all the digits.
                sum2 += sumOfDigits;

            // Position is Odd: Add up the odd indexed numbers.
            } else
                sum1 += creditDigitNumerical;
        }

        // Get the last digit using mod. If ends with 0, return true. Otherwise false.
        return (sum1 + sum2)%10 == 0;
    }

    
    /** 
     * Generates a customer data file.
     * 
     * @param customerCollectionData A string of all inputted customer data. 
     * @return 
     */
    public static void generateCustomerDataFile(String customerCollectionData) {

        // ensure the creditCardNumber string object has a value.
        if (customerCollectionData == null || customerCollectionData.equals("")) {
            System.out.print("There are 0 customer records in the system. Please add.");
            return;
        }

        /**
         * Constructs a new Scanner that produces values scanned from the specified input stream.
         */
        Scanner reader = new Scanner(System.in);

        // Asks user to enter the filename and path of the CSV. 
        System.out.print("Enter filename and path of the CSV: ");
        
        /**
         * Stores the filename and path. 
         */
        String outputFilename = reader.nextLine();

        /**
         * Creates a new output file reference.
         */
        File outFile = new File(outputFilename);

        // If the file already exists, then it will ask the user if they want to overwrite the file. Otherwise, write the file.
        if (outFile.exists()) {
            System.out.print("File already exists, ok to overwrite (y/n)? ");
            if (!reader.nextLine().startsWith("y"))
                return;
        }


        // Creates a new PrintWriter with the specified file. The customer collection is already a csv in memory. 
        // Simply write the in memory customerCollection variable to the file.
        // Use a resource block so we don't have to close the PrintWriter. 
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.println(customerCollectionData);
        }
        // Catches IOException exceptions. 
        catch (IOException e) {       
            System.out.println(e.getMessage()); // Prints the exception.
        }
    }
    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/

}