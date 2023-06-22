import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        //Initialize of scanner object to get inputs
        Scanner scan = new Scanner(System.in);

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

        



        String choice = displayMenu(menuOptions, scan);//getting the user choice

        while (!choice.equals("EXT") && !choice.equals("999")) {

            switch (choice) {
                case "VFQ":
                case "100":
                    //1 option
                    printingQue(queuePlan);
                    break;


                case "VEQ":
                case "101":
                    //2 option
                    viewAllEmptyQueues(queuePlan);
                    break;

                case "ACQ":
                case "102":
                    //3 option

                case "RCQ":
                case "103":
                    //4 option

                case "PCQ":
                case "104":
                    //5 option

                case "VCS":
                case "105":
                    //6 option

                case "SPD":
                case "106":
                    //7 option

                case "LPD":
                case "107":
                    //8 option

                case "STK":
                case "108":
                    //9 option

                case "AFS":
                case "109":
                    //10 option

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
                            System.out.print("    O ");
                        } else {
                            System.out.print("    X ");
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

    private static void viewAllEmptyQueues(String[][] queuePlan) {
        System.out.print("Empty queues at the moment  :");
        for(int i=0;i<3;i++){
            for(int j=0;j<queuePlan[i].length;j++){
                if(queuePlan[i][j]==null) {
                    System.out.print(i+1+" ");
                    break; //will stop checking further in that queue and move for next queue
                }
            }
        }
        System.out.println();


        }




}