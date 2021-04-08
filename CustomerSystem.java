// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray
/*
 * Date: April 8, 2021
 * Name: Ibrahim Rahman, 341169092@gapps.yrdsb.ca
 * Teacher: Mr. Ho
 * Description: This program allows users to enter customer information such as first name, last name, city, postal code,
 * and credit card number. Postal codes are validated against a .CSV file containing all area codes around Canada,
 * and credit card numbers are validated using the Luhn Algorithm. Once the postal code and credit card are validated,
 * all the customer information is saved. This can be exported in the form of a .CSV file. 
 * 
 */



import java.util.Scanner;
// More packages may be imported in the space below
// TODO: Why I imported that package?
import java.io.*;

/**
 * @author Ibrahim Rahman <341169092@gapps.yrdsb.ca>
 */

class CustomerSystem {

    // TODO: Comment variable
    static String allPostalCodeCache = null;

    /**
     * Main method. Allows users to enter customer information and generate a .CSV file with that information. 
     * @param args Command line arguments. 
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
                // TODO: Comment
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

        // Asks the user to enter customer's first name.
        System.out.println("Enter First Name: ");

        /**
         * Stores the customer's first name. 
         */
        String firstName = reader.nextLine();

        // Asks the user to enter customer's last name.
        System.out.println("Enter Last Name: ");

        /**
         * Stores the customer's last name. 
         */
        String lastName = reader.nextLine();

        // Asks the user to enter customer's city. 
        System.out.println("Enter City: ");

        /**
         * Stores the customer's city. 
         */
        String city = reader.nextLine();

        // Postal Code validation

        /**
         * Holds the inputted postal code. 
         */
        String postalCode = "";

        /**
         * Holds the boolean value of what a method returns. 
         */
        boolean valid = false;

        try {

            // While valid is false, asks for user to input postal code. Then, it validates the first 3 characters of the 
            // postal code with the validatePostalCode method. If the postal code is invalid, then it prints a message and 
            // repeats the loop. 
            while (!valid) {
                System.out.println("Enter Postal Code: ");
                postalCode = reader.nextLine();
                valid = validatePostalCode(postalCode.substring(0,3));
                if (!valid)
                    System.out.println("That postal code is invalid.");
            }
        }
        // Catches all exceptions. 
        catch (Exception e) {
            // Prints the exception. 
            System.out.println(e.getMessage());
        }
        
        // Credit Card validation 

        /**
         * Holds the inputted credit card number.
         */
        String creditCard = "";

        // sets valid to false.
        valid = false;

        // While valid is false, asks user to enter credit card number. Then, it validates the number using the 
        // validateCreditCard method. If the credit card is invalid, then it prints a message and repeats the loop. 
        while (!valid) {
            System.out.println("Enter Credit Card Number: ");
            creditCard = reader.nextLine();
            valid = validateCreditCard(creditCard);
            if (!valid)
                System.out.println("That Credit Card is invalid.");
        }

        // Returns a string of first name, last name, city, postal code, and credit card number. 
        return firstName + ", " + lastName + ", " + city + ", " + postalCode + ", " + creditCard + "\r\n";     
    }
    /**
     * Validates postal code. 
     *
     * @param userPostalCode String of the user's inputted postal code.  
     * @return <code> false </code> if the psotal code is under a length of 3, the postal code isn't a letter, number, letter, or if the postal code 
     *         doesn't exist; <code> true </code> if the postal code does exist. 
     * @throws FileNotFoundException Signals that an attempt to open a file denoted by a specified pathname has failed. 
     */
    public static boolean validatePostalCode(String userPostalCode) throws FileNotFoundException {  

        // Check that userPostalCode is an object. Then check if the length of the user's postal code is under 3. If either, return false. 
        if (userPostalCode == null || userPostalCode.length() < 3)
            return false;

        // Check if the first 3 characters of the postal code is a letter, number, letter. Otherwise return false. 
        if (!Character.isAlphabetic(userPostalCode.charAt(0)) || !Character.isDigit(userPostalCode.charAt(1)) || !Character.isAlphabetic(userPostalCode.charAt(2))) 
            return false;

        // Check if the postal codes have been previously loaded into the global string variable. If it hasn't, then load once. 
        if (allPostalCodeCache == null)
            allPostalCodeCache = loadPostalCodes();
        
        // Returns true if the postal codes exists (in the file) and false if it does not. 
        return (allPostalCodeCache.indexOf(userPostalCode) > -1);
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
        if (creditCardNumber.length() < 9)
            return false;

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
             * Assigns the int value that the creditDigit Unicode character represents to creditDigitNumerical.
             */
            int creditDigitNumerical = Character.getNumericValue(creditDigit);
    
            // If the position of the number is even, then sum the digits and add to sum2. 
            if ((i + 1) % 2 == 0) { // even
                int doubleDigits = 2*creditDigitNumerical;
                int sumOfDigits = (doubleDigits/10) + (doubleDigits%10);
                sum2 += sumOfDigits;

            // else, add the numbers to sum1. 
            } else // odd
                sum1 += creditDigitNumerical;
        }

        // returns true or false, depending on if the number ends with 0.
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

        // TODO: checking to see if customerCollectionData is null

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
         * Creates a new file instance.
         */
        File outFile = new File(outputFilename);

        // If the file already exists, then it will ask the user if they want to overwrite the file. 
        if (outFile.exists()) {
            System.out.print("File already exists, ok to overwrite (y/n)? ");
            if (!reader.nextLine().startsWith("y")) {
                return;
            }
        }


        /**
         * Initiates a PrintWriter type and assignes it a value of null. 
         */
        PrintWriter printWriter = null;

        // Creates a new PrintWriter with the specified file. Then, prints the customer collection data to
        // the file. 
        try {
            printWriter = new PrintWriter(outFile);
            printWriter.println(customerCollectionData);
        }
        // Catches all exceptions. 
        catch (FileNotFoundException e) {
            // Prints the exception.
            System.out.println(e.getMessage());
        }

        // Closes printWriter. 
        finally {
            if (printWriter != null) 
                printWriter.close();
        }

    }
    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/

    /**
     * Loads postal codes from the .CSV file. Adds the postal codes in the form of a string to
     * the allPostalCodes variable. 
     * 
     * @return <code> allPostalCodes </code> when the first four characters from every line is 
     *         added to the string. 
     * @throws FileNotFoundException
     */
    public static String loadPostalCodes() throws FileNotFoundException {  

        /**
         * Creates a new FileReader to read the postal_codes.csv file. 
         */
        FileReader file = new FileReader(".\\postal_codes.csv");

        /**
         * Constructs a new Scanner that produces values scanned from the specified input stream.
         */
        Scanner scnr = new Scanner(file);

        /**
         * Holds a string of all the postal codes concatenated to each other. 
         */
        String allPostalCodes = "";

        // This line skips the header. 
        if (scnr.hasNextLine())
            scnr.nextLine(); 

        // While there's another line, this will continue to append the first for digits of a line
        // to the allPostalCodes string. 
        while(scnr.hasNextLine())
            allPostalCodes += scnr.nextLine().substring(0,4);

        // Will return the string of allPostalCodes. 
        return allPostalCodes;
    }

}