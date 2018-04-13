package com.example.entropy.room;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data="";
    String productCode="default";
    String productName="default";
    String productIngredients="default";
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

            JSONObject jsonObject=new JSONObject(data);


//            JSONArray jsonArray= new JSONArray(data);
//            for (int i = 0; i <jsonArray.length() ; i++) {
//                JSONObject jsonObject=(JSONObject) jsonArray.get(i) ;
                productCode=jsonObject.getString("code");
                //can't get jerarchy right for these two
                productName=jsonObject.getString("product_name");
                productIngredients=jsonObject.getString("ingredients_text");






        } catch (MalformedURLException e) {
            e.printStackTrace();
            MainActivity.tvliveResponse.setText("Try a different code");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void Void) {
        super.onPostExecute(Void);

        Product product= new Product();
        product.setCode(productCode);
        product.setInfo(productName);
        product.setIngredients_text(productIngredients);
        MainActivity.myAppDatabase.productsDao().addProduct(product);


        MainActivity.tvProductFound.setText(this.productCode+" "+this.productName+" "+this.productIngredients);
    }
}
