package com.cjh.finalproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class InvadeAlarmService extends Service {
    NotificationManager Notifi_M;
    Service2Thread thread;
    Notification.Builder Notifi;
    RequestQueue requestQueue;
    NotificationChannel notificationChannel;

    private int max;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("gas", "onStartCommand 실행");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        thread = new Service2Thread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }


    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {


            String server_url = "http://project-student-6.ddns.net/pang_Com/SeInvadeServlet";

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    server_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.v("Alarm_gas_res", response);

                            try {
                                JSONObject jObject = new JSONObject(response);
                                max = Integer.parseInt(jObject.getString("invade"));

                                Log.v("gas", "invade_max: " + max);

                                if (max == 1) {
                                    Intent intent = new Intent(InvadeAlarmService.this, MainActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(InvadeAlarmService.this, 0, intent, 0);

                                    Notification no;

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        Notifi = new Notification.Builder(getApplicationContext(), "channel_id");
                                    } else {
                                        Notifi = new Notification.Builder(getApplicationContext());
                                    }

                                    no = Notifi.setContentTitle("침입자 감지!!")
                                            .setContentText("침입자가 감지되었습니다!!")
                                            .setSmallIcon(R.drawable.start)
                                            .setTicker("!!!경고!!!")
                                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                            .setAutoCancel(true)
                                            .setVibrate(new long[]{1000, 2000, 1000, 3000, 1000, 4000, 1000, 3000, 1000, 3000})
                                            .setContentIntent(pendingIntent)
                                            .build();


                                    Log.v("gas", "핸들러 실행");
                                    //소리추가
                                    no.defaults = Notification.DEFAULT_SOUND;

                                    //알림 소리를 한번만 내도록
                                    no.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                                    //확인하면 자동으로 알림이 제거 되도록
                                    no.flags = Notification.FLAG_AUTO_CANCEL;


                                    Notifi_M.notify(777, no);

                                    //토스트 띄우기
                                    // Toast.makeText(AlarmService.this, "뜸?", Toast.LENGTH_LONG).show();

                                }


                            } catch (JSONException exp) {

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("main", error.toString());
                        }
                    }
            );

            requestQueue.add(request);

        }
    }

    ;
}