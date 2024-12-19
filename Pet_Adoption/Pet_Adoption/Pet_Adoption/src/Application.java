import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static List<Admin> admins;
    public static List<Adopter> adopters;
    public static List<Pet> pets;
    public static List<Shelter> shelters;
    public static List<Adoption> adoptions;
    public static List<Notifications> notificaions;
    public static User currentUser;

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

        System.out.printf("Five");
        notificaions = HandleFiles.loadNotifications();
    }

    private void SaveFilesData() throws IOException {
        HandleFiles.saveAdoptions(adoptions);
        HandleFiles.savePets(pets);
        HandleFiles.saveShelters(shelters);
        HandleFiles.saveUsers(admins, adopters);
        HandleFiles.saveNotifications(notificaions);
    }

    private void mainMenu() {
        while (true) {
            Screen.clearScreen();
            Screen.showMainMenu();
            int choice = Screen.getIntInput("");

            Screen.clearScreen();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
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


    private void login() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3; // maximum tries
        System.out.println("=== Login Screen ===");

        do {
            String username = Screen.getInput("Enter username:");
            String password = Screen.getInput("Enter password:");

            currentUser = validateUser(username, password);

            if (currentUser != null) {
                Screen.displayMessage("Welcome, " + currentUser.getUsername() + "!");
                Screen.pauseScreen();
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
                Screen.pauseScreen();
                return;
            }

            String choice = Screen.getInput("Wrong password or username! Do you want to try again? [Y/N]:");
            if (!choice.equalsIgnoreCase("Y")) {
                Screen.displayMessage("Exiting login :(");
                Screen.pauseScreen();
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
//            System.out.println("logged out");
//        } else if (loginAdopter) {
//            Screen.displayMessage("Logged in as Adopter.");
//            adopterMenu();

    private void register() {
        System.out.println("=== Register Screen ===");

        String role = Screen.getInput("Role (Admin/Adopter): ");

        while (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Adopter")) {
            Screen.displayMessage("Invalid role. Please try again  :).");
            role = Screen.getInput("Role (Admin/Adopter): ");

        }

        String username = Screen.getInput("Enter username: ");
        while (isUsernameTaken(username)) {
            Screen.displayMessage("Username already exists. Please choose a different username.");
            username = Screen.getInput("Enter username: ");
        }

        String password = Screen.getInput("Enter password: ");
        while (password.isEmpty()) {
            Screen.displayMessage("Password cannot be empty.");
            password = Screen.getInput("Enter password: ");
        }

        if (role.equalsIgnoreCase("Admin")) {
            admins.add(new Admin(username, password, new ArrayList<>()));
            Screen.displayMessage("Admin registered successfully.");
        } else if (role.equalsIgnoreCase("Adopter")) {
            adopters.add(new Adopter(username, password, new ArrayList<>()));
            Screen.displayMessage("Adopter registered successfully.");
        }

        Screen.pauseScreen();



    }


    public static boolean isUsernameTaken(String username) {
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
            Screen.clearScreen();

            Screen.showAdminMenu();
            int choice = Screen.getIntInput("");

            Screen.clearScreen();

            switch (choice) {
                case 1:
                    //manage User Profiles
                    break;
                case 2:
                    //Pet Management
                    ShelterManagementMenu();

                    break;
                case 3:
                    // Adopter Management
                    RequestManagementMenu();
                    break;
                case 4:
                    PetExplorationMenuForAdmins();
                    break;
                case 5:
                    //reports and analytics
                    AnalyticsMenu();
                    break;
                case 6:
                    System.out.println("log out");
                    return;
                case 0:
                    return;
                default:
                    Screen.displayMessage("Invalid choice.");
            }
        }
    }

    private void adopterMenu(Adopter currentuser) {

        while (true) {
            Screen.clearScreen();
            Screen.showAdopterMenu();
            int choice = Screen.getIntInput("");

            Screen.clearScreen();

            switch (choice) {
                case 1: // manage profile
                    currentuser.ManageProfile();
                    break;
                case 2:// Pet Exploration
                    PetExplorationMenuForAdopters();
                    break;
                case 3 :
                    currentuser.AdoptaPet();
                    break;
                case 4:
                    currentuser.ShowNotifications();

                    break;
                case 5:
                    //Adoption History

                    break;
                case 6:
                    System.out.println("log out");
                    return;

                default : Screen.displayMessage("Invalid choice.");
            }
        }
    }

    private void DeleteShelter() {
        int id=Screen.getIntInput("enter the ID of the shelter");
        for(int i=0;i<shelters.size();i++)
        {
            if (shelters.get(i).getId()==id) {
                shelters.remove(i);
                break;
            }
        }
    }

    public static void PetExplorationMenuForAdmins() {
        int Choice = 0;
        while (true) {
            Screen.clearScreen();

            Screen.ShowPetExplorationScreenForAdmins();
            Choice = Screen.getIntInput("");

            Screen.clearScreen();

            switch (Choice) {
                case 1:
                    ShelterManagement.FindPetByID();
                    break;
                case 2:
                    ShelterManagement.ShowPetsFilteredByName();
                    break;
                case 3:
                    ShelterManagement.ShowPetsFilteredByAge();
                    break;
                case 4:
                    ShelterManagement.ShowPetsFilteredBySpecies();
                    break;
                case 5:
                    ShelterManagement.ShowPetsFilteredByBreed();
                    break;
                case 6:
                    ShelterManagement.ShowPetsFilteredByShelterID();
                    break;
                case 7:
                    ShelterManagement.ShowPetsFilteredByAvailablity();
                    break;
                case 8:
                    ShelterManagement.ShowAllPets();
                    break;
                case 9:
                    return;
                default : Screen.displayMessage("Invalid choice.");


            }
        }
    }

    public static void PetExplorationMenuForAdopters() {
        int Choice = 0;
        while (true) {
            Screen.clearScreen();

            Screen.ShowPetExplorationScreenForAdopters();
            Choice = Screen.getIntInput("");

            Screen.clearScreen();

            switch (Choice) {
                case 1:
                    ShelterManagement.ShowPetsFilteredByName();
                    break;
                case 2:
                    ShelterManagement.ShowPetsFilteredByAge();
                    break;
                case 3:
                    ShelterManagement.ShowPetsFilteredBySpecies();
                    break;
                case 4:
                    ShelterManagement.ShowPetsFilteredByBreed();
                    break;
                case 5:
                    ShelterManagement.ShowPetsFilteredByShelterID();
                    break;
                case 6:
                    ShelterManagement.ShowPetsFilteredByAvailablity();
                    break;
                case 7:
                    ShelterManagement.ShowAllPets();
                    break;
                case 8:
                    return;
                default : Screen.displayMessage("Invalid choice.");


            }
        }
    }

    public static void ShelterManagementMenu(){
        int Choice = 0;
        while (true) {
            Screen.clearScreen();
            Screen.ShowShelterManagementScreen();

            Choice = Screen.getIntInput("");
            switch (Choice) {
                case 1:
                    ShelterManagement.AddNewPet(pets,shelters);
                    break;
                case 2:
                    ShelterManagement.DeletePet();
                    break;
                case 3:
                    ShelterManagement.UpdatePet(pets,shelters);
                    break;
                case 4:
                    return;

                default : Screen.displayMessage("Invalid choice.");

            }
        }
    }

    public static void RequestManagementMenu(){
        int Choice = 0;
        while (true) {
            Screen.clearScreen();
            Screen.ShowRequestsManagementScreen();

            Choice = Screen.getIntInput("");
            switch (Choice) {
                case 1:
                    Adoption.ShowAdminPendingRequests(adoptions, currentUser);
                    break;
                case 2:
                    Adoption.ShowAdminHandledRequests(adoptions, currentUser);
                    break;
                case 3:
                    return;

                default : Screen.displayMessage("Invalid choice.");

            }
        }
    }

    public static void AnalyticsMenu(){
        int Choice = 0;
        while (true) {
            Screen.clearScreen();
            Screen.ShowAnalyticsScreen();

            Choice = Screen.getIntInput("Enter Your Choice :");
            switch (Choice) {
                case 1:
                    Analytics.showSheltersPerformance();
                    break;
                case 2:
                    Analytics A = new Analytics();
                    A.showTheSpeciesPreference();;
                    break;
                case 3:
                    return;

                default : Screen.displayMessage("Invalid choice.");

            }
        }
    }

    public static void ShelterMenu(){
        int Choice = 0;
        while (true) {
            Screen.clearScreen();
            Screen.ShowSheltersScreen();

            Choice = Screen.getIntInput("Enter Your Choice :");
            switch (Choice) {
                case 1:
                    ShelterManagement.AddNewShelter();
                    break;
                case 2:
                   ShelterManagement.DeleteShelter();
                    break;
                case 3:
                    ShelterManagement.UpdateShelter();
                    break;
                case 4:
                    return;

                default : Screen.displayMessage("Invalid choice.");

            }
        }
    }


}
