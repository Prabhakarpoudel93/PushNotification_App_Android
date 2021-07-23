package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.ContentValues;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CustomerSignup extends AppCompatActivity {
    EditText custname,custphone,custemail,custareano,custusername,custpassword;
    Button register;
    DatabaseHelper databaseHelper;
    AQuery aQuery;
    int id;
    String posturl="http://192.168.0.14/wasteconcern/insertcustomer.php";
    String geturl="http://192.168.0.14/wasteconcern/selectparticularcustomer.php";
    String updateurl="http://192.168.0.14/wasteconcern/updatecustomer.php";
    String emailurl="http://192.168.0.14/wasteconcern/emailforunameandpwd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);
        aQuery=new AQuery(this);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String validNumber = "^[+]?[0-9]{8,15}$";
        final String validuname="^[A-Za-z]+$";
        final int id=getIntent().getIntExtra("id",0);
        final String adminid=getIntent().getStringExtra("adminid");
        databaseHelper=new DatabaseHelper(this);
        custname=findViewById(R.id.cust_name);
        custphone=findViewById(R.id.cust_phone);
        custemail=findViewById(R.id.cust_email);
        custareano=findViewById(R.id.cust_Areano);
        custusername=findViewById(R.id.cust_username);
        custpassword=findViewById(R.id.cust_password);
        register=findViewById(R.id.signup);
        if(id!=0)
        {
            /*Customerinfo info=databaseHelper.getUserInfo(id+"");
            custname.setText(info.name);
            custphone.setText(info.phone);
            custemail.setText(info.email);
            custareano.setText(info.areano);
            custusername.setText(info.username);
            custpassword.setText(info.password);*/
            final HashMap<String,Object> params=new HashMap<>();
            params.put("id",id);
            Log.i("messageone","dataoreo"+params);
            aQuery.ajax(geturl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                @Override
                public void callback(String url, JSONArray array, AjaxStatus status) {
                    super.callback(url, array, status);
                    Log.i("objectval","objectval"+array);
                    for(int i=0; i<=array.length();i++)
                    {
                        try {
                            JSONObject object = array.getJSONObject(i);

                            Customerinfo customerinfo=new Customerinfo();
                            customerinfo.id= (String) object.get("customer_id");
                            customerinfo.name= (String) object.get("name");
                            customerinfo.phone= (String) object.get("phone");
                            customerinfo.areano= (String) object.get("areano");
                            customerinfo.email= (String) object.get("email");
                            customerinfo.username= (String) object.get("username");
                            customerinfo.password= (String) object.get("password");
                            Log.i("aashish","aashish"+customerinfo.id+customerinfo.username);
                            custname.setText(customerinfo.name);
                            custphone.setText(customerinfo.phone);
                            custemail.setText(customerinfo.email);
                            custareano.setText(customerinfo.areano);
                            custusername.setText(customerinfo.username);
                            custpassword.setText(customerinfo.password);
                            custusername.setEnabled(false);
                            custpassword.setEnabled(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            register.setText("Update");
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=custname.getText().toString().trim();
                String phone=custphone.getText().toString().trim();
                String areano=custareano.getText().toString().trim();
                String email=custemail.getText().toString().trim();
                String username=custusername.getText().toString().trim();
                String password=custpassword.getText().toString().trim();
                Toast.makeText(CustomerSignup.this,"name"+name,Toast.LENGTH_LONG).show();

                ContentValues contentValues=new ContentValues();
                contentValues.put("name",name);
                contentValues.put("phone",phone);
                contentValues.put("email",email);
                contentValues.put("areano",areano);
                contentValues.put("username",username);
                contentValues.put("password",password);

                    if(name.isEmpty() || phone.isEmpty() || areano.isEmpty() || username.isEmpty() || password.isEmpty())
                    {
                        Toast.makeText(CustomerSignup.this,"The field is empty",Toast.LENGTH_LONG).show();
                    }
                    else if(!(name.matches(validuname)))
                    {
                        custname.setError("please enter the valid name");
                    }
                    else if(!(phone.matches(validNumber)))
                    {
                        custphone.setError("Please enter the valid phone number");
                    }
                    else if (!(phone.length()==10))
                    {
                        custphone.setError("Please enter the valid phone number");
                    }
                    else if(!(email.matches(emailPattern)))
                    {
                        custemail.setError("Please enter the valid email address");
                    }
                    else if(!((areano.equals("1"))||(areano.equals("2"))||(areano.equals("3"))||(areano.equals("4"))))
                    {
                        custareano.setError("Please enter the valid area no");
                    }
                    else {
                        if (id == 0) {
                            databaseHelper.insertCustomer(contentValues);
                            //Toast.makeText(CustomerSignup.this, "Recordsaved" + "\nname=\n" + name, Toast.LENGTH_LONG).show();
                            final HashMap<String,Object> params=new HashMap<>();
                            params.put("name",name);
                            params.put("email",email);
                            params.put("phone",phone);
                            params.put("areano",areano);
                            params.put("username",username);
                            params.put("password",password);
                            params.put("admin_id",adminid);
                            aQuery.ajax(posturl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                                @Override
                                public void callback(String url, JSONArray object, AjaxStatus status) {
                                    super.callback(url, object, status);
                                    Toast.makeText(CustomerSignup.this,"User inserted to server",Toast.LENGTH_LONG).show();
                                    Log.i("messageone",url+"val"+params);
                                }
                            });
                            final HashMap<String,Object> params1=new HashMap<>();

                            params.put("mailto",email);
                            params.put("mailmsg","Dear, "+name+" your Username and password for Wasteconcern app is:\n\n"+"username =\t\t"+username+"\n\nand is Password=\t\t"+password+
                                    "\n\n Note:change this username and password soon.Thankyou!!");
                            aQuery.ajax(emailurl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                                @Override
                                public void callback(String url, JSONArray object, AjaxStatus status) {
                                    super.callback(url, object, status);

                                    Toast.makeText(CustomerSignup.this,"User updated to server",Toast.LENGTH_LONG).show();

                                }
                            });


                            finish();

                        } else {
//                            databaseHelper.updateCustomer(id + "", contentValues);
//                            Toast.makeText(CustomerSignup.this, "Recordsaved" + "\nname=\n" + name, Toast.LENGTH_LONG).show();
                            final HashMap<String,Object> params=new HashMap<>();
                            params.put("name",name);
                            params.put("email",email);
                            params.put("phone",phone);
                            params.put("areano",areano);
                            params.put("username",username);
                            params.put("password",password);
                            params.put("id",id);
                            Log.i("fz250","fz250"+params);
                            aQuery.ajax(updateurl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                                @Override
                                public void callback(String url, JSONArray object, AjaxStatus status) {
                                    super.callback(url, object, status);

                                    Toast.makeText(CustomerSignup.this,"User updated to server",Toast.LENGTH_LONG).show();

                                }
                            });


                            finish();
                        }
                    }


            }
        });

    }

}
