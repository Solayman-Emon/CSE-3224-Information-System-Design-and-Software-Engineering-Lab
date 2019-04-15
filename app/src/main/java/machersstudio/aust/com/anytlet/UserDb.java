package machersstudio.aust.com.anytlet;

/**
 * Created by user on 4/8/2019.
 */

public class UserDb {
    public String UserId;
    public  String name;
    public String address;
    public String phone ;
    public String pass;
    public String OwnerId;
    public String RenterId;
    public String Bkash;
    public String Ukash;

    public UserDb() {
    }

    public UserDb(String userId, String name, String address, String phone, String pass, String ownerId, String renterId, String bkash, String ukash) {
        UserId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pass = pass;
        OwnerId = ownerId;
        RenterId = renterId;
        Bkash = bkash;
        Ukash = ukash;
    }
}
