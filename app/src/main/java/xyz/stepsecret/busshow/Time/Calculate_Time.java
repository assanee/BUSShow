package xyz.stepsecret.busshow.Time;

import android.util.Log;

import xyz.stepsecret.busshow.MainActivity;

/**
 * Created by XEUSLAB on 31/1/2559.
 */
public class Calculate_Time {

    public static void Get_TimeFS()
    {
        MainActivity.First_time = 0;
        MainActivity.Second_time = 0;

        MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][4]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                /MainActivity.Cumulative_first)*1000;
        MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][5]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                /MainActivity.Cumulative_Second)*1000;

      //  Log.e("LOG TAG", "Fist_time : " + MainActivity.First_time+" Second_time : " + MainActivity.Second_time);

    }

    public static void Get_TimeF()
    {
        MainActivity.First_time = 0;

        MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][4]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                /MainActivity.Cumulative_first)*1000;

      //  Log.e("LOG TAG", "Fist_time : " + MainActivity.First_time);

    }

    public static void Get_TimeS()
    {

        MainActivity.Second_time = 0;

        MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][5]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                /MainActivity.Cumulative_Second)*1000;

      //  Log.e("LOG TAG", " Second_time : "+MainActivity.Second_time);

    }

    public static void Get_TimeFS_dialog()
    {
        MainActivity.First_time_dialog = 0;
        MainActivity.Second_time_dialog = 0;

        MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][4]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                /MainActivity.Cumulative_first_dialog)*1000;
        MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                /MainActivity.Cumulative_Second_dialog)*1000;

        Log.e("LOG TAG", "Fist_time_dialog : " + MainActivity.First_time_dialog+" Second_time_dialog : " + MainActivity.Second_time_dialog);

    }

    public static void Get_TimeF_dialog()
    {
        MainActivity.First_time_dialog = 0;

        MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][4]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                /MainActivity.Cumulative_first_dialog)*1000;

        Log.e("LOG TAG", "Fist_time_dialog : " + MainActivity.First_time_dialog);

    }

    public static void Get_TimeS_dialog()
    {

        MainActivity.Second_time_dialog = 0;

        MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                /MainActivity.Cumulative_Second_dialog)*1000;

        Log.e("LOG TAG", " D1 : "+Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]));
        Log.e("LOG TAG", " D2 : "+Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]));
        Log.e("LOG TAG", " Cumu : "+MainActivity.Cumulative_Second_dialog);
        Log.e("LOG TAG", " Second_time_dialog : "+MainActivity.Second_time_dialog);

    }
}
