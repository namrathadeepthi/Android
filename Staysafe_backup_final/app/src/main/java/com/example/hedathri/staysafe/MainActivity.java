package com.example.hedathri.staysafe;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements LocationListener{

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    protected LocationManager locationManager;
    protected Context context;
    protected boolean gps_enabled, network_enabled;
    TextView txtLat;
    public double latitude1;
    public double longitude1;
    private static Button start,speechtotext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        final List<Contact> contacts = db.getAllContacts();
        // Inserting Contacts
        /*Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
*/
        //db.addContact(new Contact("AND","12345"));


        // Reading all contacts
        txtLat = (TextView) findViewById(R.id.textview1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
// getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
            public void handleShakeEvent(int count)
            {

               for (Contact cn : contacts) {
                    //String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " +
                    //      cn.getPhoneNumber();
                    // Writing Contacts to log
                    // Log.d("Name: ", log);
                    String phone_no = cn._phone_number;
                    String smsbody = "i am in Danger!Help!Latitude: "+latitude1+" Longitude: "+longitude1;

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_no, null, smsbody, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
                for(Contact cn: contacts)
                {
                    String phone_no1 = cn._phone_number;
                    Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone_no1));
                    try{
                        startActivity(in);
                    }

                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                   /* try{

                       Thread.sleep(10000);
                    }catch(InterruptedException ex){

                    }*/
                }
               /* String phone_no1 = "9964633034";
                Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:9964633034"));
                try{
                    startActivity(in);
                }

                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        Log.d("Reading: ", "Reading all contacts..");
       // List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " +
                    cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        ListenForButton();


    }
    public void ListenForButton()
    {
        start=(Button)findViewById(R.id.start);
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent("com.example.hedathri.staysafe.FirstActivity");

                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.textview1);
        latitude1=location.getLatitude();
        longitude1=location.getLongitude();
        txtLat.setText("Latitude:" + latitude1 + ", Longitude:" + longitude1);

       /* Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude1, longitude1, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address= addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city= addresses.get(0).getLocality();
            String log1=address+" "+city;
            Log.d("Address: ", log1);
            // state = addresses.get(0).getAdminArea();
            // country = addresses.get(0).getCountryName();
            //postalCode = addresses.get(0).getPostalCode();
            //knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        }
        catch (IOException e) {
            e.printStackTrace();


        }*/

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }


}
