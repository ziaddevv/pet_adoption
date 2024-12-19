import java.util.ArrayList;
import java.util.Dictionary;
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
        for(int i = 0 ; i < Application.pets.size();i++){
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
            if(pet != null && pet.getAvailability() == enAvailability.ADOPTED){
                Counter++;
            }
        }

        return Counter;
    }

    private static void DisplayShelterPerformance(String Name , int Counter){
            System.out.printf("%-10s%-15s\n",
                    Name , Counter);
    }

    public static void showSheltersPerformance(){
        System.out.println("===Shelter Performance===");
        System.out.printf("%-10s%-15s\n",
                "Shelter", "No of Adoptions");
        for(Shelter S : Application.shelters){
            DisplayShelterPerformance(S.getName(),CountNumOfAdoptedPetsInShelter(S));
        }
        Screen.pauseScreen();
    }

}
