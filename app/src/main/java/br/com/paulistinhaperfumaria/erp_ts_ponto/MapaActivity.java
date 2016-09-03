package br.com.paulistinhaperfumaria.erp_ts_ponto;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback{

//    private LatLng location = new LatLng(-21.6719891, -49.7525192);
    private LatLng location;
    private GoogleMap mapa,mFirstMapFragment;

//    public MapaActivity(){
//        location = new LatLng( Integer.parseInt(getIntent().getStringExtra("latitude")), Integer.parseInt(getIntent().getStringExtra("longitude")));
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);




       // mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa)).getMapAsync(OnMapReady(mapa));
        mapa.addMarker(new MarkerOptions().position(location).title("Localização"));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(location));
        mapa.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        if (getIntent().getStringExtra("op") == "1"  ){
//            location = (getIntent().getStringExtra("latitude") + "," + (getIntent().getStringExtra("longitude")));
            location = new LatLng( Integer.parseInt(getIntent().getStringExtra("latitude")), Integer.parseInt(getIntent().getStringExtra("longitude")));
            Toast.makeText(this, getIntent().getStringExtra("latitude") +" , " + getIntent().getStringExtra("longitude") ,Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Nao encontramos localizacao.\nVerifique se o GPS está ativado!!!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }

}
