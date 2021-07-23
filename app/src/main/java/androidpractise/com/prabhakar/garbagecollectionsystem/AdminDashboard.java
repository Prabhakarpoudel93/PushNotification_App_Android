package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class AdminDashboard extends AppCompatActivity {
    Button addcustomer,viewcustomer,pushnotification, viewnoti;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String id= getIntent().getStringExtra("id");
        setContentView(R.layout.activity_admin_dashboard);
        addcustomer=findViewById(R.id.add_customer);
        viewcustomer=findViewById(R.id.view_customer);
        pushnotification=findViewById(R.id.pushnotification);
        viewnoti= findViewById(R.id.viewnotification);
        sharedPreferences=getSharedPreferences("Userinfo",0);
        addcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,CustomerSignup.class);
                intent.putExtra("adminid",id);
                Log.i("dashboardid:","dashboardid"+id);
                startActivity(intent);
            }
        });

        viewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,View_Customer.class);
                startActivity(intent);
            }
        });
        pushnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashboard.this,SelectArea.class);
                intent.putExtra("adminid",id);
                startActivity(intent);
            }
        });
        viewnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("saturday in admin","love to code");
                Intent intent=new Intent(AdminDashboard.this,View_Notification.class);
                intent.putExtra("adminid",id);
                startActivity(intent);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.logout:
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("remember_me",false);
                editor.apply();
                Intent intent=new Intent(AdminDashboard.this,WasteConcern.class);
                startActivity(intent);
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
