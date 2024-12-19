import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Adopter extends User {
    private List<Integer> adoptionHistory;

    public Adopter(int id, String username, String password) {
        super(id, username, password);
        this.adoptionHistory = new ArrayList<>();
    }

    public Adopter(int id, String username, String password,List<Integer> adoptionHistory) {
        super(id, username, password);
        this.adoptionHistory =adoptionHistory;
    }

    public Adopter( String username, String password,List<Integer> adoptionHistory) {
        super(username, password);
        this.adoptionHistory =adoptionHistory;
    }

    public List<Integer> getAdoptionHistory() {
        return adoptionHistory;
    }

    public void setAdoptionHistory(List<Integer> adoptionHistory) {
        this.adoptionHistory = adoptionHistory;
    }

    public void AddAdoptionIdToHistory(int adoptionID){
        adoptionHistory.add(adoptionID);
    }

    public void ManageProfile() {   System.out.println("====== Manage Profile =====");
        System.out.println("1. Change UserName");
        System.out.println("2. Change Password");
        System.out.println("3. Back");

        int choice = Screen.getIntInput("Choice");
        switch (choice) {
            case 1:
            {
                String username=Screen.getInput("New UserName is : ");

                while (Application.isUsernameTaken(username))
                {
                    System.out.println("This UserName is already taken :(");
                    username=Screen.getInput("New UserName is : ");
                }

                this.setUsername(username);
                Screen.displayMessage("Your UserName is : "+username);
            }
            break;
            case 2:{

                if(this.getPassword().equals(Screen.getInput("Please Enter the Current Password")))
                {
                    String NewPassword=Screen.getInput("Enter the New Password");
                    // confirm password (if you need ) ---> read it again to confirm
                    this.setPassword(NewPassword);
                }
                else{
                    Screen.getInput("Wrong Password");

                }
            }
            break;
            case 3 :
                System.out.println("LOG OUT");
                return;

            default : Screen.displayMessage("Invalid choice.");
        }
    }


    public void AdoptaPet() {

        ShelterManagement.showSheltersInfo();

        if (Application.shelters.isEmpty()) {
            Screen.pauseScreen();
            return;
        }

        int Choice = 0;
        boolean found = false;
        while (!found) {
            Choice = Screen.getIntInput("Enter ShelterID Or [-1] To Go Back :");
            if(Choice == -1)
                return;
            for (Shelter s : Application.shelters) {
                if (Choice == s.getId()) {
                    found = true;
                    PerformAdoptionProcess(s.getId());
                    Screen.pauseScreen();
                    break;
                }
            }
            if (!found)
                System.out.println("Invalid ID :(");

        }

    }

    public static void PerformAdoptionProcess(int ShelterID) {
        System.out.println("=== Adopting A Pet From Shelter ===" + ShelterID);
        List<Pet> FilteredPets = new ArrayList<>();

        for (Pet p : Application.pets) {
            if ((p.getShelterId() == ShelterID) && (p.IsPetAvailable())) {
                FilteredPets.add(p);
            }
        }
        ShelterManagement.ShowPetsList(FilteredPets,"");
        int Choice = 0;
        boolean found = false;
        while (!found) {

            Choice = Screen.getIntInput("Enter PetID Or [-1] To Go Back: ");
            if(Choice == -1)
                return;

            for (Pet p : FilteredPets) {
                if (Choice == p.getId()) {
                    SendARecquest(Choice);
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Invalid ID :(");
        }

        System.out.println("The Request Has Been Sent Successfully :)");

    }

    private static void SendARecquest(int PetID){
        LocalDate date = LocalDate.now();
        Adoption adoption = new Adoption(PetID,Application.currentUser.getId(),date);
        Application.adoptions.add(adoption);
    }

    public void ShowNotifications(){
        List<Notifications> FilteredNotifications = new ArrayList<>();
        for(Notifications N : Application.notificaions){
            if(!N.getIsReaded() && N.getAdopterID() == this.getId()) {
                FilteredNotifications.add(N);
            }
        }
        if(FilteredNotifications.isEmpty()){
            System.out.println("No New Notifications !");
        }
        else {
            int Counter = 0;
            for (Notifications N : FilteredNotifications) {
                Counter++;
                System.out.print(Counter + "- ");
                N.ShowNotificationToAdopter();
                N.setIsReaded(true);
            }
            System.out.println("Count = " + Counter);
        }
        Screen.pauseScreen();
    }


}
