package machersstudio.aust.com.anytlet;

/**
 * Created by user on 4/13/2019.
 */

public class CommunicateDb {
    public String chatid;
    public String chatid1;
    public String sender;
    public String reciever;
    public String msg;
    public String timee;

    public CommunicateDb() {
    }

    public CommunicateDb(String chatid,String chatid1, String sender, String reciever, String msg, String timee) {
        this.chatid = chatid;
        this.chatid1=chatid1;
        this.sender = sender;
        this.reciever = reciever;
        this.msg = msg;
        this.timee = timee;
    }
}
