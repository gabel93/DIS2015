package protocol;

import java.io.Serializable;

public class LogoutRequest implements Serializable
{
	//Har til form�l at logge ud af programmet, n�r man er f�rdig med at spille eller andet
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
