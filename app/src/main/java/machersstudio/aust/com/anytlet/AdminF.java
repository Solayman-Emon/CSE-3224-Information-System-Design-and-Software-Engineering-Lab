package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 4/11/2019.
 */

public class AdminF extends Fragment {
    public Button b1,b2,b3,b4;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.adminpanel, container, false);
        b1=(Button)  view.findViewById(R.id.additem);
        b2=(Button)  view.findViewById(R.id.showitem);
        b3=(Button)  view.findViewById(R.id.addpay);
        b4=(Button)  view.findViewById(R.id.showpay);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdminadditemF()).addToBackStack(null).commit();

            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdminitemF()).addToBackStack(null).commit();

            }
        });

        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdminaddpayF()).addToBackStack(null).commit();

            }
        });
        b4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.container_frame,new AdminpayF()).addToBackStack(null).commit();

            }
        });

        return view;
    }
}
