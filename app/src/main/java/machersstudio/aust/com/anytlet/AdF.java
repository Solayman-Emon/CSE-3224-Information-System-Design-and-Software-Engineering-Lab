package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by user on 4/8/2019.
 */

public class AdF extends Fragment {
    ListView listView;
    DatabaseReference databaseReference;
    List<PostadDb> ads;
    ItemAdapter itemAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad, container, false);
        listView=(ListView)view.findViewById(R.id.adlist);
        databaseReference= FirebaseDatabase.getInstance().getReference("Ads");
        ads=new ArrayList<>();
        itemAdapter=new ItemAdapter(getActivity(),ads);
        while(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostadDb postadDb= (PostadDb) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),postadDb.area,Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.container_frame,AddetailsF.newInstance(postadDb.Adid)).addToBackStack(null).commit();

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                ads.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    PostadDb postadDb=post.getValue(PostadDb.class);
                    ads.add(postadDb);
                }
                listView.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
