package protocol;

import java.io.Serializable;

public class DeleteGameRequest implements Serializable
{
	//Har til formål at skabe en funktion hvor der kan slettes spil fra databasen igennem terminalen,
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
