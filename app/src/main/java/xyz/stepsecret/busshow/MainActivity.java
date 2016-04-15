package xyz.stepsecret.busshow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import xyz.stepsecret.busshow.API.Get_EV;
import xyz.stepsecret.busshow.API.Get_Station;
import xyz.stepsecret.busshow.API.PutTOKEN_API;
import xyz.stepsecret.busshow.Cumulative.Cumulative;
import xyz.stepsecret.busshow.Distance.Distance;
import xyz.stepsecret.busshow.GCM.QuickstartPreferences;
import xyz.stepsecret.busshow.GCM.RegistrationIntentService;
import xyz.stepsecret.busshow.Model.Get_EV_Model;
import xyz.stepsecret.busshow.Model.Get_Station_Model;
import xyz.stepsecret.busshow.Model.PutTOKEN_Model;
import xyz.stepsecret.busshow.TinyDB.TinyDB;

public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {

    //GCM
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private ProgressBar mRegistrationProgressBar;


///////////////////////

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
    public static Marker Your_Marker;
    public static Marker[] ST_Marker;
    public static LatLng camera;


    private String API = "https://stepsecret.xyz";

    public static RestAdapter restAdapter;

    private static Double DistanceStationFirst = 0.00;
    private static Double DistanceStationSecond = 0.00;
    private static Double DistanceStationFirst_repeatedly = 0.00;
    private static Double DistanceStationSecond_repeatedly = 0.00;

    private Double DistanceStationFirst_dialog = 0.00;
    private Double DistanceStationSecond_dialog = 0.00;
    private Double DistanceStationFirst_repeatedly_dialog = 0.00;
    private Double DistanceStationSecond_repeatedly_dialog = 0.00;


    public static String[][] Station;
    public static int Station_number;
    private static String[][] TempDataEV;
    public static String[][] DataEV;

    public static int Station_number_dialog;
    private String[][] TempDataEV_dialog;
    public static String[][] DataEV_dialog;

    private static Double TempDistanceFirst = 999999.00;
    private static Double TempDistanceSecond = 999999.00;
    private static Double TempDistanceFirst_repeatedly = 999999.00;
    private static Double TempDistanceSecond_repeatedly = 999999.00;

    private Double TempDistanceFirst_dialog = 999999.00;
    private Double TempDistanceSecond_dialog = 999999.00;
    private Double TempDistanceFirst_repeatedly_dialog = 999999.00;
    private Double TempDistanceSecond_repeatedly_dialog = 999999.00;

    private static int TempFirst = 0;

    public static int first = 0;
    public static int second = 0;

    private static int TempSecond = 0;

    private static Boolean Have_First = false;
    public static Boolean Have_First_repeatedly = false;
    private static Boolean Have_Second = false;
    public static Boolean Have_Second_repeatedly = false;

    private int TempFirst_dialog = 0;

    public static int first_dialog = 0;
    public static int second_dialog = 0;

    private int TempSecond_dialog = 0;

    private Boolean Have_First_dialog = false;
    public static Boolean Have_First_dialog_repeatedly = false;
    private Boolean Have_Second_dialog = false;
    public static Boolean Have_Second_dialog_repeatedly = false;

    public static Boolean Get_station_success = false;
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

    public static TinyDB tinydb;

    public static Boolean Onstart = false;

    //public static WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    private RelativeLayout re;
    private LobsterPicker lobsterPicker;
    private LobsterShadeSlider shadeSlider;
    private LobsterOpacitySlider opacitySlider;

    private LinearLayout LY;

    private MaterialSpinner spinnerBG;
    private int BG_select = 0;

    private Switch switchPush;

    private Boolean Check_time_main = true;
    public static Boolean Status_Get_EV = true;

    public String Temp_display_first;
    public String Temp_display_last;
    public String Temp_display_first_dialog;
    public String Temp_display_last_dialog;

    public static SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tinydb = new TinyDB(getApplicationContext());

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

        LY = (LinearLayout) findViewById(R.id.LinearLayout1);

        LY.setBackgroundColor(tinydb.getInt("ColorBG", 16777215));

        findViewById(R.id.Logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Confirm_Logout();
            }
        });

        findViewById(R.id.change_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Change_color();
            }
        });

        findViewById(R.id.push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Change_push();
            }
        });


        Check_time_main = true;

        //textView.setTextSize();


        // Create Google API Client instance
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {

                    // Toast.makeText(getApplicationContext(), "GCM success",
                    //         Toast.LENGTH_LONG).show();
                }
                else
                {

                    //Toast.makeText(getApplicationContext(), "GCM failure",
                    //       Toast.LENGTH_LONG).show();
                }
            }
        };





        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        turnGPSOn();
        initViewrefresh();


    }

    @SuppressWarnings("deprecation")
    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }


    private void initViewrefresh() {

        int color = tinydb.getInt("ColorRF", -43231);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_red, R.color.holo_green, R.color.holo_blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setRefreshing(true);
                Log.e(TAG, "onRefresh");

                if(isOnline())
                {
                    if(Get_station_success)
                    {
                        Get_EV();
                    }
                    else
                    {
                        Get_ST();
                        //Get_EV();
                    }


                }
                else
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }



            }
        });






    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.e(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Connect to Google API Client
        Onstart = true;
        Check_time_main = true;
        googleApiClient.connect();

        if(isOnline())
        {
            Toast.makeText(getApplicationContext(), "isOnline",
                     Toast.LENGTH_LONG).show();
            Get_ST();
            startTimerThread();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "not Online",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            // Disconnect Google API Client if available and connected
            Onstart = false;
            Check_time_main = false;
            googleApiClient.disconnect();
        }
        finish();
    }

    @Override
    protected void onResume() {
        Check_time_main = true;
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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

                Onstart = false;

                Check_time_main = false;

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
                    .setInterval(5000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
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

        Check_time_dialog = false;
        for(int i = 0 ; i < Station.length ; i++)
        {
            if(Station[i][1].equals(marker.getTitle()))
            {
                //Toast.makeText(getApplicationContext(), Station[i][1], Toast.LENGTH_LONG).show();
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

        //location.setLatitude(16.74045);
        //location.setLongitude(100.199215);


        camera = new LatLng(location.getLatitude(),location.getLongitude());
        Main_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 15));

        if(Your_Marker == null)
        {
            Your_Marker = Main_Map.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(),location.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.your))
                    .title("Your Here"));

        }
        else
        {
            Your_Marker.remove();
            Your_Marker = Main_Map.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(),location.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.your))
                    .title("Your Here"));

        }

        if(Get_station_success)
        {

            Cal_near(location);
            //Get_EV();
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


    public static void Get_ST()
    {

        Log.e("LOG TAG", "Get_ST start " );

        final Get_Station Get_ST_API = restAdapter.create(Get_Station.class);

        Get_ST_API.Get_Station_API("Get_ST", new Callback<Get_Station_Model>() {
            @Override
            public void success(Get_Station_Model ST_Model, Response response) {
                Station = ST_Model.dataST();

                 Log.e("LOG TAG", "Get_ST success " + Station.length);

                ST_Marker = new Marker[Station.length];
                Get_station_success = true;

                Get_EV();

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("LOG TAG", "Get_ST failure");

                Get_ST();

            }
        });
    }


    public static void Get_EV()
    {

        if(Get_station_success)
        {
            Status_Get_EV = false;

            final Get_EV Get_EV_API = restAdapter.create(Get_EV.class);

            Get_EV_API.Get_EV_API("Get_EV", new Callback<Get_EV_Model>() {
                @Override
                public void success(Get_EV_Model EV_Model, Response response) {

                    Status_Get_EV = true;

                    String[][] dataEV = EV_Model.dataEV();


                    Draw(dataEV);

                    Cumulative.Get_Cumulative(Have_First, Have_Second, TempDataEV, TempFirst, TempSecond);




                    mSwipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void failure(RetrofitError error) {

                    Status_Get_EV = true;

                    // Toast.makeText(getApplicationContext(), "GET NOT Success.2",
                    //          Toast.LENGTH_LONG).show();
                    Log.e("LOG TAG", "Get_EV failure");

                    Get_EV();

                }
            });

        }


    }



    public static void Draw(String[][] dataEV)
    {
        Main_Map.clear();

        for(int i = 0; i < Station.length ; i++)
        {

            ST_Marker[i] = Main_Map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(Station[i][2]), Double.parseDouble(Station[i][3])))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
                    .title( Station[i][1]));

        }


        TempDataEV = dataEV;
        DataEV = dataEV;
        TempDistanceFirst = 999999.00;
        TempDistanceSecond = 999999.00;
        TempDistanceFirst_repeatedly = 999999.00;
        TempDistanceSecond_repeatedly = 999999.00;
        TempFirst = -1;
        TempSecond = -1;
        Have_First = false;
        Have_First_repeatedly = false;
        Have_Second = false;
        Have_Second_repeatedly = false;

        for (int i = 0; i < TempDataEV.length; i++) {

            if (TempDataEV[i][0] != null && !TempDataEV[i][0].isEmpty() && TempDataEV[i][8].equals("RUN"))
            {


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
                        Have_First_repeatedly = false;


                    }
                    else if(Boolean.parseBoolean(Station[Station_number][8]) == true && TempDataF_repeatedly > 0 && TempDataF_repeatedly < TempDistanceFirst_repeatedly)
                    {

                        TempDistanceFirst_repeatedly = TempDataF_repeatedly;
                        TempFirst = i;
                        first = i;

                        Have_First = true;
                        Have_First_repeatedly = true;

                        //Log.e("LOG TAG", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY : Have_First : "+Have_First);
                    }

                    //Log.e("LOG TAG", "Have_First : "+Have_First +" Have_First_repeatedly : "+Have_First_repeatedly);
                    //Log.e("LOG TAG", "repeatedly : "+Boolean.parseBoolean(Station[Station_number][8])+" Station[i][8] => "+Station[Station_number][8]+" Station_number : "+Station_number);

                    EV_Marker = Main_Map.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(TempDataEV[i][1]), Double.parseDouble(TempDataEV[i][2])))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ev))
                            .title( TempDataEV[i][6]));
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
                        Have_Second_repeatedly = false;
                    }
                    else if(Boolean.parseBoolean(Station[Station_number][8]) == true && TempDataS_repeatedly > 0 && TempDataS_repeatedly < TempDistanceSecond_repeatedly)
                    {

                        TempDistanceSecond_repeatedly = TempDataS_repeatedly;
                        TempSecond = i;
                        second = i ;

                        Have_Second = true;
                        Have_Second_repeatedly = true;


                    }

                    EV_Marker = Main_Map.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(TempDataEV[i][1]), Double.parseDouble(TempDataEV[i][2])))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ev))
                            .title( TempDataEV[i][6]));
                }


            }


        }


        //Log.e("LOG TAG", "Have_First : "+Have_First+" Have_Second : "+Have_Second);




    }


    private void startTimerThread() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                while (Check_time_main) {

                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {



                            //final int hourF = (First_time / 3600000);
                            final int minuteF = ((First_time % 3600000) / 60000 );
                            final int secondsF = (((First_time % 3600000) % 60000 ) / 1000);

                            //final int hourS = (Second_time / 3600000);
                            final int minuteS = ((Second_time % 3600000) / 60000 );
                            final int secondsS = (((Second_time % 3600000) % 60000 ) / 1000);

                           //Log.e("LOG TAG", "Have_First : "+Have_First+" Have_Second : "+Have_Second);

                            if (Have_First == true && Have_Second == true)
                            {

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.lastzero)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {

                                        //textView.setText("0"+minuteF+":"+secondsF+" นาที");
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        Temp_display_first = minuteF+getResources().getString(R.string.lastzero)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else
                                    {
                                        Temp_display_first = minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);


                                    }
                                }
                                else
                                {
                                    //Log.e("LOG TAG", "First_time"+First_time+"  secondsF : "+secondsF );
                                    Temp_display_first = getResources().getString(R.string.timeEmpty);
                                    textView.setText(Temp_display_first);

                                }

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.lastzero)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        Temp_display_last = minuteS+getResources().getString(R.string.lastzero)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else
                                    {
                                        Temp_display_last = minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);


                                    }
                                }
                                else
                                {
                                    //Log.e("LOG TAG", "second 1111111111111111111111111111111111");
                                    Temp_display_last = getResources().getString(R.string.timeEmpty);
                                    textView2.setText(Temp_display_last);

                                }


                            }
                            else if (Have_First == true && Have_Second == false)
                            {

                                Temp_display_first = getResources().getString(R.string.timeEmpty);
                                textView2.setText(Temp_display_first);

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.lastzero)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        Temp_display_first = minuteF+getResources().getString(R.string.lastzero)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        Temp_display_first = getResources().getString(R.string.zero)+minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);

                                    }
                                    else
                                    {
                                        Temp_display_first = minuteF+getResources().getString(R.string.colon)+secondsF+getResources().getString(R.string.minute);
                                        textView.setText(Temp_display_first);


                                    }
                                }
                                else
                                {
                                    //Log.e("LOG TAG", "first 222222222222222222222222222222");
                                    Temp_display_first = getResources().getString(R.string.timeEmpty);
                                    textView.setText(Temp_display_first);

                                }


                            }
                            else if (Have_First == false && Have_Second == true)
                            {
                                textView.setText(getResources().getString(R.string.timeEmpty));

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.lastzero)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        Temp_display_last = minuteS+getResources().getString(R.string.lastzero)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        Temp_display_last = getResources().getString(R.string.zero)+minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);

                                    }
                                    else
                                    {
                                        Temp_display_last = minuteS+getResources().getString(R.string.colon)+secondsS+getResources().getString(R.string.minute);
                                        textView2.setText(Temp_display_last);


                                    }
                                }
                                else
                                {
                                    //Log.e("LOG TAG", "second 222222222222222222222222222222");
                                    Temp_display_last = getResources().getString(R.string.timeEmpty);
                                    textView2.setText(Temp_display_last);

                                }

                            }
                            else
                            {
                                //Log.e("LOG TAG", "first 3333333333333333333333333333333");
                                //Log.e("LOG TAG", "second 3333333333333333333333333333333");
                                Temp_display_last = getResources().getString(R.string.timeEmpty);
                                textView.setText(Temp_display_last);
                                textView2.setText(Temp_display_last);
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
                    public void onPositive(MaterialDialog dialog) {
                        // Log.e("LOG TAG", "onPositive YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

                        Check_time_dialog = false;
                        dialog.dismiss();

                    }

                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Check_time_dialog = false;
                        //Log.e("LOG TAG", "onDismiss YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

                    }
                })
                .build();



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

        ///Log.e("LOG TAG", "Get_Cumulative_dialog : ");
        Cumulative.Get_Cumulative_dialog(Have_First_dialog, Have_Second_dialog, TempDataEV_dialog, TempFirst_dialog, TempSecond_dialog);

        Check_time_dialog = true;
        //Log.e("LOG TAG", "startTimerThread_dialog : ");
        startTimerThread_dialog();

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
        Have_First_dialog_repeatedly = false;
        Have_Second_dialog = false;
        Have_Second_dialog_repeatedly = false;


        for (int i = 0; i < TempDataEV_dialog.length; i++) {
            //Log.e("LOG TAG", "Draw_dialog : "+i);
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
                        Have_First_dialog_repeatedly = false;
                        //Log.e("LOG TAG", "Have_First_dialog : "+i);
                    }
                    else if(Boolean.parseBoolean(Station[Station_number_dialog][8]) && TempDataF_repeatedly > 0 && TempDataF_repeatedly < TempDistanceFirst_repeatedly_dialog)
                    {

                        TempDistanceFirst_repeatedly_dialog = TempDataF_repeatedly;
                        TempFirst_dialog = i;
                        first_dialog = i;

                        Have_First_dialog = true;
                        Have_First_dialog_repeatedly = true;
                        //Log.e("LOG TAG", "Have_First_dialog : "+i);

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
                        Have_Second_dialog_repeatedly = false;
                        //Log.e("LOG TAG", "Have_Second_dialog : "+i);
                    }
                    else if(Boolean.parseBoolean(Station[Station_number_dialog][8]) && TempDataS_repeatedly > 0 && TempDataS_repeatedly < TempDistanceSecond_repeatedly_dialog)
                    {

                        TempDistanceSecond_repeatedly_dialog = TempDataS_repeatedly;
                        TempSecond_dialog = i;
                        second_dialog = i ;

                        Have_Second_dialog = true;
                        Have_Second_dialog_repeatedly = true;
                        // Log.e("LOG TAG", "Have_Second_dialog : "+i);

                    }


                }


            }


        }





    }

    private void startTimerThread_dialog() {



        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            //private long startTime = System.currentTimeMillis();
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


                            //final int hourF = (First_time_dialog / 3600000);
                            final int minuteF = ((First_time_dialog % 3600000) / 60000 );
                            final int secondsF = (((First_time_dialog % 3600000) % 60000 ) / 1000);

                            //final int hourS = (Second_time_dialog / 3600000);
                            final int minuteS = ((Second_time_dialog % 3600000) / 60000 );
                            final int secondsS = (((Second_time_dialog % 3600000) % 60000 ) / 1000);

                            // Log.e("LOG TAG", "First_time_dialog " + First_time_dialog + " Second_time_dialog : " + Second_time_dialog);


                            if (Have_First_dialog == true && Have_Second_dialog == true )
                            {



                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                        // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.lastzero) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = minuteF + getResources().getString(R.string.lastzero) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else
                                    {
                                        // Log.e("LOG TAG", "1 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);


                                    }
                                }
                                else
                                {
                                    // Log.e("LOG TAG", "2 minuteF : "+minuteF+" secondsF : "+secondsF);
                                    Temp_display_first_dialog = getResources().getString(R.string.timeEmpty);
                                    TimeFirst_dialog.setText(Temp_display_first_dialog);

                                }

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                        // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.lastzero) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = minuteS + getResources().getString(R.string.lastzero) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else
                                    {
                                        // Log.e("LOG TAG", "1 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);


                                    }
                                }
                                else
                                {
                                    // Log.e("LOG TAG", "2 minuteS : "+minuteS+" secondsS : "+secondsS);
                                    Temp_display_last_dialog = getResources().getString(R.string.timeEmpty);
                                    TimeSecond_dialog.setText(Temp_display_last_dialog);

                                }


                            }
                            else if (Have_First_dialog == true && Have_Second_dialog == false)
                            {

                                if(secondsF >= 0 )
                                {
                                    if(minuteF < 10 && secondsF < 10 )
                                    {
                                        // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.lastzero) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);
                                    }
                                    else if(minuteF < 10 && secondsF > 10)
                                    {
                                        // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else if(minuteF >= 10 && secondsF < 10)
                                    {
                                        // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = minuteF + getResources().getString(R.string.lastzero) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else if(minuteF < 10 && secondsF == 10)
                                    {
                                        // Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = getResources().getString(R.string.zero)  + minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);

                                    }
                                    else
                                    {
                                        //  Log.e("LOG TAG", "3 minuteF : "+minuteF+" secondsF : "+secondsF);
                                        Temp_display_first_dialog = minuteF + getResources().getString(R.string.colon) + secondsF +getResources().getString(R.string.minute);
                                        TimeFirst_dialog.setText(Temp_display_first_dialog);


                                    }
                                }
                                else
                                {
                                    // Log.e("LOG TAG", "4 minuteF : "+minuteF+" secondsF : "+secondsF);
                                    Temp_display_first_dialog = getResources().getString(R.string.timeEmpty);
                                    TimeFirst_dialog.setText(Temp_display_first_dialog);

                                }


                            }
                            else if (Have_First_dialog == false && Have_Second_dialog == true)
                            {

                                if(secondsS >= 0 )
                                {
                                    if(minuteS < 10 && secondsS < 10 )
                                    {
                                        // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.lastzero) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);
                                    }
                                    else if(minuteS < 10 && secondsS > 10)
                                    {
                                        //  Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else if(minuteS >= 10 && secondsS < 10)
                                    {
                                        //  Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);

                                        Temp_display_last_dialog = minuteS + getResources().getString(R.string.lastzero) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else if(minuteS < 10 && secondsS == 10)
                                    {
                                        // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = getResources().getString(R.string.zero)  + minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);

                                    }
                                    else
                                    {
                                        // Log.e("LOG TAG", "3 minuteS : "+minuteS+" secondsS : "+secondsS);
                                        Temp_display_last_dialog = minuteS + getResources().getString(R.string.colon) + secondsS +getResources().getString(R.string.minute);
                                        TimeSecond_dialog.setText(Temp_display_last_dialog);


                                    }
                                }
                                else
                                {
                                    // Log.e("LOG TAG", "4 minuteS : "+minuteS+" secondsS : "+secondsS);
                                    Temp_display_last_dialog = getResources().getString(R.string.timeEmpty);
                                    TimeSecond_dialog.setText(Temp_display_last_dialog);

                                }

                            }
                            else
                            {
                                //  Log.e("LOG TAG", "111 minuteF : "+minuteF+" secondsF : "+secondsF);
                                //  Log.e("LOG TAG", "111 minuteS : "+minuteS+" secondsS : "+secondsS);
                                Temp_display_last_dialog = getResources().getString(R.string.timeEmpty);
                                TimeFirst_dialog.setText(Temp_display_last_dialog);
                                TimeSecond_dialog.setText(Temp_display_last_dialog);
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

    public void Change_push()
    {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_push, true)
                .positiveText("OK")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        dialog.dismiss();

                    }

                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {



                    }
                })
                .build();



        dialog.show();

        switchPush = (Switch) dialog.getCustomView().findViewById(R.id.switch1);
        switchPush.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (switchPush.isChecked()) {

                    tinydb.putBoolean("showPush",switchPush.isChecked());

                    Toast.makeText(MainActivity.this,
                            String.valueOf("Push notification is On"),
                            Toast.LENGTH_SHORT).show();
                } else {

                    tinydb.putBoolean("showPush",switchPush.isChecked());
                    Toast.makeText(MainActivity.this,
                            String.valueOf("Push notification is Off"),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        switchPush.setChecked(tinydb.getBoolean("showPush", false));





    }

    public void Change_color()
    {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_color, true)
                .positiveText("OK")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        dialog.dismiss();

                    }

                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {



                    }
                })
                .build();



        dialog.show();

        spinnerBG = (MaterialSpinner) dialog.getCustomView().findViewById(R.id.spinner);
        lobsterPicker = (LobsterPicker) dialog.getCustomView().findViewById(R.id.lobsterpicker);
        shadeSlider = (LobsterShadeSlider) dialog.getCustomView().findViewById(R.id.shadeslider);
        opacitySlider = (LobsterOpacitySlider) dialog.getCustomView().findViewById(R.id.opacityslider);

        re = (RelativeLayout) dialog.getCustomView().findViewById(R.id.RelativeLayout5);

        lobsterPicker.addDecorator(shadeSlider);
        lobsterPicker.addDecorator(opacitySlider);


        lobsterPicker.addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {

                if (BG_select == 1) {
                    tinydb.putInt("ColorBG", lobsterPicker.getColor());
                    LY.setBackgroundColor(lobsterPicker.getColor());
                } else if (BG_select == 2) {
                    Log.e(TAG, "color2 : " + color);
                    tinydb.putInt("ColorRF", lobsterPicker.getColor());
                    //mWaveSwipeRefreshLayout.setWaveColor(lobsterPicker.getColor());
                    mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);
                }
                re.setBackgroundColor(lobsterPicker.getColor());


            }

            @Override
            public void onColorSelected(@ColorInt int color) {


            }
        });

        spinnerBG.setItems("Select", "Background Color", "Refresh");
        spinnerBG.setBackgroundColor(Color.parseColor("#CDDC39"));

        spinnerBG.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                BG_select = position;

            }
        });


    }

    public void Confirm_Logout()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Logout();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void Logout()
    {
        String ApiKey = tinydb.getString("ApiKey");

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();

        PutTOKEN_API Puttoken = restAdapter.create(PutTOKEN_API.class);
        Puttoken.PutTOKEN(ApiKey, "empty", new Callback<PutTOKEN_Model>() {
            @Override
            public void success(PutTOKEN_Model PutTOKEN, Response response) {


                Log.e("LOG TAG", "Put Token error : " + PutTOKEN.getError());

                tinydb.clear();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("LOG TAG", "Logout failure");

                Logout();

            }
        });




    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}