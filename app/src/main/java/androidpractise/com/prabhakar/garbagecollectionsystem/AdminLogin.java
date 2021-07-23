package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminLogin extends AppCompatActivity {
    Button admin_login;
    EditText username,password;
    CheckBox rememberme;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    AQuery aQuery;
    String loginurl="http://192.168.0.14/wasteconcern/adminlogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        username=findViewById(R.id.admin_uname);;
        password=findViewById(R.id.admin_password);
        aQuery=new AQuery(this);
        admin_login=findViewById(R.id.admin_login);
        databaseHelper= new DatabaseHelper(this);
        rememberme=findViewById(R.id.rememberme);
        sharedPreferences=getSharedPreferences("Userinfo",0);
        if(sharedPreferences.getBoolean("remember_me",false))
        {
            Intent intent=new Intent(AdminLogin.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String admin_username=username.getText().toString();
                String admin_password=password.getText().toString();
                final HashMap<String,Object> params=new HashMap<>();
                params.put("username",admin_username);
                params.put("password",admin_password);
                Log.i("messageone","dataoreo"+params);
                aQuery.ajax(loginurl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                    @Override
                    public void callback(String url, JSONArray array, AjaxStatus status) {
                        super.callback(url, array, status);
                        Log.i("arrayvalue","datas"+array.length());
                        AdminLoginInfo adminLoginInfo=new AdminLoginInfo();
                        for(int i=0; i<=array.length();i++)
                        {
                            try {
                                JSONObject object = array.getJSONObject(i);

                                adminLoginInfo.admin_id= (String) object.get("admin_id");
                                adminLoginInfo.name= (String) object.get("name");
                                adminLoginInfo.email= (String) object.get("email");
                                adminLoginInfo.username= (String) object.get("username");
                                adminLoginInfo.password= (String) object.get("password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(array.length()>0)
                        {
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("username",admin_username);
                            editor.apply();
                            if(rememberme.isChecked())
                            {
                                SharedPreferences.Editor editor1=sharedPreferences.edit();
                                editor1.putBoolean("remember_me",true);
                                editor1.apply();
                            }

                            Log.i("WasteConcerndebug","uname"+admin_username);
                            Intent intent=new Intent(AdminLogin.this,AdminDashboard.class);
                            intent.putExtra("id",adminLoginInfo.admin_id);
                            Log.i("adminid=","admin_id="+adminLoginInfo.admin_id);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(AdminLogin.this,"Login failed",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                /*if(databaseHelper.isLoginValid(admin_username,admin_password)){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username",admin_username);
                    editor.apply();
                    if(rememberme.isChecked())
                    {
                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                        editor1.putBoolean("remember_me",true);
                        editor1.apply();
                    }
                    Intent intent=new Intent(AdminLogin.this,AdminDashboard.class);
                    startActivity(intent);
                    Toast.makeText(AdminLogin.this,"username"+admin_username+"password"+admin_password,Toast.LENGTH_LONG).show();
                    finish();


                }
                else
                {
                    Toast.makeText(AdminLogin.this,"Login failed",Toast.LENGTH_LONG).show();
                }*/



            }
        });
    }
    }
