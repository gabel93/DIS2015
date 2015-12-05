package server.tui;

import java.util.Scanner;

import server.backend.Services;
import database.Game;
import database.User;

public class Tui {

	public static void main(String[] strings)
    {
        new Tui().start();
    }
    
    private final Scanner scanner = new Scanner(System.in);
    
    public void start()
    {
    	while(true)
        {
            System.out.print("Admin username? [Admin]: ");
            String username = scanner.nextLine();
            if(username.length() == 0)
            {
                username = "Admin";
            }
            
            System.out.print("Admin password? [password]: ");
            String password = scanner.nextLine();
            if(password.length() == 0)
            {
                password = "password";
            }

            User user = Services.INSTANCE.getUserByUsername(username);
            if(user != null&& user.isAdmin()&& password.equals(user.getPassword()))
            {
                break;
            }
            System.out.println("This is not a valid admin.");
        }	
    	
    	questions:
            while(true)
            {
                int option = this.readOption("1. Create a user 2. Delete a user 3. Create a game 4. Quit ", 1, 4);
                switch(option)
                {
                case 1: this.createUser(); break;
                case 2: this.deleteUser(); break;
                case 3: this.createGame(); break;
                default: break questions;
                }
            }
        }
    

   