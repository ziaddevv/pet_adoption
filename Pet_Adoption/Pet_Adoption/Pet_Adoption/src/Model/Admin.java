package Model;

import Main.Application;
import UI.Screen;

import java.util.ArrayList;
import java.util.List;
import Management.*;

public class Admin extends User {
    private List<Integer> managedShelters;

    // Constructor
    public Admin(int id, String username, String password) {
        super(id, username, password);
        this.managedShelters = new ArrayList<>();
    }
    public Admin(int id, String username, String password,List<Integer> managedShaleterss) {
        super(id, username, password);
        this.managedShelters=managedShaleterss;
    }
    public Admin( String username, String password,List<Integer> managedShaleterss) {
        super( username, password);
        this.managedShelters=managedShaleterss;
    }



    public List<Integer> getManagedShelters()
    {
        return managedShelters;
    }

    public void addShelter(int shelterId) {
        managedShelters.add(shelterId);
    }


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
    public static void DeleteShelter() {
        ShelterManagement.FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Change The Shelter :");
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)){
            ShelterID = Screen.getIntInput("Invalid ID :( Try Again :");
        }

        String choice;
        boolean flag = true , HasReference = true;

        while(flag) {
            for (Shelter S : Application.shelters) {
                if (ShelterID == S.getId()) {
                    if(!ShelterManagement.IsShelterFree(S)){
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
        ShelterManagement.FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID Or -1 To Go Back :");
        if(ShelterID == -1)
            return;
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)){
            System.out.println("You Do Not Have Access On This Shelter Try Another One");
            ShelterID = Screen.getIntInput("Enter ShelterID Or [-1] To Go Back :");
            if(ShelterID == -1)
                return;
        }

        Shelter S = ShelterManagement.IsShelterExistedByID(ShelterID);
        while(S == null){
            ShelterID = Screen.getIntInput("Invalid ID Enter [-1] to Go Back Or Enter The ID :");
            if(ShelterID == -1)
                return;
            S = ShelterManagement.IsShelterExistedByID(ShelterID);
        }
        S.UpdateShelterInfo();
        System.out.println("Updated Successfully :)");
        Screen.pauseScreen();
    }



    public static void AddNewPet(List<Pet> pets, List<Shelter> shelters) {
        Pet P1;
        String Name, Breed, Species, HealthStatus;
        enavailability Availability = enavailability.AVAILABLE;
        int Age = -1, ShelterID = -1;


        Name = Screen.getInput("Enter The Name Of The Animal :");
        Species = Screen.getInput("\nEnter The Species Of The Animal :");
        Breed = Screen.getInput("\nEnter The Breed Of The Animal :");
        HealthStatus = Screen.getInput("\nEnter The Health Status Of The Animal :");
        Age = Screen.getIntInput("\nEnter The age Of The Animal :");
        ShelterManagement.FilterSheltersByAdmin();
        ShelterID = Screen.getIntInput("\nEnter The ShelterID Or [-1] To Go Back :");
        if(ShelterID == -1)
            return;
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)) {
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
        ShelterManagement.FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID For The Pet :");
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)){
            ShelterID = Screen.getIntInput("Invalid ID :( Try Again :");
        }
        // The following code is for displaying Pets on The Shelter;
        List<Pet> FilteredPets = new ArrayList<>();
        for (Pet p : Application.pets) {
            if (p.getShelterId() == ShelterID&&p.IsPetAvailable()) {
                FilteredPets.add(p);
            }
        }
        ShelterManagement.ShowPetsList(FilteredPets, "Filtered Pets By ShelterID : " + ShelterID);
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
                        ShelterManagement.DeletePetIDFromShelter(Application.shelters, p.getShelterId(), p.getId());
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

    public static void UpdatePet(List<Pet> pets, List<Shelter> shelters) {
        ShelterManagement.FilterSheltersByAdmin();
        int ShelterID = Screen.getIntInput("Enter The Shelter ID Or -1 To Go Back :");
        if(ShelterID == -1)
            return;
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)){
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
        ShelterManagement.ShowPetsList(FilteredPets, "Filtered Pets By ShelterID : " + ShelterID);

        int PetID = Screen.getIntInput("Enter The Pet ID Or [-1] To Go Back :");
        if(PetID == -1)
            return;

        Pet pet = ShelterManagement.IsPetExistByIDAndShelterID(PetID,ShelterID);

        while (pet == null) {
            System.out.println("Invalid ID ): Try Again :");
            PetID = Screen.getIntInput("Enter The Pet ID Or [-1] To Go Back :");
            if(PetID == -1)
                return;
            pet = ShelterManagement.IsPetExistByIDAndShelterID(PetID,ShelterID);
        }

        pet.UpdatePetInfo(shelters);
    }


}
