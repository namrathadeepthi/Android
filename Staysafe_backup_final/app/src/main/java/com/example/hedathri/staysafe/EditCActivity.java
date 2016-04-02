package com.example.hedathri.staysafe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EditCActivity extends ActionBarActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    private static Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_c);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();
        List<String> contactNames= new ArrayList<String>();
        for (Contact cn : contacts) {
            //String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " +
            //      cn.getPhoneNumber();
            // Writing Contacts to log
            // Log.d("Name: ", log);
            contactNames.add("Name: "+cn.getName()+"\n"+"Number: "+cn.getPhoneNumber());

        }
        /*
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_edit_c,contactNames);

        ListView listView = (ListView) findViewById(R.id.listofcontact);
        listView.setAdapter(adapter);*/
        mainListView = (ListView) findViewById( R.id.mainListView );

        // Create and populate a List of planet names.


        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,contactNames);

        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_c, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
