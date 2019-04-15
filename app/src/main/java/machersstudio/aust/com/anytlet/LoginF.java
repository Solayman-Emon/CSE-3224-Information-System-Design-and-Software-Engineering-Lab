package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
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
 * Created by user on 4/8/2019.
 */

public class LoginF extends Fragment {
    EditText E1,E2;
    Button b,b1;
    DatabaseReference databaseReference,databaseReference1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.login, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        databaseReference1= FirebaseDatabase.getInstance().getReference("Admins");
        E1=(EditText) view.findViewById(R.id.umail);
        E2=(EditText)  view.findViewById(R.id.upass);
        b1=(Button)  view.findViewById(R.id.nav_slideshow);
        b=(Button)  view.findViewById(R.id.usup);
        while(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               chkLogin();

            }
        });
        return view;
    }
    public void chkLogin()
    {
        String email=E1.getText().toString().trim();
        final  String pass=E2.getText().toString().trim();
        final String mail=email.replace("@gmail.com","");
        if(!Constants.isNetworkAvailable(getContext())){
            Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty() || pass.isEmpty() ){
            if(email.isEmpty()){
                E1.setError("required");

            }
            if(pass.isEmpty()){
                E2.setError("required");

            }

        }
        else {
            if(!mail.equals("jBond")){
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(mail)) {
                            if(snapshot.child(mail).child("pass").getValue().equals(pass)){
                                Toast.makeText(getContext(),"Logged in",Toast.LENGTH_SHORT).show();
                                Constants.UserIdentity=mail;
                                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();



                            }
                            else{
                                Toast.makeText(getContext()," pasword doesnt match",Toast.LENGTH_SHORT).show();
                            }

                            // it exists!
                        }else{
                            // does not exist
                            Toast.makeText(getContext(),"user or gmail doesnt exist",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });
            }
            else{
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(mail)) {
                            if(snapshot.child(mail).child("pass").getValue().equals(pass)){
                                Toast.makeText(getContext(),"Welcome master",Toast.LENGTH_SHORT).show();
                                Constants.UserIdentity=mail;
                                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();



                            }
                            else{
                                Toast.makeText(getContext()," pasword doesnt match",Toast.LENGTH_SHORT).show();
                            }


                            // it exists!
                        }else{
                            // does not exist
                            Toast.makeText(getContext(),"user or gmail doesnt exist",Toast.LENGTH_SHORT).show();;
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });
            }

        }
    }
}
