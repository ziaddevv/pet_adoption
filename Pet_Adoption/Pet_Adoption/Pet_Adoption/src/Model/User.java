package Model;

import Main.Application;
import UI.Screen;

public abstract class User {
    private int id;
    private String username;
    private String password;



    // Constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User( String username, String password) {
        this.id = ++MAXID;
        this.username = username;
        this.password = password;
    }
    public static int MAXID=0;
    public static void setMaxID(int maxID) {
        MAXID = maxID;   // I need to set the maximum id after loading from files
    }
    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword( ) {
    return this.password;
    }
    public void ManageProfile() {   System.out.println("====== Manage Profile =====");
        System.out.println("1. Change UserName");
        System.out.println("2. Change Password");
        System.out.println("3. Back");

        int choice = Screen.getIntInput("Choice");
        switch (choice) {
            case 1:
            {
                String username= Screen.getInput("New UserName is : ");

                while (Application.isUsernameTaken(username))
                {
                    System.out.println("This UserName is already taken :(");
                    username= Screen.getInput("New UserName is : ");
                }

                this.setUsername(username);
                Screen.displayMessage("Your UserName is : "+username);
            }
            break;
            case 2:{

                if(this.getPassword().equals(Screen.getInput("Please Enter the Current Password")))
                {
                    String NewPassword= Screen.getInput("Enter the New Password");
                    // confirm password (if you need ) ---> read it again to confirm
                    this.setPassword(NewPassword);
                }
                else{
                    Screen.getInput("Wrong Password");

                }
            }
            break;
            case 3 :
                System.out.println("LOG OUT");
                return;

            default : Screen.displayMessage("Invalid choice.");
        }
    }



}
