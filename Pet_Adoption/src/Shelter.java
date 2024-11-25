import java.util.ArrayList;
import java.util.List;

public class Shelter {
    private int id;
    private String name;
    private String location;
    private String contactInfo;
    public List<Integer> PetsIDs; // List of Pet IDs for pets
    
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


    public void displayShelterDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Location: " + location +
                ", Contact: " + contactInfo + ", Pets: " + PetsIDs);
    }
}
