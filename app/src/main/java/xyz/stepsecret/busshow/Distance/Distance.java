package xyz.stepsecret.busshow.Distance;

/**
 * Created by XEUSLAB on 31/1/2559.
 */
public class Distance {

    public static Double calculateDistance(double userLat, double userLng,double venueLat, double venueLng)
    {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2.0) * Math.sin(latDistance / 2.0)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2.0) * Math.sin(lngDistance / 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        return 6371000.0 * c;
    }
}
