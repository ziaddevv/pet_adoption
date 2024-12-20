package Model;

import java.util.List;
import UI.*;
import Management.*;


public class Pet {
    private int id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String healthStatus;
    private int shelterId;
    private enavailability availability;
    private enMode _Mode = enMode.Update;

    public static int MAXID=0;




    //this should be used only in files
    public Pet(int id,String name, String species, String breed, int age,
               String healthStatus, int shelterId, enavailability availability) {
        this.id = id;  
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.shelterId = shelterId;
        this.availability = availability;

        _Mode = enMode.Update;
    }

    public Pet(String name, String species, String breed, int age,
               String healthStatus, int shelterId, enavailability availability) {
        this.id = ++MAXID;   
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.shelterId = shelterId;
        this.availability = availability;

        _Mode = enMode.AddNew;
    }

    public static void setMaxID(int maxID) {
        MAXID = maxID;   // I need to set the maximum id after loading from files
    }

    public enavailability getAvailability() {
        return availability;
    }

    public void setAvailability(enavailability availability) {
        this.availability = availability;
    }

    public int getShelterId() {
        return shelterId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void displayPetDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Species: " + species +
                ", Breed: " + breed + ", Age: " + age +
                ", Health: " + healthStatus + ", Model.Shelter ID: " + shelterId);
    }

    public void UpdatePetInfo(List<Shelter> shelters){
        String Name  ,HealthStatus ;
        int age , ShelterID ;
        enavailability Availablity;

        Name = Screen.getInput("Enter The New Name :");
        HealthStatus = Screen.getInput("Enter Health Status :");
        age = Screen.getIntInput("Enter The Age :");
        ShelterManagement.FilterSheltersByAdmin();
        ShelterID = Screen.getIntInput("Enter The Shelter ID :");
        while(!ShelterManagement.IsAdminHasAccessToShelter(ShelterID)) {
            ShelterID = Screen.getIntInput("You Do not Have Access On this Shelter Try Another One :");
        }

        this.name = Name;
        this.age = age ;
        this.healthStatus = HealthStatus;

        ShelterManagement.DeletePetIDFromShelter(shelters,this.shelterId,this.getId());
        this.shelterId = ShelterID;
        for(Shelter S : shelters){  // To Add The Model.Pet ID To The New Model.Shelter;
            if(this.getShelterId() == S.getId()){
                S.PetsIDs.add(this.getId());
            }
        }

        System.out.println("Updated Suceessfully (:");
        Screen.pauseScreen();


    }

    public boolean IsPetAvailable(){
        return (this.getAvailability() == enavailability.AVAILABLE);
    }


}

enum enMode{
    AddNew,Update
}

enum enBreed{
    Male , Female
}


