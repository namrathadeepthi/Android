package com.example.hedathri.staysafe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class FirstActivity extends Activity implements View.OnClickListener {
    private static Button addContact,viewContacts,deleteContact,sendsms,speechtotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        addContact=(Button)findViewById(R.id.add);
        addContact.setOnClickListener(this);
        viewContacts=(Button)findViewById(R.id.view);
        viewContacts.setOnClickListener(this);
        deleteContact=(Button)findViewById(R.id.delete);
        deleteContact.setOnClickListener(this);
        sendsms=(Button)findViewById(R.id.smsid);
        sendsms.setOnClickListener(this);
        speechtotext=(Button)findViewById(R.id.speechtotext);
        speechtotext.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent i = new Intent("com.example.hedathri.staysafe.AddCActivity");
                startActivity(i);
                break;
            case R.id.view:
                Intent i1 = new Intent("com.example.hedathri.staysafe.EditCActivity");
                startActivity(i1);
                break;
            case R.id.delete:
                Intent i2 =new Intent("com.example.hedathri.staysafe.DeleteCActivity");
                startActivity(i2);
                break;
            case R.id.smsid:
                DatabaseHandler db = new DatabaseHandler(this);
                List<Contact> contacts = db.getAllContacts();
                for (Contact cn : contacts) {
                    String phone_no = cn._phone_number;
                    String smsbody = "I am in danger!Help!";
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_no, null, smsbody, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.speechtotext:

                Intent i3=new Intent("com.microsoft.AzureIntelligentServicesExample.MainActivity");
                startActivity(i3);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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
