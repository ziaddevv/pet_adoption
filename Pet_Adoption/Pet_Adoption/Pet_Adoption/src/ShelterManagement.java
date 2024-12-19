import java.util.ArrayList;
import java.util.List;

public class ShelterManagement {

    public static void AddNewShelter() {
        Shelter S1;
        String Name = "", Location = "", ContactInfo = "";
        List<Integer> PetsIDS = new ArrayList<>();
        Name = Screen.getInput("\nEnter The Name Of The Shelter :");
        Location = Screen.getInput("Enter The Location Of The Shelter :");
        ContactInfo = Screen.getInput("Enter Contact Info :");
        S1 = new Shelter(Name, Location, ContactInfo, PetsIDS);
        Application.shelters.add(S1);
        Admin A = (Admin)Application.currentUser;
        A.addShelter(S1.getId());                   //To Add The ShelterID To The Admin;
        System.out.println("Shelter Was Added Successfully :)");
        Screen.pauseScreen();
    }

    public static void ShowShelterInfo(Shelter S) {

        System.out.print(S.getId() + " | " + S.getName() + " | " + S.getLocation()
                + " | " + S.getContactInfo());
    }

    public static boolean IsShelterExistByID(int ShelterID) {

        for (Shelter S : Application.shelters) {
            if (S.getId() == ShelterID) {
                return true;
            }
        }
        return false;
    }

    public static Shelter IsShelterExistedByID(int ShelterID) {
        for (Shelter s : Application.shelters) {
            if (s.getId() == ShelterID) {
                return s;
            }

        }
        return null;
    }

    public static void DeleteShelter() {
        FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID :");
        while(!IsAdminHasAccessToShelter(ShelterID)){
            ShelterID = Screen.getIntInput("Invalid ID :( Try Again :");
        }

        String choice;
        boolean flag = true , HasReference = true;

        while(flag) {
            for (Shelter S : Application.shelters) {
                if (ShelterID == S.getId()) {
                    if(!IsShelterFree(S)){
                        break;
                    }
                    flag = false;
                    choice = Screen.getInput("Are You Sure You Want To Delete It [y/n] :");
                    if (choice.equals("y") || choice.equals("Y")) {
                        Application.shelters.remove(S);
                        System.out.println("Deleted Successfully (:");
                        Screen.pauseScreen();
                    }
                    break;
                }
            }
            if(HasReference){
                System.out.println("Cannot Remove This Shelter As it Has References :(");
            }
            if(flag)
            {
                ShelterID = Screen.getIntInput("Invalid ID Enter [-1] To go Back Or Enter A new One :");
                if(ShelterID == -1)
                    return;
            }
        }

    }

    public static void UpdateShelter(){
        FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID Or -1 To Go Back :");
        if(ShelterID == -1)
            return;
        while(!IsAdminHasAccessToShelter(ShelterID)){
            System.out.println("You Do Not Have Access On This Shelter Try Another One");
            ShelterID = Screen.getIntInput("Enter ShelterID Or [-1] To Go Back :");
            if(ShelterID == -1)
                return;
        }

        Shelter S = IsShelterExistedByID(ShelterID);
        while(S == null){
            ShelterID = Screen.getIntInput("Invalid ID Enter [-1] to Go Back Or Enter The ID :");
            if(ShelterID == -1)
                return;
            S = IsShelterExistedByID(ShelterID);
        }
        S.UpdateShelterInfo();
        System.out.println("Updated Successfully :)");
        Screen.pauseScreen();
    }

    private static boolean IsShelterFree(Shelter S) {return (S.PetsIDs.isEmpty());}

    public static void AddNewPet(List<Pet> pets, List<Shelter> shelters) {
        Pet P1;
        String Name, Breed, Species, HealthStatus;
        enAvailability Availability = enAvailability.AVAILABLE;
        int Age = -1, ShelterID = -1;


        Name = Screen.getInput("Enter The Name Of The Animal :");
        Species = Screen.getInput("\nEnter The Species Of The Animal :");
        Breed = Screen.getInput("\nEnter The Breed Of The Animal :");
        HealthStatus = Screen.getInput("\nEnter The Health Status Of The Animal :");
        Age = Screen.getIntInput("\nEnter The age Of The Animal :");
        FilterSheltersByAdmin();
        ShelterID = Screen.getIntInput("\nEnter The ShelterID Or [-1] To Go Back :");
        if(ShelterID == -1)
            return;
        while(!IsAdminHasAccessToShelter(ShelterID)) {
            System.out.println("You Do Not Have Access On This Shelter Try Another One");
            ShelterID = Screen.getIntInput("Enter ShelterID Or [-1] To Go Back :");
            if(ShelterID == -1)
                return;
        }
        P1 = new Pet(Name, Species, Breed, Age, HealthStatus, ShelterID, Availability);
        pets.add(P1);

        for (Shelter S : shelters) {
            if (P1.getShelterId() == S.getId()) {
                S.PetsIDs.add(P1.getId());
            }
        }
        System.out.println(P1.getName() + " Has Been Added Successfully :)");
        Screen.pauseScreen();
    }

    public static void DeletePet() {
        FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID For The Pet :");
        while(!IsAdminHasAccessToShelter(ShelterID)){
            ShelterID = Screen.getIntInput("Invalid ID :( Try Again :");
        }
        // The following code is for displaying Pets on The Shelter;
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : Application.pets) {
            if (p.getShelterId() == ShelterID&&p.IsPetAvailable()) {
                FilteredPets.add(p);
            }
        }
        ShowPetsList(FilteredPets, "Filtered Pets By ShelterID : " + ShelterID);
        Screen.pauseScreen();
        if(FilteredPets.isEmpty()){
            System.out.println("The Shelter Is Empty !");
            Screen.pauseScreen();
            return;
        }
        int PetID = Screen.getIntInput("Enter The Pet ID :");
        String choice;
        boolean flag = true;

        while(flag) {
            for (Pet p : FilteredPets) {
                if (PetID == p.getId()) {
                    flag = false;
                    choice = Screen.getInput("Are You Sure You Want To Delete It [y/n] :");
                    if (choice.equals("y") || choice.equals("Y")) {
                        DeletePetIDFromShelter(Application.shelters, p.getShelterId(), p.getId());
                        Adoption.DeleteAdoption(p.getId());
                        FilteredPets.remove(p);
                        Application.pets.remove(p);
                        System.out.println("Deleted Successfully (:");
                        Screen.pauseScreen();
                    }
                    break;
                }
            }
            boolean flagE = FilteredPets.isEmpty();
            if(flagE){
                flag = false;
            }
            if(flag == true )
            {
                PetID = Screen.getIntInput("Invalid ID :( Try Again :");
            }
        }

    }

    public static void DeletePetIDFromShelter(List<Shelter> shelters, int ShelterID, int PetID) {
        for (Shelter S : shelters) {
            if (ShelterID == S.getId()) {
                for (int i = 0; i <= S.PetsIDs.size(); i++) {
                    if (S.PetsIDs.get(i) == PetID) {
                        S.PetsIDs.remove(i);
                        break;
                    }
                }
                break;
            }
        }
    }

    public static void UpdatePet(List<Pet> pets, List<Shelter> shelters) {
        FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID Or -1 To Go Back :");
        if(ShelterID == -1)
            return;
        while(!IsAdminHasAccessToShelter(ShelterID)){
            System.out.println("You Do Not Have Access On This Shelter Try Another One");
            ShelterID = Screen.getIntInput("Enter ShelterID Or [-1] To Go Back :");
            if(ShelterID == -1)
                return;
        }

        // The following code is for displaying Pets on The Shelter;
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : Application.pets) {
            if (p.getShelterId() == ShelterID) {
                FilteredPets.add(p);
            }
        }
        ShowPetsList(FilteredPets, "Filtered Pets By ShelterID : " + ShelterID);

        int PetID = Screen.getIntInput("Enter The Pet ID Or [-1] To Go Back :");
        if(PetID == -1)
            return;

        Pet pet = IsPetExistByIDAndShelterID(PetID,ShelterID);

        while (pet == null) {
            System.out.println("Invalid ID ): Try Again :");
            PetID = Screen.getIntInput("Enter The Pet ID Or [-1] To Go Back :");
            if(PetID == -1)
                return;
            pet = IsPetExistByIDAndShelterID(PetID,ShelterID);
        }

        pet.UpdatePetInfo(shelters);
    }

    public static void ShowPetInfo(Pet p) {
            System.out.printf(
                    "%-5d%-10s%-10s%-10s%-5d%-15s%-10s%-10s\n",
                    p.getId(),
                    p.getName(),
                    p.getBreed(),
                    p.getSpecies(),
                    p.getAge(),
                    p.getHealthStatus(),
                    p.getShelterId(),
                    p.getAvailability()
            );

    }

    public static Pet IsPetExistByID(int PetID) {
        for (Pet p : Application.pets) {
            if (p.getId() == PetID) {

                return p;
            }

        }
        return null;
    }

    public static Pet IsPetExistByIDAndShelterID(int PetID,int ShelterID) {
        for (Pet p : Application.pets) {
            if (p.getId() == PetID && p.getShelterId() == ShelterID) {

                return p;
            }

        }
        return null;
    }

    public static boolean UpdatePetAvailabilityInList(List<Pet> PetsList, int TargetedID, enAvailability newAvailability) {
        for (Pet p : PetsList) {
            if (p.getId() == TargetedID) {
                p.setAvailability(newAvailability);
                return true;
            }
        }
        return false;
    }

    public static void FindPetByID() { // This Function Is Only For Admins
        System.out.println("=== Finding Pet By ID ===");

        int PetID = Screen.getIntInput("Enter Pet ID :");

        Pet p = IsPetExistByID( PetID);
        List<Integer> ManagedShelters = GetManagedSheltersIDs();
        boolean Managed = false;
        if (p != null) {
            for(int ID : ManagedShelters){
                if(p.getShelterId() == ID){
                    ShowPetInfo(p);
                    Managed = true;
                    break;
                }
            }
            if(!Managed){
                System.out.println("You Do not Have Access On that Pet !");
            }

        } else {
            System.out.println("Invalid ID......!");
        }
        Screen.pauseScreen();
    }

    public static void ShowPetsList(List<Pet> pets, String Title) {
        Screen.PrintHeader(Title);
        System.out.printf("%-5s%-10s%-10s%-10s%-5s%-15s%-10s%-10s\n",
                "ID", "Name","Breed" ,"Species", "Age" ,"Health Status" ,
                "ShelterID" , "Availability");
        System.out.println("------------------------------------------------------------------------------------");
        for (Pet p : pets) {
            ShowPetInfo(p);
            System.out.println("------------------------------------------------------------------------------------");
        }

        System.out.println("Count :" + pets.stream().count() + " Pet.");

    }

    public static void ShowAllPets() {
        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        ShowPetsList(pets, "All Pets");
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredByName() {
        System.out.println("=== Filtering Pets By Name ===");
        List<Pet> pets;
        String TargetedName = Screen.getInput("Enter A Name To Filter By: ");

        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getName().equals(TargetedName)) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By Name : " + TargetedName);
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredByAge() {
        System.out.println("=== Filtering Pets By Age ===");
        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;
        int TargetedAge = Screen.getIntInput("Enter An Age To Filter By: ");
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getAge() == TargetedAge) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By Age : " + TargetedAge);
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredBySpecies() {
        System.out.println("=== Filtering Pets By Species ===");

        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        String TargetedSpecies = Screen.getInput("Enter A Species To Filter By: ");
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getSpecies().equals(TargetedSpecies)) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By Species : " + TargetedSpecies);
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredByBreed() {
        System.out.println("=== Filtering Pets By Breed ===");

        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        String TargetedBreed = Screen.getInput("Enter A Breed To Filter By: ");
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getBreed().equals(TargetedBreed)) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By Breed : " + TargetedBreed);
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredByShelterID() {
        System.out.println("=== Filtering Pets By ShelterID ===");

        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        int TargetedShilterID = Screen.getIntInput("Enter A Shilter ID to Filter By :");
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getShelterId() == TargetedShilterID) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By ShilterID : " + TargetedShilterID);
        Screen.pauseScreen();
    }

    public static void ShowPetsFilteredByAvailablity() {
        System.out.println("=== Filtering Pets By Availability ===");

        List<Pet> pets;
        if(Application.currentUser instanceof Admin){
            pets = GetManagedPets();
        }
        else
            pets = Application.pets;

        enAvailability TargetedAvailability;
        int Result = Screen.getIntInput("Enter Availability Mode [1]Available , [0]Unavailable");
        List<Pet> FilteredPets = new ArrayList<>();
        TargetedAvailability = (Result == 1) ? enAvailability.AVAILABLE : enAvailability.ADOPTED;

        for (Pet p : pets) {
            if (p.getAvailability() == TargetedAvailability) {
                FilteredPets.add(p);
            }
        }

        ShowPetsList(FilteredPets, "Filtered Pets By Availability : " + TargetedAvailability);
        Screen.pauseScreen();
    }

    public static void showSheltersInfo() {

        if (Application.shelters.isEmpty()) {
            System.out.println("No shelters available to display.");
            return;
        }

        // Header
        System.out.printf("%-10s%-20s%-20s%-20s\n", "ID", "Name", "Location", "Contact Info");

        // Display each shelter
        for (Shelter shelter : Application.shelters) {
            System.out.printf(
                    "%-10d%-20s%-20s%-20s\n",
                    shelter.getId(),
                    shelter.getName(),
                    shelter.getLocation(),
                    shelter.getContactInfo()
            );
        }
    }

    private static List<Shelter> GetManagedShelters(){
        List<Integer> ManagedSheltersIDs = GetManagedSheltersIDs();
        List<Shelter> ManagedShelters = new ArrayList<>();
        Shelter S;
        for(int I : ManagedSheltersIDs){
            S = IsShelterExistedByID(I);
            ManagedShelters.add(S);
        }
        return ManagedShelters;
    }

    private static List<Integer> GetManagedSheltersIDs(){
        List<Integer> ManagedShelters = new ArrayList<>();
        if(Application.currentUser instanceof Admin){
            ManagedShelters = ((Admin) Application.currentUser).getManagedShelters();
        }
        return ManagedShelters;
    }

    private static List<Pet> GetManagedPets(){
        List<Pet> ManagedPets = new ArrayList<>();
        List<Integer> ManagedShelters = GetManagedSheltersIDs();

        for(Pet P : Application.pets){
            for(int ID : ManagedShelters){
                if(P.getShelterId() == ID){
                    ManagedPets.add(P);
                }
            }
        }
        return ManagedPets;
    }

    public static boolean IsAdminHasAccessToShelter(int ShelterID){
        List<Integer> ManagedShelters = GetManagedSheltersIDs();

        for(int ID : ManagedShelters){
            if(ID == ShelterID){
                return true;
            }
        }
        return false;
    }

    public static void FilterSheltersByAdmin(){
        List<Integer> managedShelters = GetManagedSheltersIDs();
        System.out.println("===Managed Shelters===");
        System.out.printf(
                "%-5s%-10s%-10s%-10s\n",
                "ID", "Name", "Location", "Contact"
        );
        System.out.println("------------------------------------------------");
        Shelter S ;
        for(int ID : managedShelters){
            S = IsShelterExistedByID(ID);
            if(S != null)
            S.displayShelterDetails();
        }
    }

}






