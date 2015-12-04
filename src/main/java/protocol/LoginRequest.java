package protocol;

import java.io.Serializable;

public class LoginRequest implements Serializable
{
	//Har til formål at sørge for login funktionen, hvis man vil ind i menuen.
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
