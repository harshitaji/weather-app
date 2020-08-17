package com.example.weather;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SingletonVolley {
    private static SingletonVolley mInstance;
    private RequestQueue mRequstQueue;

    public SingletonVolley(Context context) {
        mRequstQueue= Volley.newRequestQueue(context.getApplicationContext());
    }
    public static  synchronized  SingletonVolley getInstance(Context context){
        if(mInstance==null){
            mInstance= new SingletonVolley(context);
        }
        return mInstance;
    }
    public RequestQueue getmRequstQueue(){
        return mRequstQueue;
    }
}
