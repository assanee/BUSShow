package xyz.stepsecret.busshow.Time;

import android.util.Log;

import xyz.stepsecret.busshow.MainActivity;

/**
 * Created by XEUSLAB on 31/1/2559.
 */
public class Calculate_Time {

    public static void Get_TimeFS()
    {
        if(MainActivity.Have_First_repeatedly != true && MainActivity.Have_Second_repeatedly != true)
        {
            MainActivity.First_time = 0;
            MainActivity.Second_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][4]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;
            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][5]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;
        }
        else if(MainActivity.Have_First_repeatedly != true && MainActivity.Have_Second_repeatedly == true)
        {
            MainActivity.First_time = 0;
            MainActivity.Second_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][4]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;
            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][7]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;

        }
        else if(MainActivity.Have_First_repeatedly == true && MainActivity.Have_Second_repeatedly != true)
        {
            MainActivity.First_time = 0;
            MainActivity.Second_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][6]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;
            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][5]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;

        }
        else
        {
            MainActivity.First_time = 0;
            MainActivity.Second_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][6]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;
            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][7]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;

        }


      //  Log.e("LOG TAG", "Fist_time : " + MainActivity.First_time+" Second_time : " + MainActivity.Second_time);

    }

    public static void Get_TimeF()
    {

        if(MainActivity.Have_First_repeatedly != true)
        {
            MainActivity.First_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][4]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;

        }
        else
        {
            MainActivity.First_time = 0;

            MainActivity.First_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][6]) - Double.parseDouble(MainActivity.DataEV[MainActivity.first][5]))
                    /MainActivity.Cumulative_first)*1000;

        }



    }

    public static void Get_TimeS()
    {

        if(MainActivity.Have_Second_repeatedly != true)
        {
            MainActivity.Second_time = 0;

            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][5]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;

        }
        else
        {
            MainActivity.Second_time = 0;

            MainActivity.Second_time = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number][7]) - Double.parseDouble(MainActivity.DataEV[MainActivity.second][5]))
                    /MainActivity.Cumulative_Second)*1000;

        }




    }

    public static void Get_TimeFS_dialog()
    {
        if(MainActivity.Have_First_dialog_repeatedly != true && MainActivity.Have_Second_dialog_repeatedly != true)
        {
            MainActivity.First_time_dialog = 0;
            MainActivity.Second_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][4]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;
            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;
        }
        else if(MainActivity.Have_First_dialog_repeatedly != true && MainActivity.Have_Second_dialog_repeatedly == true)
        {
            MainActivity.First_time_dialog = 0;
            MainActivity.Second_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][4]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;
            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][7]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;

        }
        else if(MainActivity.Have_First_dialog_repeatedly == true && MainActivity.Have_Second_dialog_repeatedly != true)
        {
            MainActivity.First_time_dialog = 0;
            MainActivity.Second_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][6]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;
            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;

        }
        else
        {
            MainActivity.First_time_dialog = 0;
            MainActivity.Second_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][6]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;
            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][7]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;

        }


        //  Log.e("LOG TAG", "Fist_time : " + MainActivity.First_time_dialog+" Second_time_dialog : " + MainActivity.Second_time_dialog);

    }

    public static void Get_TimeF_dialog()
    {

        if(MainActivity.Have_First_dialog_repeatedly != true)
        {
            MainActivity.First_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][4]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;

        }
        else
        {
            MainActivity.First_time_dialog = 0;

            MainActivity.First_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][6]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.first_dialog][5]))
                    /MainActivity.Cumulative_first_dialog)*1000;

        }


        //  Log.e("LOG TAG", "Fist_time : " + MainActivity.First_time_dialog);

    }

    public static void Get_TimeS_dialog()
    {

        if(MainActivity.Have_Second_dialog_repeatedly != true)
        {
            MainActivity.Second_time_dialog = 0;

            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][5]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;

        }
        else
        {
            MainActivity.Second_time_dialog = 0;

            MainActivity.Second_time_dialog = (int)((Double.parseDouble(MainActivity.Station[MainActivity.Station_number_dialog][7]) - Double.parseDouble(MainActivity.DataEV_dialog[MainActivity.second_dialog][5]))
                    /MainActivity.Cumulative_Second_dialog)*1000;

        }


    }



}
