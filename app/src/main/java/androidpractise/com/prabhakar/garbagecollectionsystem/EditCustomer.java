package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class EditCustomer extends AppCompatActivity {
    AQuery aQuery;
    EditText custusername,custpassword,custnewpassword;
    String holdval;
    Button edit;
    String geturl="http://192.168.0.14/wasteconcern/editcustomerinfo.php";
    String posturl="http://192.168.0.14/wasteconcern/updatecustomerlogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_customer);
        aQuery=new AQuery(this);
        custusername=findViewById(R.id.customerusername1);
        custpassword=findViewById(R.id.customerpassword1);
        custnewpassword=findViewById(R.id.customerpassword2);
        edit=findViewById(R.id.edit);
        fetchData();
        super.onCreate(savedInstanceState);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String newusername=custusername.getText().toString();
                    String newuserpassword=custnewpassword.getText().toString();
                    if(newusername.isEmpty())
                    {
                        Toast.makeText(EditCustomer.this,"The Username field is empty",Toast.LENGTH_LONG).show();
                    }
                    else if(newuserpassword.isEmpty())
                    {
                        Toast.makeText(EditCustomer.this,"The Password field is empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String somevalue=custpassword.getText().toString();
                       // if(holdval.equals(somevalue))
                       /// {
                        String cust_id = getIntent().getStringExtra("custid");
                        final HashMap<String, Object> params = new HashMap<>();
                        params.put("username", newusername);
                        params.put("password", newuserpassword);
                        params.put("oldpassworddatabase", holdval);
                        params.put("oldpasswordandroid", somevalue);
                        params.put("customer_id", cust_id);
                        Log.i("fz250", "fz250" + params);
                        aQuery.ajax(posturl, params, JSONArray.class, new AjaxCallback<JSONArray>() {
                            @Override
                            public void callback(String url, JSONArray object, AjaxStatus status) {
                                super.callback(url, object, status);

                                Toast.makeText(EditCustomer.this, "Your username and password is updated sucessfully.", Toast.LENGTH_LONG).show();
                                /*Intent intent=new Intent(EditCustomer.this,WasteConcern.class);
                                startActivity(intent);*/
                            }
                        });


                        Toast.makeText(EditCustomer.this, "Your username and password is updated sucessfully.", Toast.LENGTH_LONG).show();
                  //  }
                        finish();


                }



            }
        });
        //Toast.makeText(EditCustomer.this,"Your account has been updated",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        fetchData();

        super.onResume();
    }

    public void fetchData()
    {
        String cust_id=getIntent().getStringExtra("custid");
        final HashMap<String,Object> params=new HashMap<>();
        params.put("customer_id",cust_id);
        //Log.i("messageone","dataoreo"+params);
        aQuery.ajax(geturl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("objectval","objectval"+array);

                for(int i=0; i<=array.length();i++)
                {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        Editcustomerinfo editcustomerinfo=new Editcustomerinfo();
                        editcustomerinfo.username= (String) object.get("username");
                        editcustomerinfo.password= (String) object.get("password");
                        Log.i("aashish","aashish"+editcustomerinfo.username+editcustomerinfo.password);

                        custusername.setText(editcustomerinfo.username);
                        holdval = editcustomerinfo.password;
                        Log.i("holdval ","holdval"+holdval);


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

}
