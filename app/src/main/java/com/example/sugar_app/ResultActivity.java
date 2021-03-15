package com.example.sugar_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import org.w3c.dom.Text;

import static java.lang.Math.round;

public class ResultActivity extends AppCompatActivity {

    public static TextView BarCodeResult;
    Button AddProduct;
    DatabaseReference reference;
    TextView product_text_str, sugar_text_str, kcal_text_str, result_of;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        BarCodeResult = findViewById(R.id.BarCodeResult);
        AddProduct = findViewById(R.id.AddProduct);
        product_text_str = findViewById(R.id.product_name);
        sugar_text_str = findViewById(R.id.sugar_contain);
        kcal_text_str = findViewById(R.id.Kcal_contain);
        result_of = findViewById(R.id.result_text);

        Intent intent = getIntent();
        final String BarCode = intent.getStringExtra("message_key");
        BarCodeResult.setText("Bar Code: " + BarCode);

        reference = FirebaseDatabase.getInstance().getReference().child("Product").child(BarCode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String bar_code_str = snapshot.child("barCode").getValue().toString();
                    String product_name_str = snapshot.child("productName").getValue().toString();
                    String sugar_str = snapshot.child("sugar").getValue().toString();
                    String kcal_str = snapshot.child("kcal").getValue().toString();

                    product_text_str.setText(product_name_str);
                    sugar_text_str.setText("Sugar: " + sugar_str + "/100g");
                    kcal_text_str.setText("Kcal: " + kcal_str + "/100g");

                    double sugar_int = round(Double.parseDouble(sugar_str));

                    if(sugar_int <= 5){
                        result_of.setTextColor(ContextCompat.getColor(ResultActivity.this, R.color.result_good));
                        result_of.setText("That product is soo healthy!");
                    }
                    if(sugar_int > 6 || sugar_int <= 21){
                        result_of.setTextColor(ContextCompat.getColor(ResultActivity.this, R.color.result_mid));
                        result_of.setText("That product isn't soo healthy at all!");
                    }
                    if(sugar_int > 22){
                        result_of.setTextColor(ContextCompat.getColor(ResultActivity.this, R.color.result_bad));
                        result_of.setText("That product isn' healthy, eat it only occasionally!");
                    }

                } else {
                    AddProduct.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
                intent.putExtra("message_key", BarCode);
                startActivity(intent);
            }
        });


    }
}