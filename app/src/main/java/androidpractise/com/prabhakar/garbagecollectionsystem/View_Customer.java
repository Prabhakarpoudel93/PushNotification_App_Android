package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class View_Customer extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    LinearLayout container;
    AutoCompleteTextView autoCompleteTextView;
    AQuery aQuery;
    String fetchurl= "http://192.168.0.14/wasteconcern/selectcustomers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlayout);
        autoCompleteTextView=findViewById(R.id.autocomplete);
        databaseHelper=new DatabaseHelper(this);
        container=findViewById(R.id.container);
        aQuery=new AQuery(this);

        autoCompleteTextView.setAdapter(new UserListAutocompleteadapter(this,databaseHelper.getUserNameList()));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(View_Customer.this,"values"+item, Toast.LENGTH_LONG).show();
//                Log.i("list","listval"+id1);
                /*Intent intent=new Intent(View_Customer.this,DetailActivity_cust.class);
                intent.putExtra("name",item );
                startActivity(intent);*/
            }
        });


    }

    @Override
    protected void onResume() {

        fetchData();
        //displayData();
        super.onResume();
    }

    public  void displayData()
    {
        container.removeAllViews();
        ArrayList<Customerinfo> list= databaseHelper.getUserList();
        Log.i("listsize:", "listsize:" + list.size());
        for ( final Customerinfo info:list) {


            TextView textView=new TextView(this);
            textView.setText(info.id+"\t"+info.name+"\t"+info.areano+"\t"+info.username+"\t"+info.password);
            View view = LayoutInflater.from(this).inflate(R.layout.item_layout_new,null);
            TextView id=view.findViewById(R.id.custid),
                    name=view.findViewById(R.id.custname),
                    phone=view.findViewById(R.id.custphone),
                    //email=view.findViewById(R.id.custemail),
                    areano=view.findViewById(R.id.custareano);
                    /*username=view.findViewById(R.id.custusername),
                    password=view.findViewById(R.id.custpassword);*/
            id.setText(info.id);
            name.setText(info.name);
            phone.setText(info.phone);
            //email.setText(info.email);
            areano.setText(info.areano);
            /*username.setText(info.username);
            password.setText(info.password);*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(View_Customer.this,DetailActivity_cust.class);
                    intent.putExtra("id",info.id);
                    startActivity(intent);
                }
            });

            container.addView(view);
            
        }
    }
    public void fetchData()
    {
        container.removeAllViews();
        aQuery.ajax(fetchurl, JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("messageone","dataone"+array);
                ArrayList<Customerinfo> list=new ArrayList<>();
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
                        list.add(customerinfo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for ( final Customerinfo info:list) {
                    View view = LayoutInflater.from(View_Customer.this).inflate(R.layout.item_layout_new,null);
                    TextView id=view.findViewById(R.id.custid),
                            name=view.findViewById(R.id.custname),
                            phone=view.findViewById(R.id.custphone),
                            areano=view.findViewById(R.id.custareano);
                    id.setText(info.id);
                    name.setText(info.name);
                    phone.setText(info.phone);
                    areano.setText(info.areano);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(View_Customer.this,DetailActivity_cust.class);
                            intent.putExtra("id",info.id);
                            Log.i("value of i","valuue i"+info.id);
                            startActivity(intent);
                        }
                    });

                    container.addView(view);

                }
            }
        });
    }
}
