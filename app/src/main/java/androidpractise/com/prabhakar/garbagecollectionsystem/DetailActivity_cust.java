package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class DetailActivity_cust extends AppCompatActivity {
    String id;
    DatabaseHelper databaseHelper;
    TextView name,phone,email,areano,username,password;
    AQuery aQuery;
    String url="http://192.168.0.14/wasteconcern/selectparticularcustomer.php";
    String deleteurl="http://192.168.0.14/wasteconcern/deletecustomer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cust);
        id=getIntent().getStringExtra("id");
        databaseHelper=new DatabaseHelper(this);
        aQuery=new AQuery(this);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        areano=findViewById(R.id.areano);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity_cust.this,CustomerSignup.class);
                intent.putExtra("id",Integer.parseInt(id));
                startActivity(intent);
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showConfirmDialog();
            }
        });


    }
    public void showConfirmDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Customer");
        builder.setMessage("Are you sure you want to delete??");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //databaseHelper.deleteCustomer(id+"");
                final HashMap<String,Object> params=new HashMap<>();
                params.put("id",id);
                aQuery.ajax(deleteurl,params, JSONArray.class,new AjaxCallback<JSONArray>(){
                    @Override
                    public void callback(String url, JSONArray object, AjaxStatus status) {
                        super.callback(url, object, status);
                        Toast.makeText(DetailActivity_cust.this,"User Deleted",Toast.LENGTH_LONG).show();
                        //Log.i("messageone",url+"val"+params);
                    }
                });
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


    public void showDetail()
    {
        /*Customerinfo info=databaseHelper.getUserInfo(id);
        name.setText(info.name);
        phone.setText(info.phone);
        email.setText(info.email);
        areano.setText(info.areano);
        username.setText(info.username);
        password.setText(info.password);*/


    }
    public void fetchData()
    {
        final HashMap<String,Object> params=new HashMap<>();
        params.put("id",id);
        Log.i("messageone","dataoreo"+params);
        aQuery.ajax(url,params, JSONArray.class,new AjaxCallback<JSONArray>(){
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


                        String ps2 = (String) object.get("password");
                        byte[] decrypt= Base64.decode(ps2, Base64.DEFAULT);
                        String text = new String(decrypt, "UTF-8");


                        customerinfo.password= text;
                        Log.i("aashish","aashish"+customerinfo.id+customerinfo.username);
                        name.setText(customerinfo.name);
                        phone.setText(customerinfo.phone);
                        email.setText(customerinfo.email);
                        areano.setText(customerinfo.areano);
                        username.setText(customerinfo.username);
                        password.setText(customerinfo.password);
                        username.setEnabled(false);
                        password.setEnabled(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        /*aQuery.ajax(url, JSONArray.class, new AjaxCallback<JSONArray>(){

            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("messagetwo","dataoneoreo"+url+array);
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Customerinfo info=new Customerinfo();
                name.setText(info.name);
                phone.setText(info.phone);
                email.setText(info.email);
                areano.setText(info.areano);
                username.setText(info.username);
                password.setText(info.password);


            }
        });*/
    }

    @Override
    protected void onResume() {

        fetchData();
        super.onResume();
    }
}
