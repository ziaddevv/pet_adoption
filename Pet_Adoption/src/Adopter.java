import java.util.ArrayList;
import java.util.List;

public class Adopter extends User {
    public List<Integer> adoptionHistory; 


    public Adopter(int id, String username, String password) {
        super(id, username, password);
        this.adoptionHistory = new ArrayList<>();
    }
    public Adopter(int id, String username, String password,List<Integer> adoptionHistory) {
        super(id, username, password);
        this.adoptionHistory =adoptionHistory;
    }
    public Adopter( String username, String password,List<Integer> adoptionHistory) {
        super(username, password);
        this.adoptionHistory =adoptionHistory;
    }



}
