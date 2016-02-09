package xyz.stepsecret.busshow;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import xyz.stepsecret.busshow.API.Get_EV;
import xyz.stepsecret.busshow.API.Get_Station;
import xyz.stepsecret.busshow.Cumulative.Cumulative;
import xyz.stepsecret.busshow.Distance.Distance;
import xyz.stepsecret.busshow.Model.Get_EV_Model;
import xyz.stepsecret.busshow.Model.Get_Station_Model;

public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleApiClient googleApiClient;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;

    private RelativeLayout First_show;
    private RelativeLayout Second_show;

    private RelativeLayout First_show_dialog;
    private RelativeLayout Second_show_dialog;


    private TextView TimeFirst_dialog;
    private TextView TimeSecond_dialog;

    public static GoogleMap Main_Map;
    public static Marker EV_Marker;
    public static Marker[] ST_Marker;
    public static LatLng camera;


    private String API = "https://stepsecret.xyz";

    public static RestAdapter restAdapter;

    private Double DistanceStationFirst = 0.00;
    private Double DistanceStationSecond = 0.00;
    private Double DistanceStationFirst_repeatedly = 0.00;
    private Double DistanceStationSecond_repeatedly = 0.00;

    private Double DistanceStationFirst_dialog = 0.00;
    private Double DistanceStationSecond_dialog = 0.00;
    private Double DistanceStationFirst_repeatedly_dialog = 0.00;
    private Double DistanceStationSecond_repeatedly_dialog = 0.00;


    public static String[][] Station;
    public static int Station_number;
    private String[][] TempDataEV;
    public static String[][] DataEV;

    public static int Station_number_dialog;
    private String[][] TempDataEV_dialog;
    public static String[][] DataEV_dialog;

    private Double TempDistanceFirst = 999999.00;
    private Double TempDistanceSecond = 999999.00;
    private Double TempDistanceFirst_repeatedly = 999999.00;
    private Double TempDistanceSecond_repeatedly = 999999.00;

    private Double TempDistanceFirst_dialog = 999999.00;
    private Double TempDistanceSecond_dialog = 999999.00;
    private Double TempDistanceFirst_repeatedly_dialog = 999999.00;
    private Double TempDistanceSecond_repeatedly_dialog = 999999.00;

    private int TempFirst = 0;

    public static int first = 0;
    public static int second = 0;

    private int TempSecond = 0;

    private Boolean Have_First = false;
    private Boolean Have_Second = false;

    private int TempFirst_dialog = 0;

    public static int first_dialog = 0;
    public static int second_dialog = 0;

    private int TempSecond_dialog = 0;

    private Boolean Have_First_dialog = false;
    private Boolean Have_Second_dialog = false;

    private Boolean Get_station_success = false;
    public static Boolean Check_Swap_Function = false;

    public static Double Cumulative_first = 0.0;
    public static Double Cumulative_Second = 0.0;

    public static Double Cumulative_first_dialog = 0.0;
    public static Double Cumulative_Second_dialog = 0.0;


    public static int First_time = 0;
    public static int Second_time = 0;

    public static int First_time_dialog = 0;
    public static int Second_time_dialog = 0;

    public static Boolean Check_time_dialog = false;
    public static Boolean Check_State_dialog = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();


        Main_Map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        Main_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Main_Map.setOnMarkerClickListener(this);

        First_show = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        Second_show = (RelativeLayout) findViewById(R.id.RelativeLayout2);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);


        //textView.setTextSize();


        // Create Google API Client instance
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Get_ST();
        startTimerThread();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Connect to Google API Client
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            // Disconnect Google API Client if available and connected
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Do something when connected with Google API Client


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
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            // Call Location Services
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when Location Provider not available
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Do something when Google API Client connection was suspended

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Do something when Google API Client connection failed

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        for(int i = 0 ; i < Station.length ; i++)
        {
            if(Station[i][1].equals(marker.getTitle()))
            {
                //Toast.makeText(getApplicationContext(), ""+Station[i][1], Toast.LENGTH_LONG).show();
                Station_number_dialog = i;
                DistanceStationFirst_dialog = Double.parseDouble(Station[i][4]);
                DistanceStationSecond_dialog = Double.parseDouble(Station[i][5]);
                DistanceStationFirst_repeatedly_dialog = Double.parseDouble(Station[i][6]);
                DistanceStationSecond_repeatedly_dialog = Double.parseDouble(Station[i][7]);



                showCustomView(Station[i][1]);

                break;
            }
        }


        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

        Main_Map.clear();
        camera = new LatLng(location.getLatitude(),location.getLongitude());
        Main_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 15));

        EV_Marker = Main_Map.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(),location.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
                    .title("Your Here"));



        if(Get_station_success)
        {
            for(int i = 0; i < Station.length ; i++)
            {

            ST_Marker[i] = Main_Map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(Station[i][2]), Double.parseDouble(Station[i][3])))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
                    .title("" + Station[i][1]));

            }
            Cal_near(location);
            Get_EV();


        }


    }

    public void Cal_near(Location location)
    {
        Double Temp_distance = 0.0;
        Double Temp_distance2 = 9999999.0;


            for(int i = 0 ; i < Station.length ; i++)
            {

                Temp_distance = Distance.calculateDistance(location.getLatitude(), location.getLongitude(), Double.parseDouble(Station[i][2]), Double.parseDouble(Station[i][3]));
                if(Temp_distance <= Temp_distance2)
                {
                    Station_number = i;
                    Temp_distance2 = Temp_distance;
                    DistanceStationFirst = Double.parseDouble(Station[i][4]);
                    DistanceStationSecond = Double.parseDouble(Station[i][5]);
                    DistanceStationFirst_repeatedly = Double.parseDouble(Station[i][6]);
                    DistanceStationSecond_repeatedly = Double.parseDouble(Station[i][7]);
                  //  Log.e("LOG TAG", "Cal_near " + i);
                }


            }

            First_show.setBackgroundColor(Color.parseColor("#"+Station[Station_number][11]));
            Second_show.setBackgroundColor(Color.parseColor("#" + Station[Station_number][12]));



            textView.setTextColor(Color.parseColor("#" + Station[Station_number][13]));
            textView2.setTextColor(Color.parseColor("#"+Station[Station_number][14]));
            textView3.setText(Station[Station_number][1]);
            textView3.setSelected(true);


    }


    public void Get_ST()
    {

        final Get_Station Get_ST_API = restAdapter.create(Get_Station.class);

        Get_ST_API.Get_Station_API("Get_ST", new Callback<Get_Station_Model>() {
            @Override
            public void success(Get_Station_Model ST_Model, Response response) {
                Station = ST_Model.dataST();
                // Log.e("LOG TAG", "Get_ST success " + Station.length);

                ST_Marker = new Marker[Station.length];
                Get_station_success = true;


            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("LOG TAG", "Get_EV failure");

                Get_ST();

            }
        });
    }


    public void Get_EV()
    {

        final Get_EV Get_EV_API = restAdapter.create(Get_EV.class);

        Get_EV_API.Get_EV_API("Get_EV", new Callback<Get_EV_Model>() {
            @Override
            public void success(Get_EV_Model EV_Model, Response response) {
                String[][] dataEV = EV_Model.dataEV();
                // Log.e("LOG TAG", "Get_EV success " + dataEV.length);

                Draw(dataEV);

                // if (!Check_Swap_Function) {

                //Log.e("LOG TAG", "TempFirst : " + TempFirst + " TempSecond : " + TempSecond);
                Cumulative.Get_Cumulative(Have_First, Have_Second, TempDataEV, TempFirst, TempSecond);
                //    Show();
                // }

            }

            @Override
            public void failure(RetrofitError error) {

                // Toast.makeText(getApplicationContext(), "GET NOT Success.2",
                //          Toast.LENGTH_LONG).show();
                Log.e("LOG TAG", "Get_EV failure");

                Get_EV();

            }
        });
    }

    public void Draw(String[][] dataEV)
    {
        TempDataEV = dataEV;
        DataEV = dataEV;
        TempDistanceFirst = 999999.00;
        TempDistanceSecond = 999999.00;
        TempDistanceFirst_repeatedly = 999999.00;
        TempDistanceSecond_repeatedly = 999999.00;
        TempFirst = -1;
        TempSecond = -1;
        Have_First = false;
        Have_Second = false;

                for (int i = 0; i < TempDataEV.length; i++) {

                    if (TempDataEV[i][0] != null && !TempDataEV[i][0].isEmpty() && TempDataEV[i][8].equals("RUN")) {

                        if(Station[Station_number][9].equals(TempDataEV[i][7]))
                        {

                            Double TempDataF = DistanceStationFirst - Double.parseDouble(TempDataEV[i][5]);
                            Double TempDataF_repeatedly = DistanceStationFirst_repeatedly - Double.parseDouble(TempDataEV[i][5]);

                            if (TempDataF > 0 && TempDataF < TempDistanceFirst)
                            {
                                TempDistanceFirst = TempDataF;
                                TempFirst = i;
                                first = i;

                                Have_First = true;

                            }
                            else if(Boolean.parseBoolean(TempDataEV[i][8]) == true && TempDataF_repeatedly > 0 && TempDataF_repeatedly < TempDistanceFirst_repeatedly)
                            {

                                TempDistanceFirst_repeatedly = TempDataF_repeatedly;
                                TempFirst = i;
                                first = i;

                                Have_First = true;


                            }

                            EV_Marker = Main_Map.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(TempDataEV[i][1]), Double.parseDouble(TempDataEV[i][2])))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ev))
                                    .title("" + TempDataEV[i][6]));
                        }
                        else if(Station[Station_number][10].equals(TempDataEV[i][7]))
                        {


                            Double TempDataS = DistanceStationSecond - Double.parseDouble(TempDataEV[i][5]);
                            Double TempDataS_repeatedly = DistanceStationSecond_repeatedly - Double.parseDouble(TempDataEV[i][5]);

                            if (TempDataS > 0 && TempDataS < TempDistanceSecond)
                            {
                                TempDistanceSecond = TempDataS;
                                TempSecond = i;
                                second = i ;

                                Have_Second = true;
                            }
                            else if(Boolean.parseBoolean(TempDataEV[i][8]) == true && TempDataS_repeatedly > 0 && TempDataS_repeatedly < TempDistanceSecond_repeatedly)
                            {

                                TempDistanceSecond_repeatedly = TempDataS_repeatedly;
                                TempSecond = i;
                                second = i ;

                                Have_Second = true;


                            }

                            EV_Marker = Main_Map.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(TempDataEV[i][1]), Double.parseDouble(TempDataEV[i][2])))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ev))
                                    .title("" + TempDataEV[i][6]));
                        }


                    }


                }





    }


    private void startTimerThread() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {

                            final int hourF = (First_time / 3600000);
                            final int minuteF = ((First_time % 3600000) / 60000 );
                            final int secondsF = (((First_time % 3600000) % 60000 ) / 1000);

                            final int hourS = (Second_time / 3600000);
                            final int minuteS = ((Second_time % 3600000) / 60000 );
                            final int secondsS = (((Second_time % 3600000) % 60000 ) / 1000);

                            if (Have_First == true && Have_Second == true)
                            {
                                // Log.e("LOG TAG", "secondsS : "+secondsS+" secondsF : "+secondsF);

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {

                                        textView.setText("0"+minuteF+":0"+secondsF+" นาที");
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {

                                        textView.setText("0"+minuteF+":"+secondsF+" นาที");

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        textView.setText(""+minuteF+":0"+secondsF+" นาที");

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        textView.setText("0"+minuteF+":"+secondsF+" นาที");

                                    }
                                    else
                                    {
                                        textView.setText(""+minuteF+":"+secondsF+" นาที");


                                    }
                                }
                                else
                                {
                                    textView.setText("--:-- นาที");

                                }

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {

                                        textView2.setText("0"+minuteS+":0"+secondsS+" นาที");
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {

                                        textView2.setText("0"+minuteS+":"+secondsS+" นาที");

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        textView2.setText(""+minuteS+":0"+secondsS+" นาที");

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        textView2.setText("0"+minuteS+":"+secondsS+" นาที");

                                    }
                                    else
                                    {
                                        textView2.setText(""+minuteS+":"+secondsS+" นาที");


                                    }
                                }
                                else
                                {
                                    textView2.setText("--:-- นาที");

                                }


                            }
                            else if (Have_First == true && Have_Second == false)
                            {

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {

                                        textView.setText("0"+minuteF+":0"+secondsF+" นาที");
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {

                                        textView.setText("0"+minuteF+":"+secondsF+" นาที");

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        textView.setText(""+minuteF+":0"+secondsF+" นาที");

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        textView.setText("0"+minuteF+":"+secondsF+" นาที");

                                    }
                                    else
                                    {
                                        textView.setText(""+minuteF+":"+secondsF+" นาที");


                                    }
                                }
                                else
                                {
                                    textView.setText("--:-- นาที");

                                }


                            }
                            else if (Have_First == false && Have_Second == true)
                            {

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {

                                        textView2.setText("0"+minuteS+":0"+secondsS+" นาที");
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {

                                        textView2.setText("0"+minuteS+":"+secondsS+" นาที");

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        textView2.setText(""+minuteS+":0"+secondsS+" นาที");

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        textView2.setText("0"+minuteS+":"+secondsS+" นาที");

                                    }
                                    else
                                    {
                                        textView2.setText(""+minuteS+":"+secondsS+" นาที");


                                    }
                                }
                                else
                                {
                                    textView2.setText("--:-- นาที");

                                }

                            }
                            else
                            {
                                textView.setText("--:-- นาที");
                                textView2.setText("--:-- นาที");
                            }

                            First_time = First_time - 1000;
                            Second_time = Second_time - 1000;


                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


    private void showCustomView(String title)
    {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_customview, true)
                .positiveText("Exit")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        Check_time_dialog = false;


                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {


                    }
                }).build();

        dialog.show();


        TextView Title= (TextView) dialog.getCustomView().findViewById(R.id.Station);
        TimeFirst_dialog = (TextView) dialog.getCustomView().findViewById(R.id.TimeFirst);
        TimeSecond_dialog = (TextView) dialog.getCustomView().findViewById(R.id.TimeSecond);
        First_show_dialog = (RelativeLayout) dialog.getCustomView().findViewById(R.id.RelativeLayout1);
        Second_show_dialog = (RelativeLayout) dialog.getCustomView().findViewById(R.id.RelativeLayout2);

        Title.setText(title);
        Title.setSelected(true);

        First_show_dialog.setBackgroundColor(Color.parseColor("#" + Station[Station_number_dialog][11]));
        Second_show_dialog.setBackgroundColor(Color.parseColor("#" + Station[Station_number_dialog][12]));

        TimeFirst_dialog.setTextColor(Color.parseColor("#" + Station[Station_number_dialog][13]));
        TimeSecond_dialog.setTextColor(Color.parseColor("#" + Station[Station_number_dialog][14]));



        Draw_dialog(TempDataEV);

        Log.e("LOG TAG", "Get_Cumulative_dialog : ");
        Cumulative.Get_Cumulative_dialog(Have_First_dialog, Have_Second_dialog, TempDataEV_dialog, TempFirst_dialog, TempSecond_dialog);

        Check_time_dialog = true;
        Log.e("LOG TAG", "startTimerThread_dialog : ");
        startTimerThread_dialog(dialog);

    }


    public void Draw_dialog(String[][] dataEV)
    {
        TempDataEV_dialog = dataEV;
        DataEV_dialog = dataEV;
        TempDistanceFirst_dialog = 999999.00;
        TempDistanceSecond_dialog = 999999.00;
        TempDistanceFirst_repeatedly_dialog = 999999.00;
        TempDistanceSecond_repeatedly_dialog = 999999.00;
        TempFirst_dialog = -1;
        TempSecond_dialog = -1;
        Have_First_dialog = false;
        Have_Second_dialog = false;


        for (int i = 0; i < TempDataEV_dialog.length; i++) {
            Log.e("LOG TAG", "Draw_dialog : "+i);
            if (TempDataEV_dialog[i][0] != null && !TempDataEV_dialog[i][0].isEmpty() && TempDataEV_dialog[i][8].equals("RUN")) {

                if(Station[Station_number_dialog][9].equals(TempDataEV_dialog[i][7]))
                {

                    Double TempDataF = DistanceStationFirst_dialog - Double.parseDouble(TempDataEV_dialog[i][5]);
                    Double TempDataF_repeatedly = DistanceStationFirst_repeatedly_dialog - Double.parseDouble(TempDataEV_dialog[i][5]);

                    if (TempDataF > 0 && TempDataF < TempDistanceFirst_dialog)
                    {
                        TempDistanceFirst_dialog = TempDataF;
                        TempFirst_dialog = i;
                        first_dialog = i;

                        Have_First_dialog = true;
                        Log.e("LOG TAG", "Have_First_dialog : "+i);
                    }
                    else if(Boolean.parseBoolean(TempDataEV_dialog[i][8]) == true && TempDataF_repeatedly > 0 && TempDataF_repeatedly < TempDistanceFirst_repeatedly_dialog)
                    {

                        TempDistanceFirst_repeatedly_dialog = TempDataF_repeatedly;
                        TempFirst_dialog = i;
                        first_dialog = i;

                        Have_First_dialog = true;
                        Log.e("LOG TAG", "Have_First_dialog : "+i);

                    }


                }
                else if(Station[Station_number_dialog][10].equals(TempDataEV_dialog[i][7]))
                {


                    Double TempDataS = DistanceStationSecond_dialog - Double.parseDouble(TempDataEV_dialog[i][5]);
                    Double TempDataS_repeatedly = DistanceStationSecond_repeatedly_dialog - Double.parseDouble(TempDataEV_dialog[i][5]);

                    if (TempDataS > 0 && TempDataS < TempDistanceSecond_dialog)
                    {
                        TempDistanceSecond_dialog = TempDataS;
                        TempSecond_dialog = i;
                        second_dialog = i ;

                        Have_Second_dialog = true;
                        Log.e("LOG TAG", "Have_Second_dialog : "+i);
                    }
                    else if(Boolean.parseBoolean(TempDataEV_dialog[i][8]) == true && TempDataS_repeatedly > 0 && TempDataS_repeatedly < TempDistanceSecond_repeatedly_dialog)
                    {

                        TempDistanceSecond_repeatedly_dialog = TempDataS_repeatedly;
                        TempSecond_dialog = i;
                        second_dialog = i ;

                        Have_Second_dialog = true;
                        Log.e("LOG TAG", "Have_Second_dialog : "+i);

                    }


                }


            }


        }





    }

    private void startTimerThread_dialog(final MaterialDialog dialog) {



        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                while (Check_time_dialog) {

                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {


                            final int hourF = (First_time_dialog / 3600000);
                            final int minuteF = ((First_time_dialog % 3600000) / 60000 );
                            final int secondsF = (((First_time_dialog % 3600000) % 60000 ) / 1000);

                            final int hourS = (Second_time_dialog / 3600000);
                            final int minuteS = ((Second_time_dialog % 3600000) / 60000 );
                            final int secondsS = (((Second_time_dialog % 3600000) % 60000 ) / 1000);

                           // Log.e("LOG TAG", "First_time_dialog " + First_time_dialog + " Second_time_dialog : " + Second_time_dialog);


                            if (Have_First_dialog == true && Have_Second_dialog == true)
                            {



                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                       // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":0" + secondsF + " นาที");
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":" + secondsF + " นาที");

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("" + minuteF + ":0" + secondsF + " นาที");

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":" + secondsF + " นาที");

                                    }
                                    else
                                    {
                                       // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("" + minuteF + ":" + secondsF + " นาที");


                                    }
                                }
                                else
                                {
                                   // Log.e("LOG TAG", "2 minuteF : "+minuteF+" secondsF : "+secondsF);
                                    TimeFirst_dialog.setText("--:-- นาที");

                                }

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                       // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":0" + secondsS + " นาที");
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":" + secondsS + " นาที");

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("" + minuteS + ":0" + secondsS + " นาที");

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                       // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":" + secondsS + " นาที");

                                    }
                                    else
                                    {
                                       // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("" + minuteS + ":" + secondsS + " นาที");


                                    }
                                }
                                else
                                {
                                   // Log.e("LOG TAG", "2 minuteS : "+minuteS+" secondsS : "+secondsS);
                                    TimeSecond_dialog.setText("--:-- นาที");

                                }


                            }
                            else if (Have_First_dialog == true && Have_Second_dialog == false)
                            {

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                       // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":0" + secondsF + " นาที");
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {
                                       // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":" + secondsF + " นาที");

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                       // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("" + minuteF + ":0" + secondsF + " นาที");

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                       // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("0" + minuteF + ":" + secondsF + " นาที");

                                    }
                                    else
                                    {
                                      //  Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        TimeFirst_dialog.setText("" + minuteF + ":" + secondsF + " นาที");


                                    }
                                }
                                else
                                {
                                   // Log.e("LOG TAG", "4 minuteF : "+minuteF+" secondsF : "+secondsF);
                                    TimeFirst_dialog.setText("--:-- นาที");

                                }


                            }
                            else if (Have_First_dialog == false && Have_Second_dialog == true)
                            {

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                       // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":0" + secondsS + " นาที");
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                      //  Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":" + secondsS + " นาที");

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                      //  Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("" + minuteS + ":0" + secondsS + " นาที");

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                       // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("0" + minuteS + ":" + secondsS + " นาที");

                                    }
                                    else
                                    {
                                       // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        TimeSecond_dialog.setText("" + minuteS + ":" + secondsS + " นาที");


                                    }
                                }
                                else
                                {
                                   // Log.e("LOG TAG", "4 minuteS : "+minuteS+" secondsS : "+secondsS);
                                    TimeSecond_dialog.setText("--:-- นาที");

                                }

                            }
                            else
                            {
                              //  Log.e("LOG TAG", "111 minuteF : "+minuteF+" secondsF : "+secondsF);
                              //  Log.e("LOG TAG", "111 minuteS : "+minuteS+" secondsS : "+secondsS);
                                TimeFirst_dialog.setText("--:-- นาที");
                                TimeSecond_dialog.setText("--:-- นาที");
                            }

                            First_time_dialog = First_time_dialog - 1000;
                            Second_time_dialog = Second_time_dialog - 1000;


                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

}