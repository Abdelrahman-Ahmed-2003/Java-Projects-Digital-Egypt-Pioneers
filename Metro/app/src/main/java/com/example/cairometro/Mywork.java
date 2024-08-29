package com.example.cairometro;

import static com.example.cairometro.ResultActivity.optimalroute;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class Mywork extends Worker {

    Location loc2, yourlocation;
    MetroGraph met = new MetroGraph();
    Location station = new Location("");
    String nearest = "";
    private FusedLocationProviderClient fusedLocationClient;

    public Mywork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        createNotificationChannel(); // Ensure the notification channel is created
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        getLocationAndNotify();
        return Result.success();
    }

    @SuppressLint("MissingPermission")
    private void getLocationAndNotify() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    yourlocation = location;
                    // Your logic here
                    float mindis = 1000000;
                    int minindex = -1;
                    for (int i = 0; i < optimalroute.size(); ++i) {
                        loc2 = new Location("");
                        loc2.setLatitude(met.lat.get(met.allLines.indexOf(optimalroute.get(i))));
                        loc2.setLongitude(met.lon.get(met.allLines.indexOf(optimalroute.get(i))));
                        if (yourlocation.distanceTo(loc2) / 1000 < mindis) {
                            minindex = i;
                            station = loc2;
                            mindis = yourlocation.distanceTo(loc2) / 1000;
                        }
                    }
                    nearest = optimalroute.get(minindex);
                    sendNotification(nearest);
                } else {
                    //Toast.makeText(getApplicationContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void sendNotification(String s) {
        Notification noti = new NotificationCompat.Builder(getApplicationContext(), "trip_channel")
                .setSmallIcon(R.drawable.metrologo) // Set your notification icon
                .setContentTitle("Metro Trip")
                .setContentText("Your next Station is :" + s)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, noti);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Trip Channel";
            String description = "Channel for trip notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("trip_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
