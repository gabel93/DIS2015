package protocol;

import java.io.Serializable;

public class GetGameRequest implements Serializable
{
	//Har til formål at hente spil i terminalen
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
