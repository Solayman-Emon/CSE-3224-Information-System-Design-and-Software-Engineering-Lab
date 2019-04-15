package machersstudio.aust.com.anytlet;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 4/11/2019.
 */

public class PostAd extends Fragment {
    EditText E1,E2,E3,E4,E5,E6,E7,E8;
   public Spinner spinner,spinner1;
   Button b1,b2;
   Uri uri;
  private static  final int imgreq=1;

    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    StorageReference storageReference;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.postad, container, false);
        spinner=(Spinner)view.findViewById(R.id.spinner1);
        spinner1=(Spinner)view.findViewById(R.id.spinner2);
       b1=(Button)view.findViewById(R.id.pickimage);
        b2=(Button)view.findViewById(R.id.post);

        E1=(EditText) view.findViewById(R.id.city);
        E2=(EditText)  view.findViewById(R.id.area);
        E3=(EditText)  view.findViewById(R.id.adaddress);
        E4=(EditText)  view.findViewById(R.id.roomno);
        E5=(EditText)  view.findViewById(R.id.kitchenno);
        E6=(EditText)  view.findViewById(R.id.bathno);
        E7=(EditText)  view.findViewById(R.id.phone);
        E8=(EditText)  view.findViewById(R.id.rent);


        databaseReference= FirebaseDatabase.getInstance().getReference("PaymentMethod");
        databaseReference1= FirebaseDatabase.getInstance().getReference("Items");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Ads");
        storageReference= FirebaseStorage.getInstance().getReference("image");

      final  List<String> lst = new ArrayList<>();
        final  List<String> lst1 = new ArrayList<>();
      databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

               try{
                   for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                       PaymentMethodDb b = postSnapshot.getValue(PaymentMethodDb.class);

                       lst.add(b.Name);

                   }
               }
               catch (Exception e){

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      lst.add("none");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               try {
                   for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                       ItemsDb b = postSnapshot.getValue(ItemsDb.class);

                       lst1.add(b.Name);

                   }
               }catch (Exception e){

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

lst1.add("none");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, lst);
       dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, lst1);
        dataAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                img(imgreq);

            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                     save();


            }
        });

        return view;
    }
    public void img(int a){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,a);

    }
    public void save()
    {
       final  String city=E1.getText().toString().trim();
        final  String area=E2.getText().toString().trim();
        final String address=E3.getText().toString().trim();
        final String roomno=E4.getText().toString().trim();
        final  String kitchenno=E5.getText().toString().trim();
        final  String bathno=E6.getText().toString().trim();
        final  String phone=E7.getText().toString().trim();
        final  String rent=E8.getText().toString().trim();
        final String pay=spinner.getSelectedItem().toString();
         final String item=spinner1.getSelectedItem().toString();

        if(!Constants.isNetworkAvailable(getContext())){
            Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
        else if(city.isEmpty() || area.isEmpty()||address.isEmpty()||roomno.isEmpty()||kitchenno.isEmpty() ||bathno.isEmpty() || phone.isEmpty()||rent.isEmpty()||pay.equals("none")||item.equals("none")  ){
            Toast.makeText(getContext(),"Any fields cant be empty",Toast.LENGTH_SHORT).show();

        }
        else {

            StorageReference ref=storageReference.child(System.currentTimeMillis()+"."+getEx(uri));


            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content



                            String s=Constants.UserIdentity+"o"+System.currentTimeMillis();
                            PostadDb postadDb=new PostadDb(s,Constants.UserIdentity+"o",item,pay,taskSnapshot.getTask().getResult().getDownloadUrl().toString(),city,area,address,roomno,kitchenno,bathno,phone,rent);
                            databaseReference2.child(s).setValue(postadDb);
                            Toast.makeText(getContext(),"posted",Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                    });


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imgreq && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri=data.getData();
            Toast.makeText(getContext(),"uploaded",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(),"not uploaded",Toast.LENGTH_SHORT).show();
        }
    }
    public String getEx(Uri uri){
        ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
