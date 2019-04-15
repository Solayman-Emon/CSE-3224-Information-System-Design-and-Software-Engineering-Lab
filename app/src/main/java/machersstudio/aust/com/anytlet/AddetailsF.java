package machersstudio.aust.com.anytlet;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 4/12/2019.
 */

public class AddetailsF extends Fragment {
    TextView E1,E2,E3,E4,E5,E6,E7,E8,E9,E10;
    Button b1;
    ImageView imageView;
    DatabaseReference databaseReference;
   FirebaseStorage fp;
   PostadDb postadDb;
    private static final String ARG="arg";
    String id;
    public static AddetailsF newInstance(String id){
        AddetailsF a=new AddetailsF();
        Bundle args=new Bundle();
       args.putString(ARG,id);
       a.setArguments(args);
       return a;
    }



// Put any other arguments


    String txt;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addetails, container, false);
        E1=(TextView)view.findViewById(R.id.seepay);
        E2=(TextView)view.findViewById(R.id.seeitem);
        E3=(TextView)view.findViewById(R.id.seecity);
        E4=(TextView)view.findViewById(R.id.seearea);
        E5=(TextView)view.findViewById(R.id.seeaddress);
        E6=(TextView)view.findViewById(R.id.seeroom);
        E7=(TextView)view.findViewById(R.id.seekitchen);
        E8=(TextView)view.findViewById(R.id.seebath);
        E9=(TextView)view.findViewById(R.id.seephn);
        E10=(TextView)view.findViewById(R.id.seerent);
        b1=(Button)view.findViewById(R.id.seedel);
        imageView=(ImageView) view.findViewById(R.id.seeimage);
        databaseReference= FirebaseDatabase.getInstance().getReference("Ads");
       fp=FirebaseStorage.getInstance();
       if(getArguments()!=null){
           id=getArguments().getString(ARG);
       }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(id)) {




                         postadDb =  snapshot.child(id).getValue(PostadDb.class);
                         if(!postadDb.ownerid.equals(Constants.UserIdentity+"o")){
                             b1.setVisibility(View.GONE);
                         }
                         else{
                             b1.setVisibility(View.VISIBLE);
                         }
                         String s=postadDb.ownerid;
                    s = s.substring(0, s.length() - 1);
                        E1.setText("Owner :"+s);
                    E2.setText("item :"+postadDb.Item);
                    E3.setText("city :"+postadDb.city);
                    E4.setText("area :"+postadDb.area);
                    E5.setText("addition address :"+postadDb.additionaladdress);
                    E6.setText("room no :"+postadDb.roomno);
                    E7.setText("kitchen no :"+postadDb.kitchenno);
                    E8.setText("bath no :"+postadDb.bathno);
                    E9.setText("phone :"+postadDb.phone);
                    E10.setText("rent :"+postadDb.rent);

                    Glide.with(getContext()).load(postadDb.img1).placeholder(R.mipmap.ic_launcher).into(imageView);

                    // it exists!
                }else{
                    // does not exist
                    Toast.makeText(getContext(),"not exists",Toast.LENGTH_SHORT).show();

                    // String key=databaseReference.push().getKey();

                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                StorageReference storageReference=fp.getReferenceFromUrl(postadDb.img1);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.child(id).removeValue();
                        Toast.makeText(getContext(),"deleted",Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();
                    }
                });

            }
        });

        return view;
    }
}
