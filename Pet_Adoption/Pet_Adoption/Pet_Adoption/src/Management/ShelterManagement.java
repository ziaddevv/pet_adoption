package Management;

import Main.*;
import Model.*;
import UI.*;

import java.util.ArrayList;
import java.util.List;

public class ShelterManagement {



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



    public static boolean IsShelterFree(Shelter S) {return (S.PetsIDs.isEmpty());}


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

    public static boolean UpdatePetAvailabilityInList(List<Pet> PetsList, int TargetedID, enavailability newAvailability) {
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

        enavailability TargetedAvailability;
        int Result = Screen.getIntInput("Enter Availability Mode [1]Available , [0]Unavailable");
        List<Pet> FilteredPets = new ArrayList<>();
        TargetedAvailability = (Result == 1) ? enavailability.AVAILABLE : enavailability.ADOPTED;

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













