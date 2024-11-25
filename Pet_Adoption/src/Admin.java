import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    public List<Integer> managedShelters;

    // Constructor
    public Admin(int id, String username, String password) {
        super(id, username, password);
        this.managedShelters = new ArrayList<>();
    }
    public Admin(int id, String username, String password,List<Integer> managedShaleterss) {
        super(id, username, password);
        this.managedShelters=managedShaleterss;
    }
    public Admin( String username, String password,List<Integer> managedShaleterss) {
        super( username, password);
        this.managedShelters=managedShaleterss;
    }

    public void addShelter(int shelterId) {
        managedShelters.add(shelterId);
    }
 
    public void removeShelter(int shelterId) {
        managedShelters.remove(Integer.valueOf(shelterId));
    }

 
    

    

    
    
}
