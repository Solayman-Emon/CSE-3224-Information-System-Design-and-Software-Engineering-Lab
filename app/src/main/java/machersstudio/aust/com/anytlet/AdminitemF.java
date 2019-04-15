package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2019.
 */

public class AdminitemF extends Fragment {
    ListView listView;
    DatabaseReference databaseReference;
    List<String> lst=new ArrayList<>();
    ArrayAdapter<String> ad;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminitem, container, false);
          listView=(ListView)view.findViewById(R.id.adminitem);
        databaseReference= FirebaseDatabase.getInstance().getReference("Items");

        ad=new ArrayAdapter<String>(getContext(),R.layout.test,lst);
        listView.setAdapter(ad);

        return view;
    }
    public void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    ItemsDb itemsDb=post.getValue(ItemsDb.class);
                    lst.add("Item: "+itemsDb.Name+"||"+"charge :"+itemsDb.charge);
                }
                listView.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
