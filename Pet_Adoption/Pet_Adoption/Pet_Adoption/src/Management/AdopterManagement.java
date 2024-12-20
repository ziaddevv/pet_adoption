package Management;
import java.util.List;
import java.util.Scanner;
import Model.Adopter;
public class AdopterManagement {


    public static Adopter IsAdopterExistedByID(List<Adopter>Adopters ,int AdopterID){;

        for(Adopter a : Adopters){
            if(a.getId() == AdopterID){

                return a;
            }

        }
        return  null ;
    }

    public static boolean AddAdoptionToAdopterInList(List<Adopter> adoptersList, int adopterId, int adoptionId) {
        for (Adopter a : adoptersList){
            if (a.getId() == adopterId)
            {
                a.AddAdoptionIdToHistory(adoptionId);
                return true;
            }
        }
        return false;
    }


}
