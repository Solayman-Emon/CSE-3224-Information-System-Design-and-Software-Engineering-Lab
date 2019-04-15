package machersstudio.aust.com.anytlet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by user on 4/13/2019.
 */

public class ChatF extends Fragment {
    ListView listView;
    EditText e1;
    Button b1;
    List<String> lst=new ArrayList<>();
    List<CommunicateDb> list=new ArrayList<>();
    ArrayAdapter<String> ad;
    DatabaseReference databaseReference,databaseReference1;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.msglist, container, false);
        listView=(ListView)view.findViewById(R.id.msglist);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        databaseReference1= FirebaseDatabase.getInstance().getReference("Communicate");
        e1=(EditText)view.findViewById(R.id.searchEditText);
        b1=(Button)view.findViewById(R.id.srchbtn);
        ad=new ArrayAdapter<String>(getContext(),R.layout.test,lst);
        listView.setAdapter(ad);
        list.clear();
        lst.clear();
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                srch();


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= (String) parent.getItemAtPosition(position);
                String[] s1=s.split("\n");
                s1[0]=s1[0].replaceAll(":::","");
                s1[0]=s1[0].replaceAll("\\s+","");

                getFragmentManager().beginTransaction().replace(R.id.container_frame,ChitChatF.newInstance(s1[0])).addToBackStack(null).commit();

            }
        });



        return view;

    }

    public void srch()
    {
      final  String s=e1.getText().toString().trim();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lst.clear();
                if (!s.isEmpty() && !s.equals(Constants.UserIdentity) && snapshot.hasChild(s)) {

                        UserDb u = snapshot.child(s).getValue(UserDb.class);
                        lst.add(u.UserId);
                    listView.setAdapter(ad);


                    // it exists!
                }else{
                    // does not exist
                    listView.setAdapter(ad);

                    // String key=databaseReference.push().getKey();

                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }
    public void srch1(){
        String s=Constants.UserIdentity;
        s=s.replaceAll("\\s+","");
        Query q=databaseReference1.orderByChild("reciever").equalTo(s).limitToFirst(10);


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                    list.clear();
                    lst.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    CommunicateDb communicateDb=post.getValue(CommunicateDb.class);

                    list.add(communicateDb);

                }

                Collections.sort(list, new Comparator<CommunicateDb>() {
                    @Override
                    public int compare(CommunicateDb communicateDb, CommunicateDb t1) {
                        return communicateDb.timee.compareTo(t1.timee);
                    }
                });
                for(int i=list.size()-1;i>=0;i--){
                    lst.add(list.get(i).sender+"::: \n"+list.get(i).msg);
                }

                listView.setAdapter(ad);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {

        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                srch1();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        super.onStart();
    }


}
