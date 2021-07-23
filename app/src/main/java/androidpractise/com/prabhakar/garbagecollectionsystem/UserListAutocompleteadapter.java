package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAutocompleteadapter extends ArrayAdapter<String> {
    Context context;
    public UserListAutocompleteadapter(Context context,ArrayList<String> list) {
        super(context,0,list);
        this.context=context;
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        TextView username= new TextView(context);
        final String info= getItem(position);
        username.setText(info);
        username.setPadding(15,15,15,15);
        return username;
    }
}
