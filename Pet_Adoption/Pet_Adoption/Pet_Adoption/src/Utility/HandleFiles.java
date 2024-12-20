package Utility;

import Model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class HandleFiles {

    private static final String PET_FILE = "Pet_Adoption/Pet_Adoption/Pet_Adoption/pets.txt";
    private static final String SHELTERS_FILE = "Pet_Adoption/Pet_Adoption/Pet_Adoption/shelters.txt";
    private static final String USERS_FILE = "Pet_Adoption/Pet_Adoption/Pet_Adoption/users.txt";
    private static final String ADOPTIONS_FILE = "Pet_Adoption/Pet_Adoption/Pet_Adoption/adoptions.txt";
    private static final String NOTIFICATIONS_FILE = "Pet_Adoption/Pet_Adoption/Pet_Adoption/notifications.txt";

    public static List<Pet> loadPets() {
        List<Pet> pets = new ArrayList<>();
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(PET_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String species = parts[2];
                    String breed = parts[3];
                    int age = Integer.parseInt(parts[4]);
                    String healthStatus = parts[5];
                    int shelterID = Integer.parseInt(parts[6]);
                    enavailability availability = enavailability.valueOf(parts[7]);
                    pets.add(new Pet(id, name, species, breed, age, healthStatus, shelterID, availability));
                    maxID = Math.max(maxID, id);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing pet data: " + line);
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid enum value for pet availability: " + line);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Pet file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading pet file: " + e.getMessage());
        }

        Pet.setMaxID(maxID);
        return pets;
    }

    public static List<Notifications> loadNotifications() {
        List<Notifications> notifications = new ArrayList<>();
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    int adopterID = Integer.parseInt(parts[1]);
                    int adminID = Integer.parseInt(parts[2]);
                    int petID = Integer.parseInt(parts[3]);
                    String status = parts[4];
                    LocalDate issueDate = LocalDate.parse(parts[5]);
                    boolean isRead = Boolean.parseBoolean(parts[6]);
                    notifications.add(new Notifications(id, adopterID, adminID, petID, status, isRead, issueDate));
                    maxID = Math.max(maxID, id);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing notification data: " + line);
                    continue;
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date in notification data: " + line);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Notifications file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading notifications file: " + e.getMessage());
        }

        Notifications.setMaxID(maxID);
        return notifications;
    }

    public static void saveNotifications(List<Notifications> notifications) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATIONS_FILE))) {
            for (Notifications notification : notifications) {
                writer.write(notification.getNotificationID() + "," + notification.getAdopterID() +
                        "," + notification.getAdminID() + "," + notification.getPetID() + "," +
                        notification.getStatus() + "," + notification.getIssueDate() +
                        "," + notification.getIsReaded());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to notifications file: " + e.getMessage());
        }
    }

    public static void savePets(List<Pet> pets) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PET_FILE))) {
            for (Pet pet : pets) {
                writer.write(pet.getId() + "," + pet.getName() + "," + pet.getSpecies() + "," +
                        pet.getBreed() + "," + pet.getAge() + "," + pet.getHealthStatus() + "," +
                        pet.getShelterId() + "," + pet.getAvailability());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to pets file: " + e.getMessage());
        }
    }

    public static void loadUsers(List<Admin> admins, List<Adopter> adopters) {
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    maxID = Math.max(maxID, id);
                    String username = parts[1];
                    String password = parts[2];
                    String role = parts[3].trim();

                    if (role.equalsIgnoreCase("Admin")) {
                        List<Integer> managedShelters = new ArrayList<>();
                        if (parts.length > 4 && !parts[4].isEmpty()) {
                            String[] shelterIDs = parts[4].split("\\|");
                            for (String shelterID : shelterIDs) {
                                if (!shelterID.isEmpty()) {
                                    managedShelters.add(Integer.parseInt(shelterID));
                                }
                            }
                        }
                        admins.add(new Admin(id, username, password, managedShelters));
                    } else if (role.equalsIgnoreCase("Adopter")) {
                        List<Integer> adoptionHistory = new ArrayList<>();
                        if (parts.length > 4 && !parts[4].isEmpty()) {
                            String[] historyIDs = parts[4].split("\\|");
                            for (String historyID : historyIDs) {
                                if (!historyID.isEmpty()) {
                                    adoptionHistory.add(Integer.parseInt(historyID));
                                }
                            }
                        }
                        adopters.add(new Adopter(id, username, password, adoptionHistory));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing user data: " + line);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Users file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
        }

        User.setMaxID(maxID);
    }

    public static void saveUsers(List<Admin> admins, List<Adopter> adopters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Admin admin : admins) {
                StringBuilder ids = new StringBuilder();
                for (int id : admin.getManagedShelters()) {
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
                for (int id : adopter.getAdoptionHistory()) {
                    ids.append(id).append("|");
                }
                if (ids.length() > 0) {
                    ids.setLength(ids.length() - 1);
                }
                writer.write(adopter.getId() + "," + adopter.getUsername() + "," + adopter.getPassword() + ",Adopter," + ids);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to users file: " + e.getMessage());
        }
    }

    public static List<Shelter> loadShelters() {
        List<Shelter> shelters = new ArrayList<>();
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(SHELTERS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
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
                    maxID = Math.max(maxID, id);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing shelter data: " + line);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Shelters file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading shelters file: " + e.getMessage());
        }

        Shelter.setMaxID(maxID);
        return shelters;
    }

    public static void saveShelters(List<Shelter> shelters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHELTERS_FILE))) {
            for (Shelter shelter : shelters) {
                StringBuilder ids = new StringBuilder();
                for (int id : shelter.PetsIDs) {
                    ids.append(id).append("|");
                }
                if (ids.length() > 0) {
                    ids.setLength(ids.length() - 1);
                }
                writer.write(shelter.getId() + "," + shelter.getName() + "," + shelter.getLocation() + "," +
                        shelter.getContactInfo() + "," + ids);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to shelters file: " + e.getMessage());
        }
    }

    public static List<Adoption> loadAdoptions() {
        List<Adoption> adoptions = new ArrayList<>();
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ADOPTIONS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    int petID = Integer.parseInt(parts[1]);
                    int adopterID = Integer.parseInt(parts[2]);
                    LocalDate date = LocalDate.parse(parts[3]);
                    AdoptionStatus status = AdoptionStatus.valueOf(parts[4]);
                    adoptions.add(new Adoption(id, petID, adopterID, date, status));
                    maxID = Math.max(maxID, id);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing adoption data: " + line);
                    continue;
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date in adoption data: " + line);
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid enum value for adoption status: " + line);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Adoptions file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading adoptions file: " + e.getMessage());
        }

        Adoption.setMaxID(maxID);
        return adoptions;
    }

    public static void saveAdoptions(List<Adoption> adoptions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADOPTIONS_FILE))) {
            for (Adoption adoption : adoptions) {
                writer.write(adoption.getId() + "," + adoption.getPetID() + "," + adoption.getAdopterID() +
                        "," + adoption.getAdoptionDate() + "," + adoption.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to adoptions file: " + e.getMessage());
        }
    }
}
