package server.tui;

import java.util.List;

import server.backend.Services;
import database.User;

public class MakeUsers {
	public static void main(String[] strings)
    {
        String[] names = {"", "", "", "", ""};
        for(String name : names)
        {
            User user = new User();
            user.setUsername(name);
            user.setPassword(name);
            Services.INSTANCE.add(user);
        }

}
