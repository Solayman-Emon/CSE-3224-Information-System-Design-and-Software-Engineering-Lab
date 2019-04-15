package machersstudio.aust.com.anytlet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by user on 4/8/2019.
 */

public class RegF extends Fragment {
    EditText E1,E2,E3,E4,E5,E6;
    Button b;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.reg, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        databaseReference1= FirebaseDatabase.getInstance().getReference("Owners");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Renters");

        while(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        E1=(EditText) view.findViewById(R.id.Email);
        E2=(EditText)  view.findViewById(R.id.name);
        E3=(EditText)  view.findViewById(R.id.address);
        E4=(EditText)  view.findViewById(R.id.phn);
        E5=(EditText)  view.findViewById(R.id.pass);
        E6=(EditText)  view.findViewById(R.id.cpass);
        b=(Button)  view.findViewById(R.id.sup);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                saveReg();

            }
        });
        return view;

    }
    public void saveReg()
    {
        String email=E1.getText().toString().trim();
      final  String name=E2.getText().toString().trim();
       final String address=E3.getText().toString().trim();
       final String phn=E4.getText().toString().trim();
      final  String pass=E5.getText().toString().trim();
      final  String cpass=E6.getText().toString().trim();
        final String mail=email.replace("@gmail.com","");
        if(!isNetworkAvailable(getContext())){
            Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
       else if(email.isEmpty() || name.isEmpty()||phn.isEmpty()||pass.isEmpty()||cpass.isEmpty()  ){
            if(email.isEmpty()){
                E1.setError("required");

            }
            if(name.isEmpty()){
                E2.setError("required");

            }
            if(address.isEmpty()){
                E3.setError("required");

            }
            if(phn.isEmpty()){
                E4.setError("required");

            }
            if(pass.isEmpty()){
                E5.setError("required");

            }
            if(cpass.isEmpty()){
                E6.setError("required");

            }

        }
        else if(!pass.equals(cpass)){

            Toast.makeText(getContext(),pass+" "+cpass,Toast.LENGTH_SHORT).show();
        }
        else{






            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(mail)) {
                        Toast.makeText(getContext(),"exists",Toast.LENGTH_SHORT).show();
                        // it exists!
                    }else{
                        // does not exist
                        UserDb userDb=new UserDb(mail,name,address,phn,pass,mail+"o",mail+"r",null,null);
                        OwnerDb ownerDb=new OwnerDb(mail+"o",null);
                        RenterDb renterDb=new RenterDb(mail+"r",null);

                        // String key=databaseReference.push().getKey();
                        databaseReference.child(mail).setValue(userDb);
                        databaseReference1.child(mail+"o").setValue(ownerDb);
                        databaseReference2.child(mail+"r").setValue(renterDb);



                        Toast.makeText(getContext(),"Registered",Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();
                    }
                }

                @Override
                public void onCancelled( DatabaseError databaseError) {

                }
            });

        }

        return;
    }
    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
