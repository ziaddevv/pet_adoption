package Analytics;
import Main.*;
import Model.*;
import Management.*;
import Utility.*;
import UI.*;
import java.util.ArrayList;
import java.util.List;



public class Analytics {
    public class clsMostAdoptedSpecies{
        public clsMostAdoptedSpecies(String Species, int Freq){
            this.Species = Species;
            this.Freq = Freq;
        }
        public String Species;
        public int Freq = 0;
    }

    private static boolean IsSpeceisExistOnTheList(List<clsMostAdoptedSpecies> list , String Species){
        if(list == null)
            return false;
        for(clsMostAdoptedSpecies M : list){
            if(M.Species.equals(Species)){
                return true;
            }
        }
        return false;
    }

    private List<clsMostAdoptedSpecies> CountSpeciesFrequence(){
        List<clsMostAdoptedSpecies> mostAdoptedSpecies = new ArrayList<>();
        clsMostAdoptedSpecies currentNode = new clsMostAdoptedSpecies("",0);
        for(int i = 0; i < Application.pets.size(); i++){
            if(!IsSpeceisExistOnTheList(mostAdoptedSpecies,Application.pets.get(i).getSpecies())){ // Means that It's the first Time For The Species To Appear
                currentNode = new clsMostAdoptedSpecies(Application.pets.get(i).getSpecies(),1);
                mostAdoptedSpecies.add(currentNode);
            }
            else { // It means that the Species was Frequenced so we need to increment the counter
                for(clsMostAdoptedSpecies M : mostAdoptedSpecies){
                    if(M.Species.equals(currentNode.Species)){
                        M.Freq += 1;
                    }
                }
            }
        }
        return mostAdoptedSpecies;
    }

    public void showTheSpeciesPreference(){
        List<clsMostAdoptedSpecies> mostAdoptedSpecies = CountSpeciesFrequence();
        System.out.println("===Analysis Of Species===");
        System.out.printf("%-10s%-15s\n",
                "Species", "No of Adoptions");
        for(clsMostAdoptedSpecies M : mostAdoptedSpecies ){
            System.out.printf("%-10s%-15s\n",
                    M.Species , M.Freq);
        }
        Screen.pauseScreen();
    }

    private static int CountNumOfAdoptedPetsInShelter(Shelter S){
        int Counter = 0;
        Pet pet;
        for(int pID : S.PetsIDs){
            pet = ShelterManagement.IsPetExistByID(pID);
            if(pet != null && pet.getAvailability() == enavailability.ADOPTED){
                Counter++;
            }
        }

        return Counter;
    }
    private static int CountNumOfAvailablePetsInShelter(Shelter S){
        int Counter = 0;
        Pet pet;
        for(int pID : S.PetsIDs){
            pet = ShelterManagement.IsPetExistByID(pID);
            if(pet != null && pet.getAvailability() == enavailability.AVAILABLE){
                Counter++;
            }
        }

        return Counter;
    }

    private static void DisplayShelterPerformance(String name, int counterAdopted, int counterAvailable) {
        System.out.printf("%-15s %-20d %-20d\n", name, counterAdopted, counterAvailable);
    }


    public static void showSheltersPerformance(){
        System.out.println("===Model.Shelter Performance===");
        System.out.println("Shelter Name    Number of Adopted    Number of Available");
        for(Shelter S : Application.shelters){
            DisplayShelterPerformance(S.getName(),CountNumOfAdoptedPetsInShelter(S),CountNumOfAvailablePetsInShelter(S));


        }
        Screen.pauseScreen();
    }
    //  The Monthly and Yearly trends
    public static void calculateAdoptionTrends(String trendType) {
        System.out.println("=== " + (trendType.equalsIgnoreCase("monthly") ? "Monthly" : "Yearly") + " Adoption Trends ===");
        System.out.printf("%-15s%-10s\n", trendType.equalsIgnoreCase("monthly") ? "Month-Year" : "Year", "Adoptions");

        List<String> timePeriods = new ArrayList<>();

        for (Adoption adoption : Application.adoptions) {
            String formattedDate;

            if (trendType.equalsIgnoreCase("monthly")) {
                formattedDate = adoption.getAdoptionDate().getMonthValue() + "-" + adoption.getAdoptionDate().getYear();
            } else if (trendType.equalsIgnoreCase("yearly")) {
                formattedDate = String.valueOf(adoption.getAdoptionDate().getYear());
            } else {
                System.out.println("Invalid trend type. Use 'monthly' or 'yearly'.");
                return;
            }

            if (!timePeriods.contains(formattedDate)) {
                timePeriods.add(formattedDate);
            }
        }

        for (String timePeriod : timePeriods) {
            int count = 0;
            for (Adoption adoption : Application.adoptions) {
                String formattedDate = trendType.equalsIgnoreCase("monthly")?
                        adoption.getAdoptionDate().getMonthValue() + "-" + adoption.getAdoptionDate().getYear()
                        : String.valueOf(adoption.getAdoptionDate().getYear());

                if (formattedDate.equals(timePeriod)) {
                    count++;
                }
            }
            System.out.printf("%-15s%-10d\n", timePeriod, count);
        }
        Screen.pauseScreen();
    }



    // List of adopters with the highest number of adoptions
    public static List<Adopter> getTopAdopters() {
        List<Adopter> topAdopters = new ArrayList<>();
        int maxAdoptions = 0;

        for (Adopter adopter : Application.adopters) {
            int adoptionCount = adopter.getAdoptionHistory().size();
            if (adoptionCount > maxAdoptions) {
                maxAdoptions = adoptionCount;
            }
        }

        for (Adopter adopter : Application.adopters) {
            if (adopter.getAdoptionHistory().size() == maxAdoptions) {
                topAdopters.add(adopter);
            }
        }

        return topAdopters;
    }
    public static void DisplayTheTopAdopters(){
        Screen.clearScreen();
        List<Adopter> topAdopters = getTopAdopters();
        System.out.println("Top Adopters:");
        for (Adopter adopter : topAdopters) {
            System.out.println("Adopter ID: " + adopter.getId() + ", Username: " + adopter.getUsername());
        }
        Screen.pauseScreen();
    }


    public static void showAdoptionRequestStatistics() {
        Screen.clearScreen();
        int totalRequests = Application.adoptions.size();
        int processedRequests = 0;

        // Count processed requests
        for (Adoption adoption : Application.adoptions) {
            if (adoption.getStatus() != AdoptionStatus.PENDING) {
                processedRequests++;
            }
        }

        // Display the statistics
        System.out.println("=== Adoption Request Statistics ===");
        System.out.println("Total Requests Received: " + totalRequests);
        System.out.println("Total Requests Processed: " + processedRequests);
        System.out.println("Pending Requests: " + (totalRequests - processedRequests));

        Screen.pauseScreen();
    }

}
