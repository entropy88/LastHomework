package com.example.entropy.room;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MyAppDatabase myAppDatabase;
    public static EditText edtUserCode;
    public static String stringUserInputCode;
    public static TextView tvProductFound;
  //Strings for the parsed json object values


    Button btnShowDatabase;

    TextView tvProducts;

    Button btnSearchWithinDatabase;

   public static TextView tvliveResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAppDatabase = Room.databaseBuilder
                (MainActivity.this, MyAppDatabase.class, "productsdb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        btnShowDatabase = (Button) findViewById(R.id.show_database);

        edtUserCode=(EditText) findViewById(R.id.edt_code_for_the_query);
//
        tvProducts = (TextView) findViewById(R.id.tv_display_products);

        btnSearchWithinDatabase = (Button) findViewById(R.id.btn_search_product);
        tvProductFound = (TextView) findViewById(R.id.tv_display_query_result);



        btnShowDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Product> products = myAppDatabase.productsDao().getProducts();
                String info = "";
                for (Product prod : products) {

                    String prodCode = prod.getCode();
                    String prodInfo = prod.getInfo();
                    String prodIngredients = prod.getIngredients_text();

                    info = info + "\n" + prodCode + " " + prodInfo + " " + prodIngredients;

                }
                tvProducts.setText(info);

            }
        });
        btnSearchWithinDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringUserInputCode= edtUserCode.getText().toString();
                Product product = myAppDatabase.productsDao().findProductByCode(stringUserInputCode);

                if (product == null) {
                   Toast.makeText(MainActivity.this, "No such product in the db, fetch live data", Toast.LENGTH_LONG).show();
//                    stringUserInputCode= edtUserCode.getText().toString();
                    fetchData process= new fetchData();
                    process.execute();

                } else {
                    Toast.makeText(MainActivity.this, "query successful", Toast.LENGTH_LONG).show();
                    String code = product.getCode();
                    String info = product.getInfo();
                    String ingredients = product.getIngredients_text();
                    String foundWaldo = info + " " + ingredients;

                    tvProductFound.setText(foundWaldo);
                }

            }
        });


    }
}
