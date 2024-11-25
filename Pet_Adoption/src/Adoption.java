import java.time.LocalDate;


enum AdoptionStatus {
    PENDING, APPROVED, REJECTED;
}

public class Adoption {
    private int id;
    private int petID;
    private int adopterID;

    public int getAdopterID() {
        return adopterID;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public int getId() {
        return id;
    }

    public int getPetID() {
        return petID;
    }

    public AdoptionStatus getStatus() {
        return status;
    }

    private LocalDate adoptionDate;
    private AdoptionStatus status; // Enum for status

    public Adoption(int id, int pet, int adopter, LocalDate adoptionDate) {
        this.id = id;
        this.petID = pet;
        this.adopterID = adopter;
        this.adoptionDate = adoptionDate;
        this.status = AdoptionStatus.PENDING; // def value
    }
    public Adoption( int pet, int adopter, LocalDate adoptionDate) {
        this.id = ++MAXID;
        this.petID = pet;
        this.adopterID = adopter;
        this.adoptionDate = adoptionDate;
        this.status = AdoptionStatus.PENDING; // def value
    }
    public Adoption(int id, int pet, int adopter, LocalDate adoptionDate,AdoptionStatus adoptionStatus) {
        this(id,pet,adopter,adoptionDate);
        this.status = adoptionStatus; 
    }// this is another contructor for 
    public Adoption( int pet, int adopter, LocalDate adoptionDate,AdoptionStatus adoptionStatus) {
        this(pet,adopter,adoptionDate);
        this.status = adoptionStatus; 
    }// this is another contructor for 

    public static int MAXID=0;
    public static void setMaxID(int maxID) {
        MAXID = maxID;   // I need to set the maximum id after loading from files
    }

    

    public void displayAdoptionDetails() {
        System.out.println("Adoption ID: " + id);
        System.out.println("PetID: "+petID);
        System.out.println("AdopterID: " +adopterID);
        System.out.println("Adoption Date: " + adoptionDate);
        System.out.println("Status: " + status);
    }


 }

