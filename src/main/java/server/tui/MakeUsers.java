package server.tui;

import java.util.List;

import server.backend.Services;
import database.User;

public class MakeUsers {
	//Denne klasse har til form�l at lave nye brugere i menuen og gemme dem i databasen
	//Derfor bliver der importeret database. Den giver ogs� mulighed for at se de nuv�rende
	//brugere.
	public static void main(String[] strings)
    {
        String[] names = {"husk", "at", "s�tte", "nogle", "navne"};
        for(String name : names)
        {
            User user = new User();
            user.setUsername(name);
            user.setPassword(name);
            Services.INSTANCE.add(user);
        }

        List<User> users = Services.INSTANCE.getUsers();
        System.out.println("Current users: ");
        for(User user : users)
        {
            System.out.println(user.getUsername());
        }
    }
}

