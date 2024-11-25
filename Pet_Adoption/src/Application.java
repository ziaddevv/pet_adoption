import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private List<Admin> admins;
    private List<Adopter> adopters;
    private List<Pet> pets;
    private List<Shelter> shelters;
    private List<Adoption> adoptions;
    private User currentUser;

    public Application() {
        this.admins = new ArrayList<>();
        this.adopters = new ArrayList<>();
        this.pets = new ArrayList<>();
        this.shelters = new ArrayList<>();
        this.adoptions = new ArrayList<>();
    }

    public void start() {
        try {
            LoadFilesData();
            mainMenu();
            SaveFilesData();
        } catch (Exception e) {
            Screen.displayMessage("An error occurred: " + e.getLocalizedMessage());
        }
    }

    private void LoadFilesData() throws IOException {
        System.out.println("one");
        adoptions = HandleFiles.loadAdoptions();
        System.out.println("two");

        HandleFiles.loadUsers(admins, adopters);
        System.out.println("three");

        shelters = HandleFiles.loadShelters();
        System.out.println("four");

        pets = HandleFiles.loadPets();
    }

    private void SaveFilesData() throws IOException {
        HandleFiles.saveAdoptions(adoptions);
        HandleFiles.savePets(pets);
        HandleFiles.saveShelters(shelters);
        HandleFiles.saveUsers(admins, adopters);
    }

    private void mainMenu() {
        while (true) {
            Screen.showMainMenu();
            int choice = Screen.getIntInput("");
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    ;
                    break;
                case 3:
                    Screen.displayMessage("Exiting the system...");

                    return;

                default:
                    Screen.displayMessage("Invalid choice. Try again.");
            }
        }
    }
    // drawbacks 
    //

    private void login() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3; // maximum tries  
        do {
            String username = Screen.getInput("Enter username:");
            String password = Screen.getInput("Enter password:");

            currentUser = validateUser(username, password);

            if (currentUser != null) {
                Screen.displayMessage("Welcome, " + currentUser.getUsername() + "!");
                if (currentUser instanceof Admin) {
                    adminMenu((Admin) currentUser);
                } else if (currentUser instanceof Adopter) {
                    adopterMenu((Adopter) currentUser);
                }
                return;                         ////// exit the method after successful login
            }

            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                Screen.displayMessage("Too many failed attempts. Exiting login...");
                return;
            }

            String choice = Screen.getInput("Wrong password or username! Do you want to try again? [Y/N]:");
            if (!choice.equalsIgnoreCase("Y")) {
                Screen.displayMessage("Exiting login :(");
                return;
            }

        } while (true);
    }

    //  validate users
    private User validateUser(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        for (Adopter adopter : adopters) {
            if (adopter.getUsername().equals(username) && adopter.getPassword().equals(password)) {
                return adopter;
            }
        }

        return null;
    }



//للاحتياااااط
//        if (loginAdmin) {
//            Screen.displayMessage("Logged in as Admin.");
//            adminMenu();
//            System.out.println("loged out");
//        } else if (loginAdopter) {
//            Screen.displayMessage("Logged in as Adopter.");
//            adopterMenu();

    private void register() {

        String role = Screen.getInput("Role (Admin/Adopter): ");

        if (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Adopter")) {
            Screen.displayMessage("Invalid role. Please try again  :).");
            return;
        }

        String username = Screen.getInput("Enter username: ");
        if (isUsernameTaken(username)) {
            Screen.displayMessage("Username already exists. Please choose a different username.");
            return;
        }

        String password = Screen.getInput("Enter password: ");
        if (password.isEmpty()) {
            Screen.displayMessage("Password cannot be empty.");
            return;
        }

        if (role.equalsIgnoreCase("Admin")) {
            admins.add(new Admin(username, password, new ArrayList<>()));
            Screen.displayMessage("Admin registered successfully.");
        } else if (role.equalsIgnoreCase("Adopter")) {
            adopters.add(new Adopter(username, password, new ArrayList<>()));
            Screen.displayMessage("Adopter registered successfully.");
        }

        Screen.displayMessage("Registration successful! Please log in.");
        login();
    }


    private boolean isUsernameTaken(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                return true;
            }
        }

        for (Adopter adopter : adopters) {
            if (adopter.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }



    private void adminMenu(Admin currentuser) {

        while (true) {
            Screen.showAdminMenu();
            int choice = Screen.getIntInput("");
    
            switch (choice) {
                case 1:
                    Screen.displayPets(pets);
                    break;
                case 2:
                currentuser.addShelter(555);

                    break;
                case 3:
                    Screen.displayMessage("Logging out...");
                    return;   
                default:
                    Screen.displayMessage("Invalid choice.");
            }
        }
    }
    private void adopterMenu(Adopter currentuser) {
        while (true) {
            Screen.showAdopterMenu();
            int choice = Screen.getIntInput("");
            switch (choice) {
                case 1: Screen.displayPets(pets);
                    break;
                case 2: System.out.println("NOT YET");
                    break;
                case 3 : System.out.println("log out");
                    return;
                default : Screen.displayMessage("Invalid choice.");
            }
        }
    }

    private void DeleteShelter()
    {
        int id=Screen.getIntInput("enter the ID of the shelter");
        for(int i=0;i<shelters.size();i++)
        {
            if (shelters.get(i).getId()==id) {
                shelters.remove(i);
                break;
            }
        }
    }



    
}