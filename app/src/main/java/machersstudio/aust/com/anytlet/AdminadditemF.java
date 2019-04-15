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
 * Created by user on 4/11/2019.
 */

public class AdminadditemF extends Fragment {
    EditText E1,E2;
    Button b;
    DatabaseReference databaseReference;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.additem, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("Items");
        E1=(EditText) view.findViewById(R.id.Aitemname);
        E2=(EditText)  view.findViewById(R.id.Aitemcharge);

        b=(Button)  view.findViewById(R.id.itemsubmit);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                addItem();

            }
        });

        return view;
    }
    public void addItem(){
        final String item=E1.getText().toString().trim();
        final  String charge=E2.getText().toString().trim();
        if(!Constants.isNetworkAvailable(getContext())){
            Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
        else if(item.isEmpty() || charge.isEmpty() ){
            if(item.isEmpty()){
                E1.setError("required");

            }
            if(charge.isEmpty()){
                E2.setError("required");

            }

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

                         ItemsDb itemsDb=new ItemsDb(item,item,charge);
                        // String key=databaseReference.push().getKey();
                        databaseReference.child(item).setValue(itemsDb);




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
