import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HandleFiles {

    private static final String PET_FILE = "pets.txt";
    private static final String SHELTERS_FILE = "shelters.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String ADOPTIONS_FILE = "adoptions.txt";
    

    public static List<Pet> loadPets() throws IOException {
        List<Pet> pets = new ArrayList<>();
        int maxID=0;// catch the maximum id from files
        try (BufferedReader reader = new BufferedReader(new FileReader(PET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String species = parts[2];
                String breed = parts[3];
                int age = Integer.parseInt(parts[4]);
                String healthStatus = parts[5];
                int shelterID = Integer.parseInt(parts[6]);
                Availability availability = Availability.valueOf(parts[7]);
                pets.add(new Pet( id,name, species, breed, age, healthStatus, shelterID, availability));
                maxID=Math.max(maxID,id);
            }
        }
        Pet.setMaxID(maxID);
        return pets;
    }

    public static void savePets(List<Pet> pets) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PET_FILE))) {
            for (Pet pet : pets) {
                writer.write(pet.getId() + "," + pet.getName() + "," + pet.getSpecies() + "," + pet.getBreed() + ","
                        + pet.getAge() + "," + pet.getHealthStatus() + "," + pet.getShelterId() + ","
                        + pet.getAvailability());
                writer.newLine();
            }
        }
        
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void loadUsers(List<Admin> admins, List<Adopter> adopters) throws IOException {
        int maxID=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                maxID=Math.max(maxID, id);///get the maximum id
                String username = parts[1];
                String password = parts[2];
                String role = parts[3].trim();
                if (role.equalsIgnoreCase("Admin")) 
                {
                    List<Integer> managedShelters = new ArrayList<>();
                    if(parts.length>4&&!parts[4].isEmpty())
                        {  
                            String[] shelterIDs = parts[4].split("\\|");
                            for (String shelterID : shelterIDs)
                                {
                                    if (!shelterID.isEmpty())
                                    { managedShelters.add(Integer.parseInt(shelterID));}
                                }
                        }

                    admins.add(new Admin(id, username, password, managedShelters));
                }
                else if (role.equalsIgnoreCase("Adopter"))
                {
                    List<Integer> adoptionHistory = new ArrayList<>();
                    if (parts.length > 4 && !parts[4].isEmpty()) { 
                        
                        String[] historyIDs = parts[4].split("\\|");
                        for (String historyID : historyIDs) 
                        {
                            if (!historyID.isEmpty()) {
                                adoptionHistory.add(Integer.parseInt(historyID));
                            }
                        }
                    }
                    adopters.add(new Adopter(id, username, password, adoptionHistory));
                }
            }
        }
        User.setMaxID(maxID);  
    }

    public static void saveUsers(List<Admin> admins, List<Adopter> adopters) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Admin admin : admins) {
                StringBuilder ids = new StringBuilder();
                for (int id : admin.managedShelters) {
                    ids.append(id).append("|");
                }
                if (ids.length() > 0) {
                    ids.setLength(ids.length() - 1);
                }
                writer.write(admin.getId() + "," + admin.getUsername() + "," + admin.getPassword() + ",Admin," + ids);
                writer.newLine();
            }
            for (Adopter adopter : adopters) {
                StringBuilder ids = new StringBuilder();
                for (int id : adopter.adoptionHistory) {
                    ids.append(id).append("|");
                }
                if (ids.length() > 0) {
                    ids.setLength(ids.length() - 1);
                }
                writer.write(adopter.getId() + "," + adopter.getUsername() + "," + adopter.getPassword() + ",Adopter," + ids);
                writer.newLine();
            }
        }
    
    }

    public static List<Shelter> loadShelters() throws IOException {
        List<Shelter> shelters = new ArrayList<>();
        
        int maxID=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(SHELTERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Integer id = Integer.valueOf(parts[0]);
                String name = parts[1];
                String location = parts[2];
                String contactInfo = parts[3];
                String[] petIDs = parts[4].split("\\|");
                List<Integer> petsIDs = new ArrayList<>();
                for (String petID : petIDs) {
                    if (!petID.isEmpty()) {
                        petsIDs.add(Integer.parseInt(petID));
                    }
                }
                shelters.add(new Shelter(id, name, location, contactInfo, petsIDs));
                maxID=Math.max(maxID, id);
            }
        }
        
        Shelter.setMaxID(maxID);
        return shelters;
    }

    public static void saveShelters(List<Shelter> shelters) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHELTERS_FILE))) {
            for (Shelter shelter : shelters) {
                StringBuilder ids = new StringBuilder();
                for (int id : shelter.PetsIDs) {
                    ids.append(id).append("|");
                }
                if (ids.length() > 0) {
                    ids.setLength(ids.length() - 1);
                }
                writer.write(shelter.getId() + "," + shelter.getName() + "," + shelter.getLocation() + ","
                        + shelter.getContactInfo() + "," + ids);
                writer.newLine();
            }
        }
         
    }

    public static List<Adoption> loadAdoptions() throws IOException {
        List<Adoption> adoptions = new ArrayList<>();
        int maxID=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ADOPTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                int petID = Integer.parseInt(parts[1]);
                int adopterID = Integer.parseInt(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);
                AdoptionStatus status = AdoptionStatus.valueOf(parts[4]);
                adoptions.add(new Adoption(id, petID, adopterID, date, status));

                maxID=Math.max(maxID, id);
            }
        }
        Adoption.setMaxID(maxID);
        return adoptions;
    }

    public static void saveAdoptions(List<Adoption> adoptions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADOPTIONS_FILE))) {
            for (Adoption adoption : adoptions) {
                writer.write(adoption.getId() + "," + adoption.getPetID() + "," + adoption.getAdopterID() + ","
                        + adoption.getAdoptionDate() + "," + adoption.getStatus());
                writer.newLine();
            }
        }
         
    }
}
