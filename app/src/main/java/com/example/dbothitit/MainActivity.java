package com.example.dbothitit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnNgaytieptheo;
    TextView txtTenTP, txtQuocgia, txtTemp, txtStatus, txtDoam, txtMay, txtGio, txtDay;
    ImageView txtImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                GetCurrentWeatherData(city);
            }
        });
    }



    private void Anhxa() {
        edtSearch = (EditText)findViewById(R.id.edtSearch);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnNgaytieptheo = (Button)findViewById(R.id.btnNgaytieptheo);
        txtTenTP = (TextView) findViewById(R.id.txtviewThanhPho);
        txtQuocgia = (TextView) findViewById(R.id.txtviewTenQuocGia);
        txtDay = (TextView) findViewById(R.id.txtviewCapNhat);
        txtStatus = (TextView) findViewById(R.id.txtviewTrangthai);
        txtDoam = (TextView) findViewById(R.id.txtviewHumidity);
        txtGio = (TextView) findViewById(R.id.txtviewGio);
        txtMay = (TextView) findViewById(R.id.txtviewMay);
        txtTemp =(TextView)findViewById(R.id.txtviewNhietdo);
        txtImageview = (ImageView)findViewById(R.id.imageViewMain);

    }

    public  void GetCurrentWeatherData(final String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/find?q="+data+"&units=metric&appid=6010f3bb28f6135ab474241c6ad6557a";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                           // String dayx = jsonObject.getString("dt");
//                            String name = jsonObject.getString("name");
//                            txtTenTP.setText("Tên Thành Phố: " + name);

//                            long l = Long.valueOf(dayx);
//                            Date date = new Date(l*1000L);
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
//                            String Day = simpleDateFormat.format(date);
//
//                            txtDay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("list");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String Tenthanhpho = jsonObjectWeather.getString("name");
                            txtTenTP.setText(Tenthanhpho);

                            //Truy cap vao mang
                            JSONObject jsonArrayMain = jsonObjectWeather.getJSONObject("main");
                            String nhietdo = jsonArrayMain.getString("temp");
                          //  Log.d("Ten", nhietdo);
                            txtTemp.setText(nhietdo +"°C");
                            String doam = jsonArrayMain.getString("humidity");
                            txtDoam.setText(doam +"%");
                            String day = jsonObjectWeather.getString("dt");
                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            //Get thong tin Gio
                            JSONObject jsonArrayGio = jsonObjectWeather.getJSONObject("wind");
                            String Gio = jsonArrayGio.getString("speed");
                            txtGio.setText(Gio+"m/s");

                            //Get Thong tin May
                            JSONObject jsonObjectMay = jsonObjectWeather.getJSONObject("clouds");
                            String May = jsonObjectMay.getString("all");
                            txtMay.setText(May+"%");

                            //Get thong tin trang thai hien tai
                            JSONArray jsonArrayStatus = jsonObjectWeather.getJSONArray("weather");
                            JSONObject jsonObjectStatus = jsonArrayStatus.getJSONObject(0);
                            String Status = jsonObjectStatus.getString("main");
                            txtStatus.setText(Status);
                            //Get Anh ICon
                            String Icon = jsonObjectStatus.getString("icon");
                            Log.d("Ten", Icon);
                            Picasso.get().load("https://openweathermap.org/img/w/"+Icon+".png").into(txtImageview);
                            //Get quoc gia
                            JSONObject jsonObjectQuocgia = jsonObjectWeather.getJSONObject("sys");
                            String Quocgia = jsonObjectQuocgia.getString("country");
                            txtQuocgia.setText(Quocgia);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
