package protocol;

import java.io.Serializable;

public class SetCommandsRequest implements Serializable
{
	//En lidt st�rre klasse da der her bliver defineret kommandoer til spil.
	//F�rst findes der et username, s� det spil man vil spille og til sidst de kommandoer
	//Du har lyst til at s�tte i forbindelse med dit spil.
	private String username;
    private String gameName;
    private String commands;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getCommands()
    {
        return commands;
    }

    public void setCommands(String commands)
    {
        this.commands = commands;
    }

}

