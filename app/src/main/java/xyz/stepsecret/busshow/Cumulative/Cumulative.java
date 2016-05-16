package xyz.stepsecret.busshow.Cumulative;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.stepsecret.busshow.API.Get_CumulativeF;
import xyz.stepsecret.busshow.API.Get_CumulativeFS;
import xyz.stepsecret.busshow.API.Get_CumulativeS;
import xyz.stepsecret.busshow.MainActivity;
import xyz.stepsecret.busshow.Model.Get_CumulativeFS_Model;
import xyz.stepsecret.busshow.Model.Get_CumulativeF_Model;
import xyz.stepsecret.busshow.Model.Get_CumulativeS_Model;
import xyz.stepsecret.busshow.Time.Calculate_Time;

/**
 * Created by XEUSLAB on 30/1/2559.
 */
public class Cumulative {

    public static void CumulativeFS(final String roundflowR, final String EVflowR, final String roundflowY, final String EVflowY)
    {
        final Get_CumulativeFS get_CumulativeFS_API = MainActivity.restAdapter.create(Get_CumulativeFS.class);

        get_CumulativeFS_API.Get_CumulativeFS_API( roundflowR, EVflowR, roundflowY, EVflowY, new Callback<Get_CumulativeFS_Model>() {
            @Override
            public void success(Get_CumulativeFS_Model EV_Model, Response response) {

                MainActivity.Cumulative_first = EV_Model.TempcumuF();
                MainActivity.Cumulative_Second = EV_Model.TempcumuS();
                Calculate_Time.Get_TimeFS();
                Log.e("LOG TAG", "CumulativeRY success TempcumuR " + EV_Model.TempcumuF());
                Log.e("LOG TAG", "CumulativeRY success TempcumuY " + EV_Model.TempcumuS());
            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeRY failure");

                CumulativeFS(roundflowR, EVflowR, roundflowY, EVflowY);

            }
        });
    }

    public static void CumulativeF(final String roundflowR, final String EVflowR)
    {
        final Get_CumulativeF get_CumulativeF_API = MainActivity.restAdapter.create(Get_CumulativeF.class);

        get_CumulativeF_API.Get_CumulativeF_API( roundflowR, EVflowR, new Callback<Get_CumulativeF_Model>() {
            @Override
            public void success(Get_CumulativeF_Model EV_Model, Response response) {

                MainActivity.Cumulative_first = EV_Model.TempcumuF();
                Calculate_Time.Get_TimeF();
                 Log.e("LOG TAG", "CumulativeRY success TempcumuR " + EV_Model.TempcumuF());

            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeR failure");

                CumulativeF(roundflowR, EVflowR);

            }
        });
    }

    public static void CumulativeS(final String roundflowY, final String EVflowY)
    {
        final Get_CumulativeS get_CumulativeS_API = MainActivity.restAdapter.create(Get_CumulativeS.class);

        get_CumulativeS_API.Get_CumulativeS_API( roundflowY, EVflowY, new Callback<Get_CumulativeS_Model>() {
            @Override
            public void success(Get_CumulativeS_Model EV_Model, Response response) {

                MainActivity.Cumulative_Second = EV_Model.TempcumuS();
                Calculate_Time.Get_TimeS();
                Log.e("LOG TAG", "CumulativeRY success TempcumuY " + EV_Model.TempcumuS());

            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeY failure");


                CumulativeS(roundflowY, EVflowY);

            }
        });
    }

    public static void CumulativeFS_dialog(final String roundflowR, final String EVflowR, final String roundflowY, final String EVflowY)
    {
        final Get_CumulativeFS get_CumulativeFS_API = MainActivity.restAdapter.create(Get_CumulativeFS.class);

        get_CumulativeFS_API.Get_CumulativeFS_API( roundflowR, EVflowR, roundflowY, EVflowY, new Callback<Get_CumulativeFS_Model>() {
            @Override
            public void success(Get_CumulativeFS_Model EV_Model, Response response) {

                MainActivity.Cumulative_first_dialog = EV_Model.TempcumuF();
                MainActivity.Cumulative_Second_dialog = EV_Model.TempcumuS();
                Calculate_Time.Get_TimeFS_dialog();
                Log.e("LOG TAG", "CumulativeRY success TempcumuR " + EV_Model.TempcumuF());
                Log.e("LOG TAG", "CumulativeRY success TempcumuY " + EV_Model.TempcumuS());
            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeRY failure");

                CumulativeFS_dialog(roundflowR, EVflowR, roundflowY, EVflowY);

            }
        });
    }

    public static void CumulativeF_dialog(final String roundflowR, final String EVflowR)
    {
        final Get_CumulativeF get_CumulativeF_API = MainActivity.restAdapter.create(Get_CumulativeF.class);

        get_CumulativeF_API.Get_CumulativeF_API(roundflowR, EVflowR, new Callback<Get_CumulativeF_Model>() {
            @Override
            public void success(Get_CumulativeF_Model EV_Model, Response response) {

                MainActivity.Cumulative_first_dialog = EV_Model.TempcumuF();
                Calculate_Time.Get_TimeF_dialog();
                // Log.e("LOG TAG", "CumulativeRY success TempcumuR " + EV_Model.TempcumuF());

            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeR failure");

                CumulativeF_dialog(roundflowR, EVflowR);

            }
        });
    }

    public static void CumulativeS_dialog(final String roundflowY, final String EVflowY)
    {
        final Get_CumulativeS get_CumulativeS_API = MainActivity.restAdapter.create(Get_CumulativeS.class);

        get_CumulativeS_API.Get_CumulativeS_API(roundflowY, EVflowY, new Callback<Get_CumulativeS_Model>() {
            @Override
            public void success(Get_CumulativeS_Model EV_Model, Response response) {

                MainActivity.Cumulative_Second_dialog = EV_Model.TempcumuS();
                Calculate_Time.Get_TimeS_dialog();
                //Log.e("LOG TAG", "CumulativeRY success TempcumuY " + EV_Model.TempcumuS());

            }

            @Override
            public void failure(RetrofitError error) {


                Log.e("LOG TAG", "CumulativeY failure");


                CumulativeS_dialog(roundflowY, EVflowY);

            }
        });
    }

    public static void Get_Cumulative(Boolean Have_First,Boolean Have_Second,String TempDataEV[][],int TempFirst,int TempSecond)
    {
        //Log.e("LOG TAG", "Get_Cumulative => Have_First : "+Have_First+" Have_First : "+Have_Second);
        if (Have_First == true && Have_Second == true)
        {
           //CumulativeFS(TempDataEV[TempFirst][3], TempDataEV[TempFirst][6], TempDataEV[TempSecond][3], TempDataEV[TempSecond][6]);

            MainActivity.Cumulative_first = Double.parseDouble(TempDataEV[TempFirst][9]);
            MainActivity.Cumulative_Second = Double.parseDouble(TempDataEV[TempSecond][9]);
            Calculate_Time.Get_TimeFS();

        }
        else if (Have_First == true && Have_Second == false)
        {
            //CumulativeF(TempDataEV[TempFirst][3], TempDataEV[TempFirst][6]);
            MainActivity.Cumulative_first = Double.parseDouble(TempDataEV[TempFirst][9]);
            Calculate_Time.Get_TimeF();

        }
        else if (Have_First == false && Have_Second == true)
        {
            //CumulativeS(TempDataEV[TempSecond][3], TempDataEV[TempSecond][6]);
            MainActivity.Cumulative_Second = Double.parseDouble(TempDataEV[TempSecond][9]);
            Calculate_Time.Get_TimeS();

        }
        else if (Have_First == false && Have_Second == false)
        {
            // Do some think, if Don't have EV running
        }


        MainActivity.Check_Swap_Function = true;
    }

    public static void Get_Cumulative_dialog(Boolean Have_First,Boolean Have_Second,String TempDataEV[][],int TempFirst,int TempSecond)
    {
        //Log.e("LOG TAG", "Get_Cumulative => Have_First : "+Have_First+" Have_First : "+Have_Second);
        if (Have_First == true && Have_Second == true)
        {
            //CumulativeFS_dialog(TempDataEV[TempFirst][3], TempDataEV[TempFirst][6], TempDataEV[TempSecond][3], TempDataEV[TempSecond][6]);
            MainActivity.Cumulative_first_dialog = Double.parseDouble(TempDataEV[TempFirst][9]);
            MainActivity.Cumulative_Second_dialog = Double.parseDouble(TempDataEV[TempSecond][9]);
            Calculate_Time.Get_TimeFS_dialog();
        }
        else if (Have_First == true && Have_Second == false)
        {
            //CumulativeF_dialog(TempDataEV[TempFirst][3], TempDataEV[TempFirst][6]);
            MainActivity.Cumulative_first_dialog = Double.parseDouble(TempDataEV[TempFirst][9]);
            Calculate_Time.Get_TimeF_dialog();

        }
        else if (Have_First == false && Have_Second == true)
        {
            //CumulativeS_dialog(TempDataEV[TempSecond][3], TempDataEV[TempSecond][6]);
            MainActivity.Cumulative_first_dialog = Double.parseDouble(TempDataEV[TempFirst][9]);
            MainActivity.Cumulative_Second_dialog = Double.parseDouble(TempDataEV[TempSecond][9]);
            Calculate_Time.Get_TimeS_dialog();

        }
        else if (Have_First == false && Have_Second == false)
        {
            // Do some think, if Don't have EV running
        }


        MainActivity.Check_Swap_Function = true;
    }

}
