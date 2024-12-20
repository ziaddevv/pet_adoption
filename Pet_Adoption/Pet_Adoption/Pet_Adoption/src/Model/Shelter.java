package Model;

import Main.Application;
import Management.ShelterManagement;
import UI.Screen;

import java.util.List;

public class Shelter {
    private int id;
    private String name;
    private String location;
    private String contactInfo;
    public List<Integer> PetsIDs; // List of Model.Pet IDs for pets
    
    public String getContactInfo() {
        return contactInfo;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }


    public Shelter(int id, String name, String location, String contactInfo,List<Integer>PetsIDs) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.PetsIDs=PetsIDs;
    }
    public Shelter( String name, String location, String contactInfo,List<Integer>PetsIDs) {
        this.id = ++MAXID;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.PetsIDs=PetsIDs;
    }
    public static int MAXID=0;
    public static void setMaxID(int maxID) {
        MAXID = maxID;   // I need to set the maximum id after loading from files
    }

    public void addPet(int petId) {
        PetsIDs.add(petId);
    }

    public void UpdateShelterInfo(){
        Shelter S = ShelterManagement.IsShelterExistedByID(this.getId());
        this.name = Screen.getInput("\nEnter The Name Of The Shelter :");
        this.location = Screen.getInput("\nEnter The Location Of The Shelter :");
        this.contactInfo = Screen.getInput("\nEnter Contact Info :");

        System.out.println("Shelter Was Updated Successfully");
    }
    public void displayShelterDetails() {
        System.out.printf(
                "%-5s%-10s%-10s%-10s\n",
        id , name , location , contactInfo );
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
}
