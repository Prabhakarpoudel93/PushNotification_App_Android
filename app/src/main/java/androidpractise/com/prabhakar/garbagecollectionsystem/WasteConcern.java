package androidpractise.com.prabhakar.garbagecollectionsystem;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.util.HashMap;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WasteConcern extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Button login;
    EditText username,password;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    CheckBox rememberme;
    AQuery aQuery;
    String loginurl="http://192.168.0.14/wasteconcern/logincustomer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasteconcern);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        aQuery=new AQuery(this);
        rememberme=findViewById(R.id.rememberme1);
        sharedPreferences=getSharedPreferences("Userinfo",0);
        if(sharedPreferences.getBoolean("rememberme",false))
        {
            Intent intent=new Intent(WasteConcern.this,HomeActivityCust.class);
            startActivity(intent);
            finish();
        }
        login= findViewById(R.id.login);
        databaseHelper=new DatabaseHelper(this);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname=username.getText().toString();
                String cust_password=password.getText().toString();
                final HashMap<String,Object> params=new HashMap<>();
                params.put("username",uname);
                params.put("password",cust_password);
                Log.i("messageone","dataoreo"+params);
                aQuery.ajax(loginurl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                    @Override
                    public void callback(String url, JSONArray array, AjaxStatus status) {
                        super.callback(url, array, status);
                        Log.i("arrayvalue","datas"+array.length());
                        CustomerLogininfo customerLogininfo=new CustomerLogininfo();
                        for(int i=0; i<=array.length();i++)
                        {
                            try {
                                JSONObject object = array.getJSONObject(i);

                                customerLogininfo.customer_id= (String) object.get("customer_id");
                                customerLogininfo.name= (String) object.get("name");
                                customerLogininfo.email= (String) object.get("email");
                                customerLogininfo.phone= (String) object.get("phone");
                                customerLogininfo.areano= (String) object.get("areano");
                                customerLogininfo.username= (String) object.get("username");
                                customerLogininfo.password= (String) object.get("password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(array.length()>0)
                        {
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("username",uname);
                            editor.apply();
                            if(rememberme.isChecked())
                            {
                                SharedPreferences.Editor editor1=sharedPreferences.edit();
                                editor1.putBoolean("rememberme",true);
                                editor1.apply();
                            }

                            clearFields();
                            Log.i("WasteConcerndebug","uname"+uname);
                            Intent intent=new Intent(WasteConcern.this,HomeActivityCust.class);
                            intent.putExtra("custid",customerLogininfo.customer_id);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(WasteConcern.this,"Login failed",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                /*if(databaseHelper.isCustomerValid(uname,cust_password))
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username",uname);
                    editor.apply();
                    if(rememberme.isChecked())
                    {
                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                        editor1.putBoolean("rememberme",true);
                        editor1.apply();
                    }

                    clearFields();
                    Log.i("WasteConcerndebug","uname"+uname);
                    Intent intent=new Intent(WasteConcern.this,HomeActivityCust.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(WasteConcern.this,"Login failed",Toast.LENGTH_LONG).show();
                }*/

            }


        });


    }

    private void clearFields() {
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        username.setText("");
        password.setText("");
    }

    @Override
        public void onBackPressed () {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);

            } else {
                exitApplication();
                //super.onBackPressed();
            }
        }

    public void exitApplication()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit Application");
        builder.setMessage("Are you sure you want to Exit??");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.admin) {
                // Handle the camera action
                Intent intent = new Intent(WasteConcern.this, AdminLogin.class);
                startActivity(intent);

            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    }

