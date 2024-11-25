import java.util.List;
import java.util.Scanner;

public class Screen {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void showAdminMenu() {
        System.out.println("=== Admin Menu ===");
        System.out.println("1. View Pets");
        System.out.println("2. Add Shelter");
        System.out.println("3. Log Out");
        System.out.print("Enter your choice: ");
    }

    public static void showAdopterMenu() {
        System.out.println("=== Adopter Menu ===");
        System.out.println("1. View Available Pets");
        System.out.println("2. Adopt Pet");
        System.out.println("3. Log Out");
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
}
