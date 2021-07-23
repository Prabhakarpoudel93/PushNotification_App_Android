package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SelectArea extends AppCompatActivity {
    AQuery aQuery;
    Button submit;
    EditText areano,messages;
    String posturl="http://192.168.0.14/wasteconcern/pushnotification.php";
    String bulkemailurl="http://192.168.0.14/wasteconcern/emailbulk.php";
    String notificationurl="http://192.168.0.14/wasteconcern/notification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        areano=findViewById(R.id.areanumber);
        final String id= getIntent().getStringExtra("adminid");
        messages=findViewById(R.id.message);
        submit=findViewById(R.id.submit);
        final AQuery aQuery=new AQuery(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String areanumber=areano.getText().toString();
                String message=messages.getText().toString();
                if(!((areanumber.equals("1"))||(areanumber.equals("2"))||(areanumber.equals("3"))||(areanumber.equals("4"))))
                {
                    areano.setError("Please enter the valid area no");
                }
                else if(message.isEmpty())
                {
                    messages.setError("Enter some message to be send");
                }
                else {

                    final HashMap<String, Object>[] params = new HashMap[]{new HashMap<>()};
                    params[0].put("areano",areanumber);
                    params[0].put("message",message);
                    aQuery.ajax(posturl, params[0], JSONArray.class,new AjaxCallback<JSONArray>(){
                        @Override
                        public void callback(String url, JSONArray object, AjaxStatus status) {
                            super.callback(url, object, status);
                            Toast.makeText(SelectArea.this,"Message sent to customer of areano"+areanumber,Toast.LENGTH_LONG).show();
                            Log.i("messageone",url+"val"+ params[0]);
                        }
                    });
                    final HashMap<String,Object> params1=new HashMap<>();
                    params[0].put("areano",areanumber);
                    params[0].put("message",message);
                    aQuery.ajax(bulkemailurl, params[0], JSONArray.class,new AjaxCallback<JSONArray>(){
                        @Override
                        public void callback(String url, JSONArray object, AjaxStatus status) {
                            super.callback(url, object, status);
                            Toast.makeText(SelectArea.this,"Email sent to customer of areano"+areanumber,Toast.LENGTH_LONG).show();
                            Log.i("messageone",url+"val"+ params[0]);


                        }
                    });

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    Log.i("helloworld",currentDateandTime);
                    final HashMap<String,Object> params2=new HashMap<>();
                    params2.put("areano",areanumber);
                    params2.put("message",message);
                    params2.put("date",currentDateandTime);
                    params2.put("adminid",id);
                    aQuery.ajax(notificationurl, params2, JSONArray.class,new AjaxCallback<JSONArray>(){
                        @Override
                        public void callback(String url, JSONArray object, AjaxStatus status) {
                            super.callback(url, object, status);
//                            Toast.makeText(SelectArea.this,"Email sent to customer of areano"+areanumber,Toast.LENGTH_LONG).show();
//                            Log.i("messageone",url+"val"+params);
                        }
                    });

                }



            }
        });
    }
}
