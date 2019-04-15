package machersstudio.aust.com.anytlet;

/**
 * Created by user on 4/11/2019.
 */

public class ItemsDb {
    public String itemId;
    public String Name;
    public String charge;

    public ItemsDb() {
    }

    public ItemsDb(String itemId, String name, String charge) {
        this.itemId = itemId;
        this.Name = name;
        this.charge = charge;
    }
}
