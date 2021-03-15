package com.example.sugar_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {

    EditText product_name, bar_code, sugar, Kcal_value;
    Button btn_add;
    DatabaseReference reference;
    Product product;

    boolean found_your_naughty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        bar_code = findViewById(R.id.bar_code_text);
        product_name = findViewById(R.id.product_name_text);
        sugar = findViewById(R.id.sugar_per_100_text);
        btn_add = findViewById(R.id.btn_add);
        Kcal_value = findViewById(R.id.Kcal_per_100_g);

        Intent intent = getIntent();
        String BarCode = intent.getStringExtra("message_key");
        bar_code.setText(BarCode);

        Resources res = getResources();
        String[] profanity_string_array = res.getStringArray(R.array.profanity);

        product = new Product();
        reference = FirebaseDatabase.getInstance().getReference().child("Product");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bar_code_str = bar_code.getText().toString();
                String product_name_str = product_name.getText().toString();
                String sugar_str = sugar.getText().toString();
                String kcal_str = Kcal_value.getText().toString();

                for (int i = 0; i <= profanity_string_array.length - 1; i++) {

                    if (product_name_str.contains(profanity_string_array[i])) {
                        found_your_naughty = true;
                    }
                }

                if(found_your_naughty == false){
                    product.setProductName(product_name_str);
                    product.setBarCode(bar_code_str);
                    product.setSugar(sugar_str);
                    product.setKcal(kcal_str);
                    reference.child(bar_code_str).setValue(product);
                    Toast.makeText(AddProduct.this, "Added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddProduct.this, "Not Added", Toast.LENGTH_LONG).show();
                    found_your_naughty = false;
                }
            }
        });

    }
}