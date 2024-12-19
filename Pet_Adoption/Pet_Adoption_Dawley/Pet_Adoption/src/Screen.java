import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;


public class Screen {
    private static final Scanner scanner = new Scanner(System.in);

    public static void PrintHeader(String Title){
        System.out.println("------------------------------------------");
        System.out.println(Title);
        System.out.println("------------------------------------------");
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void pauseScreen() {
        System.out.println("Press Enter to continue...");

        try {
            // Wait for the first keypress
            System.in.read();

            // Clear the remaining characters in the input buffer
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void showAdminMenu() {
        System.out.println("=== Admin Menu ===");
        System.out.println("1. Manage User Profiles on System");
        System.out.println("2. Shelter Management");
        System.out.println("3. Adoption Process Management");
        System.out.println("4. Pet Exploration");
        System.out.println("5. Reporting and Analytics");
        System.out.println("6. Log Out");
        System.out.print("Enter your choice: ");
    }

    public static void ShowRequestsManagementScreen(){
        System.out.println("=== Requests Menu ===");
        System.out.println("1. Pending Requests");
        System.out.println("2. Handled Requests");
        System.out.println("3. Back");
        System.out.print("Enter your choice: ");
    }

    public static void ShowShelterManagementScreen(){
        System.out.println("=== Shelters Menu ===");
        System.out.println("1. Add New Pet");
        System.out.println("2. Delete Pet");
        System.out.println("3. Update Pet");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");
    }

    public static void showAdopterMenu() {
        if(Notifications.NumberOfNotreadNotifications((Adopter) Application.currentUser)!=0)
        {
            System.out.println("YOU  HAVE A NEW NOTIFICATIONS PLEASE TAKE A LOOK");
        }
        System.out.println("=== Adopter Menu ===");
        System.out.println("1. manage profile");
        System.out.println("2. Pet Exploration");
        System.out.println("3. Adopt a pet");
        System.out.println("4. Notifications"+"("+Notifications.NumberOfNotreadNotifications((Adopter) Application.currentUser)+")");
        System.out.println("5. Adoption History");
        System.out.println("6. Log Out");
        System.out.print("Enter your choice: ");
    }

    public static String getInput(String message) {
        System.out.print(message);
        String input;
        do {
            input = scanner.next().trim();
            if (input.isEmpty()) {
                System.out.print("Input cannot be empty. " + message + " ");
            }
        } while (input.isEmpty());
        return input.trim();
    }

    public static void ShowPetExplorationScreenForAdopters(){
        System.out.println("\n\n\t\tPet Exploration");
        System.out.println("[1] Filter Pets By Name");
        System.out.println("[2] Filter Pets By Age");
        System.out.println("[3] Filter Pets Species");
        System.out.println("[4] Filter Pets By Breed");
        System.out.println("[5] Filter Pets By Shilter ID");
        System.out.println("[6] Filter Pets By Availabilty");
        System.out.println("[7] Show All Pets");
        System.out.println("[8] Back");

        System.out.print("\n\n\t\tEnter Your Choice :");
    }

    public static void ShowPetExplorationScreenForAdmins(){
        System.out.println("\n\n\t\tPet Exploration");
        System.out.println("[1] Find Pet By ID");
        System.out.println("[2] Filter Pets By Name");
        System.out.println("[3] Filter Pets By Age");
        System.out.println("[4] Filter Pets Species");
        System.out.println("[5] Filter Pets By Breed");
        System.out.println("[6] Filter Pets By Shilter ID");
        System.out.println("[7] Filter Pets By Availabilty");
        System.out.println("[8] Show All Pets");
        System.out.println("[9] Back");

        System.out.print("\n\n\t\tEnter Your Choice :");
    }

    public static int getIntInput(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void displayPets(List<Pet> pets) {
        for (Pet pet : pets) {
            pet.displayPetDetails();
        }
    }
public static void ShowAnalyticsScreen(){
    System.out.println("===Analytics Screen===");
    System.out.println("[1] Shelters Performance");
    System.out.println("[2] Pets Analytics");
    System.out.println("[3] Back");

}

public static void ShowSheltersScreen(){
    System.out.println("===Shelters Screen===");
    System.out.println("[1] Add New Shelter");
    System.out.println("[2] Delete Shelter");
    System.out.println("[3] Update Shelter");
    System.out.println("[4] Back");

}



}