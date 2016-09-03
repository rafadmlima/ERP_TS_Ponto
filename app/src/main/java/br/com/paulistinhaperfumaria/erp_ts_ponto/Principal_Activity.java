package br.com.paulistinhaperfumaria.erp_ts_ponto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class Principal_Activity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    /*
    * AccountManager accManager = AccountManager.get(context);
    Account acc[] = accManager.getAccounts();
    int accCount = acc.length;
    AppConstants.accOnDevice = new Vector<String>();
    for(int i = 0; i < accCount; i++){
        //preenche o(s) edittext com as conta(s) configura(s)
    }
    Pegar o número do telefone

    TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
    String mPhoneNumber = tMgr.getLine1Number();

    Permissões obrigatórias:

    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    *
    * */
    boolean flag = true;
    Button btnRegistrar, btnMapa;
    TextView txtLatitude, txtLongitude;
    String numero = "";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location l;
    private LocationManager gps;
    private boolean isOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String num = tm.getDeviceId();
        numero = tm.getLine1Number();
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(registrarPonto(numero));
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(verMapa());


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        gps = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        isOn = gps.isProviderEnabled( LocationManager.GPS_PROVIDER);
        if (isOn){
            callConnection();
        }

    }

    private View.OnClickListener verMapa() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal_Activity.this, MapaActivity.class);
                intent.putExtra("op", "1");
                intent.putExtra("latitude",txtLatitude.getText());
                intent.putExtra("longitude", txtLongitude.getText());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener registrarPonto(final String num) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("numero do celular: ", numero);
                Toast.makeText(Principal_Activity.this, "ENTRADA\nFONE: " + num + "FONE_ID: " + num, Toast.LENGTH_LONG).show();
//
//                    Toast.makeText(Principal_Activity.this, "SAIDA\nFONE: " + num, Toast.LENGTH_LONG).show();
                if (isOn){
                    callConnection();
                }



//
            }
        };
    }

    @Override
    public void onResume(){
        super.onResume();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            startLocationUpdate();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        if (mGoogleApiClient != null ){
            stopLocationUpdate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_, menu);
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


    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }

    public void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdate() {
        initLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Principal_Activity.this);
    }
    private void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, Principal_Activity.this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle + ")");


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (l != null) {
            Log.i("LOG", "Latitude: " + l.getLatitude());
            Log.i("LOG", "Longitude: " + l.getLongitude());
            Toast.makeText(this, "Latitude: " + l.getLatitude() + "\nLongitude: " + l.getLongitude(), Toast.LENGTH_LONG).show();


        }


            startLocationUpdate();

        Toast.makeText(this, "Latitude: " + l.getLatitude() + "\nLongitude: " + l.getLongitude(), Toast.LENGTH_LONG).show();
    }
        @Override
        public void onConnectionSuspended(int i) {
            Log.i("LOG", "onConnectionSuspended(" + i +")");

        }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        txtLatitude.setText(Html.fromHtml("Latitude: " + location.getLatitude()));
        txtLongitude.setText(Html.fromHtml("Longitude: " + location.getLongitude()));
    }
}
