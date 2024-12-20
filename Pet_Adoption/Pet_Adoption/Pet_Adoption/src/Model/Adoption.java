package Model;

import UI.*;
import Utility.*;
import Management.*;
import Main.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Adoption {
    private int id;
    private int petID;
    private int adopterID;
    private LocalDate adoptionDate;
    private AdoptionStatus status; // Enum for status

    public static int MAXID = 0;


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

    public void setStatus(AdoptionStatus newStatus)
    {
        this.status = newStatus;
    }

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

    public static boolean UpdateAdoptionStatusInList(List<Adoption> AdoptionsList, int TargetedID, AdoptionStatus newStatus) {
        for (Adoption a : AdoptionsList)
        {
            if (a.getId() == TargetedID)
            {
                a.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }
    private static void ChangeStatusForRequests(int IDpet,int IDAdopter)
    {
        for (Adoption a : Application.adoptions)
        {
            if (a.getPetID() == IDpet&&a.getAdopterID()!=IDAdopter&&a.getStatus().equals(AdoptionStatus.REJECTED))
            {
                a.setStatus(AdoptionStatus.REJECTED);
                Application.notificaions.add(new Notifications(a.getAdopterID(),Application.currentUser.getId(),
                        IDpet,"Rejected",false, LocalDate.now()));
            }
        }
    }

    private static void ShowOneRequest(Adoption req) {
        Adopter adopter = AdopterManagement.IsAdopterExistedByID(Application.adopters, req.getAdopterID());
        Pet pet = ShelterManagement.IsPetExistByID(req.getPetID());
        Shelter shelter = ShelterManagement.IsShelterExistedByID(pet.getShelterId());

        String Separator = "------------------------------------------";
        System.out.println(Separator);
        System.out.printf("%-15s : %s%n", "Adoption ID", req.getId());
        System.out.printf("%-15s : ID(%s) - Name(%s)%n", "From Shelter", shelter.getId(), shelter.getName());
        System.out.printf("%-15s : %s%n", "Adopter", adopter.getUsername());
        System.out.printf("%-15s : ID(%s) - Species(%s) - Name(%s) - Age(%s)%n", "Pet", pet.getId(), pet.getSpecies(), pet.getName(), pet.getAge());
        System.out.printf("%-15s : %s%n", "Request Date", req.getAdoptionDate());
        System.out.printf("%-15s : %s%n", "Status", req.getStatus());
        System.out.println(Separator);

    }

    private static void ShowRequestsList(List<Adoption> Requests , String Title){
        Screen.PrintHeader(Title);
        System.out.println("Count :"+ Requests.stream().count() + " Request(s).\n");

        for(Adoption r : Requests){
            ShowOneRequest(r);
            System.out.println("------------------------------------------\n");
        }

    }

    private static List<Adoption> FilterRequestsByStatus(List<Adoption> Requests, boolean isPending) {
        List<Adoption> FilteredRequests = new ArrayList<>();

        for (Adoption r : Requests){
            if((isPending && (r.getStatus() == AdoptionStatus.PENDING)) || (!isPending && (r.getStatus() != AdoptionStatus.PENDING))){
                FilteredRequests.add(r);
            }
        }

        return FilteredRequests;

    }

    public static List<Adoption> FilterRequestsByUser(List<Adoption> Requests, User user) {
        List<Adoption> FilteredRequests = new ArrayList<>();

        if (user instanceof Admin)
        {
            List<Integer> managedShelters = ((Admin) user).getManagedShelters();

            for (Adoption r : Requests) {
                Pet pet = ShelterManagement.IsPetExistByID(r.getPetID());
                for (Integer shelterId : managedShelters)
                {
                    if(pet.getShelterId() == shelterId)
                        FilteredRequests.add(r);
                }
            }
        }
        else if (user instanceof Adopter)
        {
            for (Adoption r : Requests){
                if (r.getAdopterID() == user.getId())
                    FilteredRequests.add(r);

            }
        }

        return FilteredRequests;
    }

    private static List<Adoption> FilterRequestsByUserAndStatus(List<Adoption> Requests, User user, boolean isPending) {
       return FilterRequestsByStatus(FilterRequestsByUser(Requests, user), isPending);
    }

    private static void ShowAllRequestsForUser(List<Adoption> Requests, String Title, User user){
        ShowRequestsList(FilterRequestsByUser(Requests, user), Title);
    }

    private static void HandleOneRequest(Adoption Request) {
        Screen.clearScreen();
        System.out.println("=== Handling A Request ===");
        ShowOneRequest(Request);
        System.out.println("[1] Approve.   [2] Reject.  [3] Leave.");

        int Choice = 0;
        while (true) {
            Choice = Screen.getIntInput("Enter Choice: ");

            switch (Choice) {
                case 1:
                    if (UpdateAdoptionStatusInList(Application.adoptions, Request.getId(), AdoptionStatus.APPROVED) &&
                            ShelterManagement.UpdatePetAvailabilityInList(Application.pets, Request.getPetID(), enavailability.ADOPTED) &&
                            AdopterManagement.AddAdoptionToAdopterInList(Application.adopters, Request.getAdopterID(), Request.getId()))
                        System.out.println("Updated Successfully :)");
                         ChangeStatusForRequests(Request.getPetID(),Request.getAdopterID());

                    Application.notificaions.add(new Notifications(Request.adopterID,Application.currentUser.getId(),
                            Request.petID,"Approved",false, LocalDate.now()));
                       // apply reject for all the rest of pending requests on that pet

                    Screen.pauseScreen();
                    return;
                case 2:
                    if (UpdateAdoptionStatusInList(Application.adoptions, Request.getId(), AdoptionStatus.REJECTED))
                        System.out.println("Updated Successfully :)");
                    Application.notificaions.add(new Notifications(Request.adopterID,Application.currentUser.getId(),
                            Request.petID,"Rejected",false, LocalDate.now()));
                    Screen.pauseScreen();
                    return;
                case 3:
                    Screen.pauseScreen();
                    return;
                default : Screen.displayMessage("Invalid choice.");


            }
        }

    }


    public static void ShowAdminPendingRequests(List<Adoption> AllAdoptions, User user) {
        List<Adoption> FilteredRequests = (FilterRequestsByUserAndStatus(AllAdoptions, user, true));
        ShowRequestsList(FilteredRequests, "Pending Requests");

        if(FilteredRequests.stream().count() == 0){
            Screen.pauseScreen();
            return;
        }

        int Choice = 0;
        boolean found = false;
        while (!found){

            Choice = Screen.getIntInput("Enter AdoptionID to handle: ");

            for (Adoption a : FilteredRequests)
            {
                if (Choice == a.getId())
                {
                    found = true;
                    HandleOneRequest(a);
                    break;
                }
            }
            if (!found)
                System.out.println("Invalid ID :(");

        }

    }

    public static void ShowAdminHandledRequests(List<Adoption> AllAdoptions, User user) {
        ShowRequestsList(FilterRequestsByUserAndStatus(AllAdoptions, user, false), "Handled Requests");
        Screen.pauseScreen();
    }

    public static Adoption IsAdoptionExistByPetID(int PetID){
        for (Adoption A : Application.adoptions){
            if(A.petID == PetID){
                return A;
            }
        }
        return null;
    }

    public static boolean DeleteAdoption(int PetID) {
        return Application.adoptions.removeIf(A -> A.petID == PetID);
    }



}

