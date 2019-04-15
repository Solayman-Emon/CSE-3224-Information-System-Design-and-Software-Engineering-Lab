package machersstudio.aust.com.anytlet;

/**
 * Created by user on 4/12/2019.
 */

public class PostadDb {
    public String Adid;
    public String ownerid;
    public String Item;
    public String payment;
    public String img1;

    public String city;
    public String area;
    public String additionaladdress;
    public String roomno;
    public String kitchenno;
    public String bathno;
    public String phone;
    public String rent;

    public PostadDb() {
    }

    public PostadDb(String Adid,String ownerid,String item, String payment, String img1, String city, String area, String additionaladdress, String roomno, String kitchenno, String bathno, String phone, String rent) {
       this.Adid=Adid;
        this.ownerid=ownerid;
        Item = item;
        this.payment = payment;
        this.img1 = img1;

        this.city = city;
        this.area = area;
        this.additionaladdress = additionaladdress;
        this.roomno = roomno;
        this.kitchenno = kitchenno;
        this.bathno = bathno;
        this.phone = phone;
        this.rent = rent;
    }
}
