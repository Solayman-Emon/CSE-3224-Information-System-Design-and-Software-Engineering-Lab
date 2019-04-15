package machersstudio.aust.com.anytlet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (getFragmentManager().getBackStackEntryCount() == 0) {

        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            if(!Constants.UserIdentity.equals("")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new AdF()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();
            }

        } else if (id == R.id.nav_gallery) {
            if(Constants.UserIdentity.equals("")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();
            }
            else{
                Toast.makeText(this,"logged as "+Constants.UserIdentity,Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_slideshow) {

            if(Constants.UserIdentity.equals("")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new RegF()).commit();
            }
            else{
                Toast.makeText(this,"logged as "+Constants.UserIdentity,Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.upostad) {
            if(!Constants.UserIdentity.equals("")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new PostAd()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();
            }


        }
        else if (id == R.id.ulogout) {
            Constants.UserIdentity="";
            while(getFragmentManager().getBackStackEntryCount()>0){
                getFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();

        }
        else if (id == R.id.uAdmin) {
            if(Constants.UserIdentity.equals("jBond")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new AdminF()).addToBackStack(null).commit();
            }
            else {
                Toast.makeText(this,"you r not admin"+Constants.UserIdentity,Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.umsg) {
            if(Constants.UserIdentity.equals("")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new LoginF()).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, new ChatF()).addToBackStack(null).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
