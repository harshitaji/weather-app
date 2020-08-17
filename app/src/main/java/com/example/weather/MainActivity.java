package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    TextView textView;
    String main;
    ImageView imageView;
    RequestQueue requestQueue;
    //https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=69eed581e0f40d39ccafd9185caf8fd8
    String baseURL="https://api.openweathermap.org/data/2.5/weather?q=";
    String api="&appid=69eed581e0f40d39ccafd9185caf8fd8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView2);
        imageView=findViewById(R.id.imageView);

        requestQueue=SingletonVolley.getInstance(this).getmRequstQueue();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String city=editText.getText().toString();
             if(city.isEmpty()){
                 editText.setError("Enter city");
             }
             else{
                 final String myURL=baseURL+city+api;
                 JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             String info = response.getString("weather");
                             String value=response.getString("coord");
                             String valu1=response.getString("main");
                             JSONObject obj=new JSONObject(value);
                             JSONObject obj1=new JSONObject(valu1);
                             String temp=obj1.get("temp").toString();
                             String humidity=obj1.get("humidity").toString();
                             String longi=obj.get("lon").toString();
                             String lati=obj.get("lat").toString();

                             Log.i("URL","url"+myURL);


                             JSONArray jsonArray = new JSONArray(info);
                             for (int i = 0; i < jsonArray.length(); i++) {
                                 JSONObject object = jsonArray.getJSONObject(i);
                                 main = object.getString("main");
                                 String discription = object.getString("description");

                                 String work=main+"\n"+"humidity:"+humidity+"\n"+"temperature:"+temp+"\n"+"longitude:"+longi+"\n"+
                                         "latitude:"+lati+"\n"+discription+"\n";
                                 textView.setText(work);
                                 if(main.equals("Clouds")){
                                     imageView.setImageResource(R.drawable.clouds);
                                 }
                                 else if(main.equals("Thunderstorm")){
                                     imageView.setImageResource(R.drawable.thunder);
                                 }
                                 else if(main.equals("Rain")){
                                     imageView.setImageResource(R.drawable.rain);
                                 }
                                 else if(main.equals("haze")){
                                     imageView.setImageResource(R.drawable.haze);
                                 }
                                 else
                                 {
                                     imageView.setImageResource(R.drawable.sun);
                                 }

                             }
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
                 requestQueue.add(jsonObjectRequest);




             }

            }
        });


    }

    }
