package machersstudio.aust.com.anytlet;

/**
 * Created by user on 4/12/2019.
 */

public class PaymentMethodDb {
    public String paymentId;
    public String Name;
    public PaymentMethodDb (){ }
    public PaymentMethodDb(String paymentId, String name) {
        this.paymentId = paymentId;
        this.Name = name;
    }
}
