package Utility;

import Main.Application;
import Management.ShelterManagement;
import Model.Adopter;
import Model.Pet;

import java.time.LocalDate;

public class Notifications {
    private int NotificationID;
    private int AdopterID ;
    private int AdminID;
    private int PetID;
    private String Status;
    private boolean IsReaded;
    private LocalDate IssueDate;

    public static int MaxID = 0;

    public static void setMaxID(int maxID){
        MaxID = maxID;
    }
    public static int getMaxID(){return MaxID;}

    public Notifications( int AdopterID , int AdminID , int PetID , String Status, boolean IsReaded , LocalDate IssueDate){
        this.NotificationID = ++MaxID;
        this.AdopterID = AdopterID;
        this.AdminID = AdminID;
        this.PetID = PetID;
        this.Status = Status;
        this.IsReaded = IsReaded;
        this.IssueDate = IssueDate;
    }
    public Notifications(int NotificationID , int AdopterID , int AdminID , int PetID , String Status,boolean IsReaded, LocalDate IssueDate){
        this.NotificationID = NotificationID;
        this.AdopterID = AdopterID;
        this.AdminID = AdminID;
        this.PetID = PetID;
        this.Status = Status;
        this.IsReaded = IsReaded;
        this.IssueDate = IssueDate;
    }


    public int getNotificationID(){return NotificationID;}
    public int getAdopterID(){return AdopterID;}
    public int getAdminID(){return AdminID;}
    public int getPetID(){return PetID;}
    public String getStatus(){return Status;}
    public LocalDate getIssueDate(){return IssueDate;}
    public boolean getIsReaded(){ return  IsReaded;}
    public void setIsReaded(boolean IsReaded){
        this.IsReaded = IsReaded;
    }

    public void ShowNotificationToAdopter(){
        Pet pet= ShelterManagement.IsPetExistByID(this.PetID);
        if(Status.equals("Approved")) {
            System.out.println("Your Request To Adopt The " + pet.getName() + " Has Been Approved By" +
                    "The Admin With ID = " + this.AdminID);
        }
        else {
            System.out.println("Unfortunately Your Request To Adopt The " + pet.getName() + " Has Been Rejected By" +
                    "The Admin With ID = " + this.AdminID);
        }
        System.out.println(this.IssueDate);
    }

    public static int NumberOfNotreadNotifications(Adopter adopter)
    {
        int count=0;
        if(Application.notificaions==null)
            return 0;
        else
        {
            for (Notifications notifications:Application.notificaions)
            {
                if(notifications.getIsReaded()==false&&notifications.getAdopterID()==adopter.getId())
                {
                    count++;
                }
            }
        }
        return count;
    }

}
