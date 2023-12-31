import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {


        //Initialize of scanner object to get inputs
        Scanner scan = new Scanner(System.in);

        //burger count
        int burgerCount = 50;

        //Keeping menu options in array to iterate using a for loop in the displayMenu method
        String[] menuOptions = {

                "100 or VFQ: View all Queues",
                "101 or VEQ: View all Empty Queues.",
                "102 or ACQ: Add customer to a Queue.",
                "103 or RCQ: Remove a customer from a Queue. (From a specific location)",
                "104 or PCQ: Remove a served customer.",
                "105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)",
                "106 or SPD: Store Program Data into file.",
                "107 or LPD: Load Program Data from file.",
                "108 or STK: View Remaining burgers Stock.",
                "109 or AFS: Add burgers to Stock.",
                "999 or EXT: Exit the Program."

        };


        //Multi-dimensional array is used to handle queue and cashier data
        String[][] queuePlan = new String[3][];
        queuePlan[0] = new String[2];
        queuePlan[1] = new String[3];
        queuePlan[2] = new String[5];

        //array will help to reuse details get from VEQ function
        int[] emptyQueues = new int[3];

        //array to store removed customers
        String[] removedCustomers = new String[20];
        //track the count of removed customers
        int count = 0;


        String choice = displayMenu(menuOptions, scan);//getting the user choice

        while (!choice.equals("EXT") && !choice.equals("999")) {

            switch (choice) {
                case "VFQ":
                case "100": {
                    //1 option
                    printingQue(queuePlan);
                    break;
                }


                case "VEQ":
                case "101": {
                    //2 option
                    viewAllEmptyQueues(queuePlan, emptyQueues);
                    break;
                }

                case "ACQ":
                case "102": {
                    //3 option
                    viewAllEmptyQueues(queuePlan, emptyQueues);//this method is called here to update emptyQueue array
                    addCustomerToQueue(emptyQueues, scan, queuePlan);//this will add the customer to queue
                    break;
                }


                case "RCQ":
                case "103": {

                    removeCustomer(scan, queuePlan);
                    break;

                }

                case "PCQ":
                case "104": {
                    burgerCount = removeServedCustomer(scan, queuePlan, burgerCount, removedCustomers, count);
                    break;

                }

                case "VCS":
                case "105": {
                    //6 option
                    sortCustomers(queuePlan);
                    break;

                }

                case "SPD":
                case "106": {
                    //7 option
                    storeDataToFile(queuePlan, burgerCount);
                    break;
                }

                case "LPD":
                case "107": {
                    //8 option
                    burgerCount = loadProgram(queuePlan);
                    break;
                }

                case "STK":
                case "108":

                    System.out.println("Burger stock has " + burgerCount + " burgers");
                    break;

                case "AFS":
                case "109": {

                    addBurgersToStock(scan);
                    break;

                }

                default: {
                    System.out.println("Invalid option");
                    break;
                }
            }

            choice = displayMenu(menuOptions, scan);//getting the user choice again
        }


    }

    private static String displayMenu(String[] menuOptions, Scanner scan) {


        System.out.println("-----Foodies Fave Queue Management System.-------");
        System.out.println();


        System.out.println("Please select an option from below :");


        //Enhance for loop to iterate over menuOptions array
        for (String option : menuOptions) {
            System.out.println(option);

        }

        if (scan.hasNextInt()) {
            String menuChoice = String.valueOf(scan.nextInt());
            return menuChoice;

        } else {
            String menuChoice = scan.nextLine();
            return menuChoice;

        }


    }


    private static void printingQue(String[][] queuePlan) {
        //following for-loop will print cashier area
        for (int i = 0; i < 3; i++) {
            if (i == 0 || i == 2) {
                System.out.println("  *******************");
            } else {
                System.out.println("  *     Cashier     *");
            }
        }

        //following nested for-loops will print queues
        for (int i = 0; i < 5; i++) {
            try {
                for (int j = 0; j < 3; j++) {
                    try {
                        if (queuePlan[j][i] == null) {
                            System.out.print("    X ");
                        } else {
                            System.out.print("    O ");
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.print("      ");//this will add spaces when there is no spaces in a queue
                    }
                }
                System.out.println();

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("      ");//this will cause to go to new line when exception occur at end of a horizontal row
            }
        }


    }

    private static void viewAllEmptyQueues(String[][] queuePlan, int[] emptyQueues) {
        System.out.print("Empty queues at the moment  :");
        //following for-loop will check the last place of each queue to make sure the queue is empty
        for (int i = 0; i < 3; i++) {
            if (queuePlan[i][(queuePlan[i].length - 1)] == null) {
                System.out.print((i + 1) + " ");
                emptyQueues[i] = i + 1;//adding the empty queue to array
            } else {
                emptyQueues[i] = 0;//will hold 0 for relevant queue if not empty
            }
        }
        System.out.println();


    }

    private static void addCustomerToQueue(int[] emptyQueues, Scanner scan, String[][] queuePlan) {
        boolean match;//this variable is used to decide weather program should go for next iteration when needed
        boolean contains; //this variable is used to find that entered array is empty or not

        do {
            try {
                match = false;
                System.out.println("Enter queue number: ");
                int queueNumber = scan.nextInt();


                contains = IntStream.of(emptyQueues).anyMatch((x -> x == queueNumber));

                //implementation of the functionality of the method through adding process method
                if (contains) {

                    addingProcess(queueNumber, queuePlan, scan);

                }

                //else block will handle the input validation
                else {
                    System.out.println("This queue is empty ! would you like to try another queue ?(Y/N)");
                    String choice = scan.next().toUpperCase();

                    //while loop is used to validate input when an invalid option is given
                    while (!choice.equals("Y") && !choice.equals("N")) {
                        System.out.println("Please enter a valid option!");
                        choice = scan.next().toUpperCase();

                    }
                    if (choice.equals("Y")) {
                        match = true;//will go for next iteration
                    }
                }


            } catch (InputMismatchException e) {
                System.out.println("you entered an invalid input");
                match = true;
            }

        } while (match);


    }

    private static void addingProcess(int queueNumber, String[][] queuePlan, Scanner scan) {

        System.out.println("Please enter your name :");
        String name = scan.next();

        for (int i = 0; i < (queuePlan[queueNumber - 1].length); i++) {
            if (queuePlan[queueNumber - 1][i] == null) {
                queuePlan[queueNumber - 1][i] = name;//making the position occupied
                break;
            }
        }


    }

    private static void removeCustomer(Scanner scan, String[][] queuePlan) {

        boolean match;
        do {
            try {
                match = false;

                //following code will get input from the user to locate relevant position
                System.out.print("Please enter the queue(1-3) :");
                int queueNum = scan.nextInt();
                System.out.print("Please enter the place :");
                int place = scan.nextInt();

                //removing the relevant position
                queuePlan[queueNum - 1][place - 1] = null;
                //rearranging the places of the queue
                reArrangingQueue(queueNum, place, queuePlan);


            } catch (InputMismatchException ex) {
                match = true;
                System.out.println("Please enter a valid input !(number)");
            }

        } while (match);
    }

    private static void reArrangingQueue(int queueNum, int place, String[][] queuePlan) {

        for (int i = place; i < queuePlan[queueNum - 1].length; i++) {
            if (!(queuePlan[queueNum - 1][i] == null)) {
                queuePlan[queueNum - 1][i - 1] = queuePlan[queueNum - 1][i];//forward the person to place in front of them
                queuePlan[queueNum - 1][i] = null;//setting that position to null
            } else {

                break;
            }
        }
        queuePlan[queueNum - 1][queuePlan[queueNum - 1].length - 1] = null;//setting the last place of queue to empty


    }

    private static void sortCustomers(String[][] queuePlan) {

        String[] customerNames = new String[10];//array to store names
        int nameIndex = 0;//track the current position of customerNames array

        //nested for-loop to loop through the multidimensional array to get customer names to customerNames array-list
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < queuePlan[i].length; j++) {
                if (!(queuePlan[i][j] == null)) {
                    customerNames[nameIndex] = queuePlan[i][j];
                    nameIndex++;
                }
            }
        }

        //following nested for-loop is used to travel through the customerNames array and sort it.

        for (int i = 0; i < customerNames.length; i++) {
            for (int j = i + 1; j < customerNames.length; j++) {
                if (!(customerNames[j] == null)) {

                    if (customerNames[i].compareTo(customerNames[j]) > 0) {

                        String temp = customerNames[i];
                        customerNames[i] = customerNames[j];
                        customerNames[j] = temp;
                    }

                } else {
                    break;//will break the inner iteration when no names are found in the array
                }
            }
            if (customerNames[i] == null) {
                break;//will break the outer iteration when no names are found in the array
            }
        }
        //following enhance for loop will print the  sorted customers
        System.out.println("Customers sorted by name");


        for (String name : customerNames) {
            if (name == null) {
                break;
            } else {
                System.out.println("    " + name);

            }
        }
    }


    private static int removeServedCustomer(Scanner scan, String[][] queuePlan, int burgerCount, String[] removedCustomers, int count) {

        boolean match;

        do {
            match = false;
            try {
                if (burgerCount == 10) {
                    System.out.println("Burger stock only has 10 burgers,Please add burgers to stock");

                } else {

                    System.out.println("Enter the queue :");
                    int queueNo = scan.nextInt();
                    scan.nextLine();
                    if (queuePlan[queueNo - 1][0] != null) {
                        removedCustomers[count] = queuePlan[queueNo - 1][0];
                        //displaying the removed customer
                        System.out.println(removedCustomers[count] + "has been removed form queue" + queueNo);

                        queuePlan[queueNo - 1][0] = null;//make the place to non-occupied
                        reArrangingQueue(queueNo, 1, queuePlan);//re-arranging the queue after removing the served customer

                        burgerCount = burgerCount - 5;
                        count++;

                    } else {
                        System.out.println("This queue has no customers to remove");
                    }


                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!Please enter a number");
                match = true;

            }


        } while (match);

        return burgerCount;
    }


    private static void storeDataToFile(String[][] queuePlan, int burgerCount) {

        //save queue data to file
        try {
            //this file is for storage of queue data
            FileWriter infoWrite = new FileWriter("information.txt");
            //this file is for storage of burgerCount
            FileWriter burgerCountWrite = new FileWriter("burgerCount.txt");


            //following nested for-loops will store queue data in information.txt
            for (int i = 0; i < 5; i++) {
                try {
                    for (int j = 0; j < 3; j++) {
                        try {
                            infoWrite.write(queuePlan[j][i] + " ");
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            infoWrite.write(" ");
                        }
                    }


                } catch (ArrayIndexOutOfBoundsException e) {
                    infoWrite.write(" ");
                }
            }


            burgerCountWrite.write(String.valueOf(burgerCount));


            infoWrite.close();
            burgerCountWrite.close();
            System.out.println("file saved successfully");
        } catch (IOException e) {

            System.out.println("Error occurred while saving the file");
        }


    }


    private static int loadProgram(String[][] queuePlan) {

        int burgerCount = 0;
        try {
            int count = 0;//track the names in the names array
            //this is used to read data form information file
            FileReader read = new FileReader("information.txt");


            Scanner scanner = new Scanner(read);
            String[] names = scanner.nextLine().split(" ");//split the string and adding to names array


            for (int i = 0; i < 5; i++) {
                try {
                    for (int j = 0; j < 3; j++) {
                        try {
                            if (names[count].equals("null")) {
                                queuePlan[j][i] = null;
                            } else {
                                queuePlan[j][i] = names[count];
                            }

                            count++;
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            //queue ie printed horizontally,this will give arrayIndexoutofbond exception
                            // as all the queue lengths are not equal
                            count++;
                        }
                    }


                } catch (ArrayIndexOutOfBoundsException e) {
                    //queue ie printed horizontally,this will give ArrayIndexOutofBond exception
                    // as all the queue lengths are not equal
                    count++;
                }
            }

            scanner.close();


        } catch (IOException e) {
            System.out.println("Error occurred while  loading/locating the file");
        }

        try {
            //this is used to read data from burgerCount file
            FileReader readCount = new FileReader("burgerCount.txt");
            Scanner scanner1 = new Scanner(readCount);
            burgerCount = Integer.parseInt(scanner1.next());
        } catch (IOException exception) {
            System.out.println("Error occured while  loading the burger count");
        }

        return burgerCount;

    }

    private static int addBurgersToStock(Scanner scanner){
        boolean match;
        int burgers=0;
        do{
            match=false;

            try{

                System.out.println("How many Burgers you want to add for the queue :");
                int additionalBurgers=scanner.nextInt();

                if(additionalBurgers<=50){
                    burgers= additionalBurgers;
                }
                else{
                    System.out.println("please enter  a number less than 50");
                    match=true;


                }

            }catch (InputMismatchException e){
                System.out.println("Invalid Input!");
                match=true;
            }
        }while (match);


        return  burgers;

    }


}

