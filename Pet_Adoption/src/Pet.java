public class Pet {
    private int id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String healthStatus;
    private int shelterId;
    private Availability availability;

   

    //this should be used only in files
    public Pet(int id,String name, String species, String breed, int age, String healthStatus, int shelterId, Availability availability) {
        this.id = id;  
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.shelterId = shelterId;
        this.availability = availability;
    }
    public Pet(String name, String species, String breed, int age, String healthStatus, int shelterId, Availability availability) {
        this.id = ++MAXID;   
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.healthStatus = healthStatus;
        this.shelterId = shelterId;
        this.availability = availability;
    }
    public static int MAXID=0;
    public static void setMaxID(int maxID) {
        MAXID = maxID;   // I need to set the maximum id after loading from files
    }

    public Availability getAvailability() {
        return availability;
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
                ", Health: " + healthStatus + ", Shelter ID: " + shelterId);
    }
}

enum Availability {
    AVAILABLE, ADOPTED
}
