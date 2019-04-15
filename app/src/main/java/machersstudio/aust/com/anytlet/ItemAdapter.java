package machersstudio.aust.com.anytlet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 4/12/2019.
 */

public class ItemAdapter extends ArrayAdapter<PostadDb> {
    private Activity context;
    private List<PostadDb> ads;

    public ItemAdapter( Activity context, List<PostadDb> ads) {
        super(context,R.layout.item_layout, ads);
        this.context = context;
        this.ads = ads;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_layout,null,true);
        PostadDb postadDb=ads.get(position);
        ImageView imageView=(ImageView)view.findViewById(R.id.four);
        TextView t1=(TextView)view.findViewById(R.id.one);
        TextView t2=(TextView)view.findViewById(R.id.two);
        TextView t3=(TextView)view.findViewById(R.id.three);
        Glide.with(getContext()).load(postadDb.img1).placeholder(R.mipmap.ic_launcher).fitCenter().into(imageView);
       t1.setText("City :"+postadDb.city);
        t2.setText("Area :"+postadDb.area);
        t3.setText("Rent :"+postadDb.rent);
        return view;
    }
}
