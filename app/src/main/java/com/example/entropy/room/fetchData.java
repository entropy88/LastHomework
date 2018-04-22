package com.example.entropy.room;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data="";
    String productCode="default";
    String productName="default";
    String productIngredients="default";
    Product product = new Product();
    Product productCopy= new Product();
    @Override
    protected Void doInBackground(Void... voids) {
        try {
           URL queryUrl= new URL ("https://world.openfoodfacts.org/api/v0/product/"+MainActivity.stringUserInputCode+".json");
//            URL url= new URL ("https://world.openfoodfacts.org/api/v0/product/737628064502.json");
            HttpURLConnection httpURLConnection= (HttpURLConnection) queryUrl.openConnection();
            InputStream inputStream =httpURLConnection.getInputStream();


            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            String line="";
            while (line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }


            Gson gson = new GsonBuilder().setLenient().create();
            product=gson.fromJson(data,Product.class);




        } catch (MalformedURLException e) {
            e.printStackTrace();
            MainActivity.tvliveResponse.setText("Try a different code");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void Void) {
        super.onPostExecute(Void);

//
//        MainActivity.myAppDatabase.productsDao().addProduct(product);


        MainActivity.tvProductFound.setText(product.getCode()+product.getProduct_name()+product.getIngredients_text());
    }
}
