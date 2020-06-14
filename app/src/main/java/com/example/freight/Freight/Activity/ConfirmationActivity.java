package com.example.freight.Freight.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freight.Freight.Helper.JSONMethods;
import com.example.freight.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmationActivity extends AppCompatActivity {

    Switch aSwitch;
    EditText etDate;
    EditText etTime;
    Intent intent;
    TextView tvName;
    TextView tvCnh;
    TextView tvRg;
    TextView tvCalculatedValue;
    Button btnConfirm;
    String date;
    String time;

    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        intent = getIntent();

        final int driverId = intent.getIntExtra("DriverId", 0);
        String driverName = intent.getStringExtra("Name");
        String cnh = intent.getStringExtra("CNH");
        String rg = intent.getStringExtra("RG");
        final int clientId = intent.getIntExtra("ClientId", 0);
        final double value = intent.getDoubleExtra("Value", 0);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date formattedDate = new Date();

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date formattedTime = new Date();

        aSwitch = findViewById(R.id.sh_select_date);
        etDate = findViewById(R.id.et_date);
        etTime = findViewById(R.id.et_time);
        tvName = findViewById(R.id.tv_name);
        tvCnh = findViewById(R.id.tv_cnh);
        tvRg = findViewById(R.id.tv_rg);
        tvCalculatedValue = findViewById(R.id.tv_calculated_value);
        btnConfirm = findViewById(R.id.btn_confirm);

        tvName.setText(driverName);
        tvCnh.setText(cnh);
        tvRg.setText(rg);
        tvCalculatedValue.setText("R$" + value);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (aSwitch.isChecked() && !etDate.getText().toString().equals("") && !etTime.getText().toString().equals("")) {
            date = etDate.getText().toString();
            time = etTime.getText().toString();
        } else {
            date = dateFormat.format(formattedDate);
            time = timeFormat.format(formattedTime);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    etDate.setVisibility(View.VISIBLE);
                    etTime.setVisibility(View.VISIBLE);
                } else {
                    etDate.setVisibility(View.INVISIBLE);
                    etTime.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream is = getApplicationContext().getAssets().open("database.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            File jsonFile = new File(getApplicationContext().getExternalFilesDir("/assets"), "database.json");

                            JSONObject jsonObject = new JSONObject(JSONMethods.getStringFromFile(jsonFile.toString()));
                            JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                            int id = jsonArray.length() + 1;

                            JSONObject jsonObj = new JSONObject();

                            jsonObj.put("id", id);
                            jsonObj.put("driver", driverId);
                            jsonObj.put("client", clientId);
                            jsonObj.put("scheduled_date", date + " " + time);
                            jsonObj.put("value", value);
                            jsonArray.put(jsonObj);

                            JSONObject currentJsonObject = new JSONObject();
                            currentJsonObject.put("schedule", jsonArray);

                            JSONMethods.writeJsonFile(jsonFile, currentJsonObject.toString());

                            Toast.makeText(ConfirmationActivity.this, "Registro salvo com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ActivityCompat.requestPermissions(ConfirmationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                        }
                    }
                }

                catch (IOException | JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
