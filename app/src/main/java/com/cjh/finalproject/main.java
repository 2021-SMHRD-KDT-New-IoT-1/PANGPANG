package com.cjh.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;


public class main extends Fragment {

    //가스값을 받아올 변수 gas_value
    private int gas;

    private ImageButton btn_led;
    private ImageButton btn_rec;

    private TextView tv_led;
    private TextView tv_rec;
    private TextView tv_progress;
    private TextView tv_COdt;

    private ImageView img_led;
    private ImageView img_rec;

    private TextView tv_gps;
    private TextView tv_temp;

    private ImageView img_weather;

    private Geocoder geocoder;
    private RequestQueue requestQueue;

    double lon;
    double lat;

    String check = "off";
    ProgressBar pb;
    int max; // 프로그래스바 max 값
    int speed = 15; // 값 올릴수록 Progress 채워지는 속도 느려짐

    int lv1 = 33, lv2 = 66;
    int coLv1 = Color.parseColor("#00ff62"), coLv2 = Color.parseColor("#fff700"), coLv3 = Color.parseColor("#ff0400");

    final static String ip = "project-student-6.ddns.net/pang_Com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        btn_led = v.findViewById(R.id.btn_led);
        btn_rec = v.findViewById(R.id.btn_rec);

        tv_led = v.findViewById(R.id.tv_led);
        tv_rec = v.findViewById(R.id.tv_rec);
        tv_progress = v.findViewById(R.id.tv_progress);
        tv_COdt = v.findViewById(R.id.tv_COdt);

        img_led = v.findViewById(R.id.img_led);
        img_rec = v.findViewById(R.id.img_rec);

        tv_gps = v.findViewById(R.id.tv_gps);
        tv_temp = v.findViewById(R.id.tv_temp);

        img_weather = v.findViewById(R.id.img_weather);

        geocoder = new Geocoder(getContext());

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }

        // GPS 기능 ---------------------------------------------------------------------------------
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            Location location = lm.getLastKnownLocation(locationProvider);
 //         String provider = location.getProvider();
            lon = location.getLongitude();
            lat = location.getLatitude();
            // double altitude = location.getAltitude(); 해수면 코드(무쓸모)

            try {
                List<Address> citylist = geocoder.getFromLocation(lat, lon, 10);
                if (citylist != null) {
                    if (citylist.size() == 0) {
                        Log.e("reverseGeocoding", "해당 도시 없음");
                    } else {
                        String subLocality = citylist.get(0).getSubLocality();
                        String thoroughfare = citylist.get(0).getThoroughfare();
                        tv_gps.setText(subLocality + " " + thoroughfare);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);

        // 날씨 기능 ----------------------------------------------------------------------------
            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=6aee37853bd0ce95c4064a9a2184045d";

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("테스트", "119 + " + response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject main = jsonObject.getJSONObject("main");
                                JSONArray weather = jsonObject.getJSONArray("weather");

                                Log.d("테스트", "127 + " + String.valueOf(main));

                                Log.d("테스트", "129 + " + String.valueOf(main));

                                String s_temp = (main.getString("temp"));
                                String s_temp_min = main.getString("temp_min");
                                String s_temp_max = main.getString("temp_max");

                                String temp = s_temp.substring(0, s_temp.length() - 3);

                                Log.d("테스트", "134 + " + temp);

                                Log.d("테스트", "136 + " + String.valueOf(lat));
                                Log.d("테스트", "137 + " + String.valueOf(lon));

                                String imgSource = weather.getJSONObject(0).getString("icon");

                                tv_temp.setText(temp + "°C");

                                if (imgSource.equals("01d")) {
                                    img_weather.setImageResource(R.drawable.wea_sunny);
                                } else if (imgSource.equals("02d")) {
                                    img_weather.setImageResource(R.drawable.wea_few_clouds);
                                } else if (imgSource.equals("03d")) {
                                    img_weather.setImageResource(R.drawable.wea_clouds);
                                } else if (imgSource.equals("04d")) {
                                    img_weather.setImageResource(R.drawable.wea_broken_clouds);
                                } else if (imgSource.equals("09d")) {
                                    img_weather.setImageResource(R.drawable.wea_shower_rain);
                                } else if (imgSource.equals("10d")) {
                                    img_weather.setImageResource(R.drawable.wea_rain);
                                } else if (imgSource.equals("11d")) {
                                    img_weather.setImageResource(R.drawable.wea_thunder);
                                } else if (imgSource.equals("13d")) {
                                    img_weather.setImageResource(R.drawable.wea_snowflake);
                                } else if (imgSource.equals("50d")) {
                                    img_weather.setImageResource(R.drawable.wea_mist);
                                }
//                                imgUrl = "http://openweathermap.org/img/wn/"+ imgSource + "@2x.png";
//
//                                Glide.with(getContext()).load(imgUrl).into(img_weather);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(request);
        }

        // Prograss바 작동 --------------------------------------------------------------------------
        pb = (ProgressBar) v.findViewById(R.id.progress_bar);
        pb.setProgress(max);

        TimeThread Thread = new TimeThread(3);
        Thread.start();

        // LED기능 버튼 -----------------------------------------------------------------------------
        btn_led.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg = btn_led.getBackground();
                if (check.equals("off")) {
                    bg.setTint(Color.parseColor("#FBC02D"));
                    img_led.setImageResource(R.drawable.background_cylinder3);
                    tv_led.setText("ON");
                    tv_led.setTextColor(Color.parseColor("#FBC02D"));
                    check = "on";

                } else {
                    bg.setTint(Color.parseColor("#FFFFFF"));
                    img_led.setImageResource(R.drawable.background_cylinder2);
                    tv_led.setText("OFF");
                    tv_led.setTextColor(Color.parseColor("#FFFFFF"));
                    check = "off";
                }
            }
        });

        // 녹화기능 버튼 -----------------------------------------------------------------------------
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable bg2 = btn_rec.getBackground();
                if (check == "off") {
                    bg2.setTint(Color.parseColor("#D50000"));
                    img_rec.setImageResource(R.drawable.background_cylinder3);
                    tv_rec.setText("ON");
                    tv_rec.setTextColor(Color.parseColor("#D50000"));
                    check = "on";
                } else {
                    bg2.setTint(Color.parseColor("#FFFFFF"));
                    img_rec.setImageResource(R.drawable.background_cylinder2);
                    tv_rec.setText("OFF");
                    tv_rec.setTextColor(Color.parseColor("#FFFFFF"));
                    check = "off";
                }
            }
        });

        return v;
    }

    // PrograssThread 전용 Handler ------------------------------------------------------------------
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            pb.setProgress(msg.arg1);

            if (msg.arg1 < lv1) {
                pb.getProgressDrawable().setColorFilter(coLv1, PorterDuff.Mode.ADD);
                tv_progress.setText("안전");
            } else if (msg.arg1 < lv2) {
                //조건식을 일산화탄소 수치화로해서 gas_value>??
                pb.getProgressDrawable().setColorFilter(coLv2, PorterDuff.Mode.ADD);
                tv_progress.setText("주의");
            } else {
                pb.getProgressDrawable().setColorFilter(coLv3, PorterDuff.Mode.ADD);
                tv_progress.setText("위험");
            }
            tv_COdt.setText(max + "ppm");
        }
    };

    // Prograss바 전용 Thread -----------------------------------------------------------------------
    class PrograssThread extends Thread {
        private int max;

        PrograssThread(int max) {
            this.max = max;
        }

        @Override
        public void run() {

            // while (true) {
                
                try {

                    int loop = 0;
                    if (max < 50) {
                        loop = 33;
                    } else if (max < 100) {
                        loop = 66;
                    } else {
                        loop = 100;
                    }

                    Log.v("error", String.valueOf(max));
                    for (int i = 0; i < loop; i++) {
                        Message msg = new Message();
                        msg.arg1 = i;

                        Thread.sleep(speed);
                        handler.sendMessage(msg);
                    }

                    //30초 지난 후
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    Log.v("gas", e.toString());
                }

            // }


        }
    }

    public class TimeThread extends Thread {
        // extends Thread 했기 때문에~ 지금부터 Thread!

        private int start; // 해당 Thread가 몇초부터 시작할 건지 전달받을거임~!

        public TimeThread(int start) {
            this.start = start;
        }

        // Thread가 시작하면 실행되는 메소드
        @Override
        public void run() {
            // 30부터 0까지 1씩 감소해야함!
            // 뭘 써야할까? for문!
            while (true) {

                responsePpm();
                // Thread 쉬게하는 법~~
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // GPS 하부 기능 --------------------------------------------------------------------------------
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();
            lon = location.getLongitude();
            lat = location.getLatitude();
            double altitude = location.getAltitude();
            try {

                List<Address> citylist = geocoder.getFromLocation(lat, lon, 10);
                if (citylist != null) {
                    if (citylist.size() == 0) {
                        Log.e("reverseGeocoding", "해당 도시 없음");
                    } else {
                        String subLocality = citylist.get(0).getSubLocality();
                        String thoroughfare = citylist.get(0).getThoroughfare();
                        tv_gps.setText(subLocality + " " + thoroughfare);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    // ppm 메소드
    public void responsePpm() {
        String server_url = "http://" + main.ip + "/GasServlet";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.v("main", response);
                        try {
                            JSONObject jObject = new JSONObject(response);
                            max = Integer.parseInt(jObject.getString("gas"));
                            Log.v("gas", Integer.toString(gas));

                            Log.v("gas", "max: " + max);

                            new PrograssThread(max).start();
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

        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}