package com.rcoem.iotproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rcoem.iotproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Integer ldr_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));
/*
*   0-200 very cloudy,rainy,cool
*   200-400 overcast,cool
*   400-600 clear moderate-sunny,little-warm
*   600-800 clear bright-sunny,moderate-warm
*   800-1024 claer very-sunny,very-warm
*
* */


        FirebaseDatabase.getInstance().getReference("WeatherData").child("LDR_Value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ldr_val_snap) {
                if(ldr_val_snap.exists()){

                    ldr_val = ldr_val_snap.getValue(Integer.class);

                    if(0<=ldr_val && ldr_val<200){
                        binding.ldrVal.setText(ldr_val.toString());
                        binding.weatherCon.setText("Cloudy | May Rain");
                        binding.tempEst.setText("Cool");
                        binding.weatherImage.setImageDrawable(getDrawable(R.drawable.may_rain));

                    }else if(200<=ldr_val && ldr_val<400){
                        binding.ldrVal.setText(ldr_val.toString());
                        binding.weatherCon.setText("Overcast | Cloudy");
                        binding.tempEst.setText("Cool-Warm");
                        binding.weatherImage.setImageDrawable(getDrawable(R.drawable.overcast_complete));
                    } else if(400<=ldr_val && ldr_val<600){

                        binding.ldrVal.setText(ldr_val.toString());
                        binding.weatherCon.setText("Quiet Sunny | Clear");
                        binding.tempEst.setText("Little Warm");
                        binding.weatherImage.setImageDrawable(getDrawable(R.drawable.overcast));
                    }else if(600<=ldr_val && ldr_val<800){

                        binding.ldrVal.setText(ldr_val.toString());
                        binding.weatherCon.setText("Sunny | Clear");
                        binding.tempEst.setText("Little Warm");
                        binding.weatherImage.setImageDrawable(getDrawable(R.drawable.sunny));
                    }else{
                        binding.ldrVal.setText(ldr_val.toString());
                        binding.weatherCon.setText("Sunny(Hot) | Clear");
                        binding.tempEst.setText("Warm");
                        binding.weatherImage.setImageDrawable(getDrawable(R.drawable.very_sunny));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DescriptionActivity.class));
            }
        });

    }
}