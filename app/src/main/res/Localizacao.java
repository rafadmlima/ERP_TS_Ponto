import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rafa on 24/08/2016.
 */
public class Localizacao implements GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;

    private  synchronized  void callConnection(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle +")");

        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(l != null){
            Log.i("LOG", "Latitude: " + );
            Log.i("LOG", "Longitude: ");
            Toast.makeText(this, "Latitude: " + l.getLatitude() +"\nLongitude: " + l.getLongitude() , Toast.LENGTH_LONG).show();


    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "onConnectionSuspended(" + i +")");

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
