package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by user on 4/12/2019.
 */

public class AdminaddpayF extends Fragment {
    EditText E1;
    Button b;
    DatabaseReference databaseReference;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.addpay, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("PaymentMethod");
        E1=(EditText) view.findViewById(R.id.Apayname);


        b=(Button)  view.findViewById(R.id.paysubmit);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    addpay();

            }
        });

        return view;
    }
    public void addpay(){
        final String item=E1.getText().toString().trim();

        if(!Constants.isNetworkAvailable(getContext())){
            Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
        else if(item.isEmpty()  ){

                E1.setError("required");



        }
        else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(item)) {
                        Toast.makeText(getContext(),"exists",Toast.LENGTH_SHORT).show();
                        // it exists!
                    }else{
                        // does not exist

                        PaymentMethodDb paymentMethodDb=new PaymentMethodDb(item,item);
                        // String key=databaseReference.push().getKey();
                        databaseReference.child(item).setValue(paymentMethodDb);




                        Toast.makeText(getContext(),"added",Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();
                    }
                }

                @Override
                public void onCancelled( DatabaseError databaseError) {

                }
            });
        }

    }
}
