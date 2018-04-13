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
  //Strings for the parsed json object values


    Button btnShowDatabase;
    //    EditText edtId;

    //the next 3 are temporary
    EditText edtCode;
    EditText edtName;
    EditText edtIngredients;

    TextView tvProducts;
    EditText edtSearchByCode;
    Button btnSearchWithinDatabase;
    TextView tvProductFound;
    Button btnLive;
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
        edtCode = (EditText) findViewById(R.id.edt_product_code);
        edtName = (EditText) findViewById(R.id.edt_product_name);
        edtIngredients = (EditText) findViewById(R.id.edt_product_ingredients);
        tvProducts = (TextView) findViewById(R.id.tv_display_products);
        edtSearchByCode = (EditText) findViewById(R.id.edt_search_by_code);
        btnSearchWithinDatabase = (Button) findViewById(R.id.btn_search_product);
        tvProductFound = (TextView) findViewById(R.id.tv_display_query_result);
        btnLive=(Button)findViewById(R.id.btn_search_live);
        tvliveResponse= (TextView) findViewById(R.id.tv_display_live_result) ;

        btnShowDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //temporary text method to create products. the real deal happens in the fetchData
                String code = edtCode.getText().toString();
                String name = edtName.getText().toString();
                String ingredients = edtIngredients.getText().toString();

                Product product = new Product();

                product.setCode(code);
                product.setInfo(name);
                product.setIngredients_text(ingredients);

                myAppDatabase.productsDao().addProduct(product);
                Toast.makeText(MainActivity.this, "product added", Toast.LENGTH_LONG).show();
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
                String codeQuery = edtSearchByCode.getText().toString();
                Product product = myAppDatabase.productsDao().findProductByCode(codeQuery);

                if (product == null) {
                    Toast.makeText(MainActivity.this, "No such product in the db", Toast.LENGTH_LONG).show();

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

        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringUserInputCode= edtUserCode.getText().toString();
                fetchData process= new fetchData();
                process.execute();
            }
        });
    }
}
