package protocol;

import java.io.Serializable;

public class GetGameRequest implements Serializable
{
	//Har til form�l at hente spil i terminalen, n�r man har �bnet en klient vel og m�rke
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
