package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class View_Notification extends AppCompatActivity {
    LinearLayout container;
    AutoCompleteTextView autoCompleteTextView;
    AQuery aQuery;
    String fetchnotiurl= "http://192.168.0.14/wasteconcern/selectnotification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__notification);
        autoCompleteTextView=findViewById(R.id.autocomplete);
        container=findViewById(R.id.container1);
        aQuery=new AQuery(this);

//        //autoCompleteTextView.setAdapter(new UserListAutocompleteadapter(this,databaseHelper.getUserNameList()));
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               String item=parent.getItemAtPosition(position).toString();
//               Toast.makeText(View_Notification.this,"values"+item, Toast.LENGTH_LONG).show();
//                //Log.i("list","listval"+id1);
//
//            }
//        });
        fetchData();
    }

    @Override
    protected void onResume() {
        Log.i("saturday","love to code");
        fetchData();
        super.onResume();
    }

    public void fetchData()
    {
        container.removeAllViews();
        Log.i("saturday","love to code");
        aQuery.ajax(fetchnotiurl, JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("shivatandav","dataone"+array);
                ArrayList<Notificationinfo> list=new ArrayList<>();
                for(int i=0; i<=array.length();i++)
                {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        Notificationinfo notificationinfo=new Notificationinfo();
                        notificationinfo.notification_id= (String) object.get("notification_id");
                        notificationinfo.message= (String) object.get("message");
                        notificationinfo.areano= (String) object.get("areano");
                        notificationinfo.admin_id= (String) object.get("admin_id");
                        notificationinfo.date_time= (String) object.get("date_time");

                        list.add(notificationinfo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for ( final Notificationinfo info:list) {
                    View view = LayoutInflater.from(View_Notification.this).inflate(R.layout.notificationview_add,null);
                    TextView id=view.findViewById(R.id.notificationid),
                            message=view.findViewById(R.id.message),
                            datetime=view.findViewById(R.id.dateandtime),
                            areano=view.findViewById(R.id.notiareano);
                    id.setText(info.notification_id);
                    message.setText(info.message);
                    areano.setText(info.areano);
                    datetime.setText(info.date_time);
                   /* view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(View_Notification.this,DetailActivity_cust.class);
                            intent.putExtra("id",info.id);
                            Log.i("value of i","valuue i"+info.id);
                            startActivity(intent);
                        }
                    });*/

                    container.addView(view);

                }
            }
        });
    }
}
