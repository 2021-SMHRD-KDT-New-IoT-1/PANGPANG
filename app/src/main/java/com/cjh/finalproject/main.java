package com.cjh.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class main extends Fragment {

    //가스값을 받아올 변수 gas_value

    private ImageButton btn_led;
    private ImageButton btn_co;

    private TextView tv_led;
    private TextView tv_co;
    private TextView tv_progress;
    private TextView tv_COdt;

    private ImageView img_led;
    private ImageView img_rec;

    private TextView tv_gps;
    private TextView tv_temp;

    private ImageView img_weather;

    private Geocoder geocoder;
    private RequestQueue requestQueue;

    private double lon;
    private double lat;

    private String check = "off";
    private ProgressBar pb;

    private int max; // 프로그래스바 max 값
    private int speed = 15; // 값 올릴수록 Progress 채워지는 속도 느려짐

    private int lv1 = 33, lv2 = 66;
    private int coLv1 = Color.parseColor("#00ff62"), coLv2 = Color.parseColor("#fff700"), coLv3 = Color.parseColor("#ff0400");

    private Drawable bg2;
    private Boolean check_spf;
    SharedPreferences spf;


    final static String ip = "project-student-6.ddns.net/pang_Com";

    // hello world!!

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main2, container, false);

        btn_led = v.findViewById(R.id.btn_led);
        btn_co = v.findViewById(R.id.btn_co);

        tv_led = v.findViewById(R.id.tv_led);
        tv_co = v.findViewById(R.id.tv_co);
        tv_progress = v.findViewById(R.id.tv_progress);
        tv_COdt = v.findViewById(R.id.tv_COdt);

        img_led = v.findViewById(R.id.img_led);
        img_rec = v.findViewById(R.id.img_rec);

        tv_gps = v.findViewById(R.id.tv_gps);
        tv_temp = v.findViewById(R.id.tv_temp);

        img_weather = v.findViewById(R.id.img_weather);

        geocoder = new Geocoder(getContext());

        bg2 = btn_co.getBackground();

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }

        spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);

        check_spf = spf.getBoolean("check",false);

        if (check_spf) {
            bg2.setTint(Color.parseColor("#D50000"));
            img_rec.setImageResource(R.drawable.background_cylinder3);
            tv_co.setText("ON");
            tv_co.setTextColor(Color.parseColor("#D50000"));
            check = "on";
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
                    bg.setTint(Color.parseColor("#f0eb54"));
                    img_led.setImageResource(R.drawable.background_cylinder3);
                    tv_led.setText("ON");
                    tv_led.setTextColor(Color.parseColor("#f0eb54"));
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

        // 일산화탄소 기능 버튼 -----------------------------------------------------------------------
        btn_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_spf) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("일산화탄소 감시");
                    builder.setMessage("일산화탄소 감시를 끄겠습니까?");
                    builder.setPositiveButton("취소", null);
                    builder.setNegativeButton("끄기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check_spf = false;

                            SharedPreferences.Editor edit = spf.edit();
                            edit.putBoolean("check", check_spf);
                            edit.commit();

                            Toast.makeText(getContext(),"일산화탄소 감시 종료",Toast.LENGTH_SHORT).show();

                            bg2.setTint(Color.parseColor("#FFFFFF"));
                            img_rec.setImageResource(R.drawable.background_cylinder2);
                            tv_co.setText("OFF");
                            tv_co.setTextColor(Color.parseColor("#FFFFFF"));

                            Intent intent = new Intent(getContext(),AlarmService.class);
                            getActivity().stopService(intent);
                        }
                    });
                    builder.create().show();
                } else if (!check_spf) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("일산화탄소 감시");
                    builder.setMessage("일산화탄소 감시를 켜시겠습니까?");
                    builder.setPositiveButton("취소", null);
                    builder.setNegativeButton("켜기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check_spf = true;

                            SharedPreferences.Editor edit = spf.edit();
                            edit.putBoolean("check", check_spf);
                            edit.commit();
                            Toast.makeText(getContext(),"일산화탄소 감시 시작",Toast.LENGTH_SHORT).show();

                            bg2.setTint(Color.parseColor("#D50000"));
                            img_rec.setImageResource(R.drawable.background_cylinder3);
                            tv_co.setText("ON");
                            tv_co.setTextColor(Color.parseColor("#D50000"));

                            Intent serviceIntent = new Intent(getContext(), AlarmService.class);
                            getActivity().startService(serviceIntent);
                        }
                    });
                    builder.create().show();
                }

//                if (check == "off") {
//                    bg2.setTint(Color.parseColor("#D50000"));
//                    img_rec.setImageResource(R.drawable.background_cylinder3);
//                    tv_co.setText("ON");
//                    tv_co.setTextColor(Color.parseColor("#D50000"));
//                    check = "on";
//                } else {
//                    bg2.setTint(Color.parseColor("#FFFFFF"));
//                    img_rec.setImageResource(R.drawable.background_cylinder2);
//                    tv_co.setText("OFF");
//                    tv_co.setTextColor(Color.parseColor("#FFFFFF"));
//                    check = "off";
//                }
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

                //~~초 지난 후
                Thread.sleep((1000 * 5));
            } catch (InterruptedException e) {
                Log.v("gas", e.toString());
            }

            // }


        }
    }

    // 일산화탄소 progress 바 초기화 쓰레드 ------------------------------------------------------------
    public class TimeThread extends Thread {
        // extends Thread 했기 때문에~ 지금부터 Thread!

        private int start;

        public TimeThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            while (true) {

                responsePpm();
                try {
                    Thread.sleep((1000 * 5));
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