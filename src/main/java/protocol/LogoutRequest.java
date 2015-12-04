package protocol;

import java.io.Serializable;

public class LogoutRequest implements Serializable
{
	//Har til formål at logge ud af programmet, når man er færdig med at spille eller andet
    private String username;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
