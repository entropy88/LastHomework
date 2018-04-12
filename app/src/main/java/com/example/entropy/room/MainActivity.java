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
    MyAppDatabase myAppDatabase;
    Button btnAddProduct;
//    EditText edtId;
    EditText edtCode;
    EditText edtName;
    EditText edtIngredients;
    TextView tvProducts;
    EditText edtSearchByCode;
    Button btnSearchWithinDatabase;
    TextView tvProductFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAppDatabase= Room.databaseBuilder
                (MainActivity.this,MyAppDatabase.class,"productsdb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        btnAddProduct = (Button) findViewById(R.id.btn_add_product);

        edtCode = (EditText) findViewById(R.id.edt_product_code);
        edtName = (EditText) findViewById(R.id.edt_product_name);
        edtIngredients = (EditText) findViewById(R.id.edt_product_ingredients);
        tvProducts=(TextView)findViewById(R.id.tv_display_products);
        edtSearchByCode = (EditText) findViewById(R.id.edt_search_by_code);
        btnSearchWithinDatabase= (Button) findViewById(R.id.btn_search_product);
        tvProductFound=(TextView) findViewById(R.id.tv_display_query_result);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = edtCode.getText().toString();
                String name = edtName.getText().toString();
                String ingredients = edtIngredients.getText().toString();

                Product product= new Product();

                product.setCode(code);
                product.setInfo(name);
                product.setIngredients_text(ingredients);

               myAppDatabase.productsDao().addProduct(product);
                Toast.makeText(MainActivity.this, "product added", Toast.LENGTH_LONG).show();
                List<Product>products=myAppDatabase.productsDao().getProducts();
                String info="";
                for (Product prod:products )
                {
//                    int prodId=prod.getId();
                    String prodCode=prod.getCode();
                    String prodInfo=prod.getInfo();
                    String prodIngredients=prod.getIngredients_text();

                    info=info+"\n"+prodCode+" "+prodInfo+" "+prodIngredients;

                }tvProducts.setText(info);

            }
        });
        btnSearchWithinDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                String codeQuery=edtSearchByCode.getText().toString();
                Product product=myAppDatabase.productsDao().findProductByCode(codeQuery);
              Toast.makeText(MainActivity.this,"query successful",Toast.LENGTH_LONG).show();
//
               String code=product.getCode();
               String info=product.getInfo();
               String ingredients=product.getIngredients_text();
               String foundWaldo=code+" "+info+" "+ingredients;

               tvProductFound.setText(foundWaldo);

            }
        });
    }
}
