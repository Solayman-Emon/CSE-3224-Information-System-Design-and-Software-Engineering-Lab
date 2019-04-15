package machersstudio.aust.com.anytlet;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by user on 4/13/2019.
 */

public class ChitChatF extends Fragment {

    EditText e1;
    Button b1;
    ListView listView;
    ArrayAdapter<String> ad;
    List<String> lst=new ArrayList<>();
    List<CommunicateDb> list=new ArrayList<>();
DatabaseReference databaseReference;
    private static final String ARG="arg";
    String toid;
    public static ChitChatF  newInstance(String i1d){
        ChitChatF a=new ChitChatF();
        Bundle args=new Bundle();
        args.putString(ARG,i1d);
        a.setArguments(args);
        return a;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        if(getArguments()!=null){
            toid=getArguments().getString(ARG);
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Communicate");
        listView=(ListView)view.findViewById(R.id.messagesContainer);


        ad=new ArrayAdapter<String>(getContext(),R.layout.test,lst);


        e1=(EditText)view.findViewById(R.id.messageEdit);
        b1=(Button) view.findViewById(R.id.chatSendButton);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                send();
                chatchng();

                e1.setText("");


            }
        });






        return view;
    }
    public void send(){
        String msg=e1.getText().toString();
        if(msg.isEmpty()){
            Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
        }
        else {
            String s1= String.valueOf(System.currentTimeMillis());
            String s2=Constants.UserIdentity+toid;
            s2=s2.trim();
            String s=s2+s1;
            s=s.trim();

           CommunicateDb communicateDb=new CommunicateDb(s,s2,Constants.UserIdentity.trim(),toid.trim(),msg,s1);
           databaseReference.child(s).setValue(communicateDb);
        }
    }

    @Override
    public void onStart() {


        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                chatchng();
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
    public void chatchng(){
        String s=Constants.UserIdentity+toid;
        s=s.replaceAll("\\s+","");
        Query q=databaseReference.orderByChild("chatid1").equalTo(s);

       list.clear();
       lst.clear();


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot post:dataSnapshot.getChildren()){
                    CommunicateDb communicateDb=post.getValue(CommunicateDb.class);

                    list.add(communicateDb);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final List<CommunicateDb> list1=new ArrayList<>();
        String s2=toid+Constants.UserIdentity;
        s2=s2.replaceAll("\\s+","");

        q=databaseReference.orderByChild("chatid1").equalTo(s2);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                list1.clear();
                lst.clear();
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    CommunicateDb communicateDb=post.getValue(CommunicateDb.class);

                    list1.add(communicateDb);

                }

                int n=list.size();
                int n1=list1.size();
              list.addAll(list1);
             //   Toast.makeText(getContext(),list.size(),Toast.LENGTH_SHORT).show();
                Collections.sort(list, new Comparator<CommunicateDb>() {
                    @Override
                    public int compare(CommunicateDb communicateDb, CommunicateDb t1) {
                        return communicateDb.timee.compareTo(t1.timee);
                    }
                });
                for(int i=0;i<list.size();i++){
                    if(list.get(i).sender.equals(Constants.UserIdentity)){
                        lst.add("You"+"::: \n"+list.get(i).msg);
                    }
                    else{
                        lst.add(list.get(i).sender+"::: \n"+list.get(i).msg);
                    }

                }

               listView.setAdapter(ad);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
