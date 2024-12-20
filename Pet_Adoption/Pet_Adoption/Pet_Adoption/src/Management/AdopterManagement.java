package Management;
import java.util.List;
import java.util.Scanner;
import Model.Adopter;
public class AdopterManagement {

    public  static void AddNewAdopter(List<Adopter> Adopters){
        Adopter A1;
        String Name = "",Password = "";
        List <Integer> HistoryOfAdoption = List.of();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter The Name Of The Adopter :");
        Name = scanner.next();
        System.out.print("Enter The Password Of The Adopter :");
        Password = scanner.next();

        A1 = new Adopter(Name, Password,HistoryOfAdoption);
        Adopters.add(A1);
    }

    public static void ShowAdopterInfo(Adopter A){

        System.out.print(A.getId() + " | " + A.getUsername() + " | " + A.getPassword());
    }

    public static boolean IsAdopterExistByID(List<Adopter>Adopters ,int AdopterID){;

        for(Adopter A : Adopters){
            if(A.getId() == AdopterID){
                return true;
            }
        }
        return  false ;
    }

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

    public static void DeleteAdopter(List<Adopter> Adopters , int AdopterID){

        for(Adopter A : Adopters){
            if(AdopterID == A.getId()){
                Adopters.remove(A);
                break;
            }
        }

    }



}
