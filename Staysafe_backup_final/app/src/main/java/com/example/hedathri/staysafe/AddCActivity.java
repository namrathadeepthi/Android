package com.example.hedathri.staysafe;

  import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
  import android.widget.Toast;


public class AddCActivity extends ActionBarActivity {

    private static Button submit;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_c);
        ListenForButton();

    }

    public void ListenForButton()
    {
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText name=(EditText)findViewById(R.id.name);
                        String c_name=name.getText().toString();
                        final EditText ph=(EditText)findViewById(R.id.ph);
                        String c_ph=ph.getText().toString();
                        db.addContact(new Contact(c_name,c_ph));
                        Toast.makeText(getApplicationContext(),"Contact submitted!",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_c, menu);
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
