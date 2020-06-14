package com.example.freight.Freight.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.freight.Freight.Helper.FreightValueCalculator;
import com.example.freight.Freight.Model.Schedule;
import com.example.freight.R;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SchedulerActivity extends AppCompatActivity {

    Spinner spTransportCategory;
    Spinner spCargoType;
    Spinner spAxisNumber;
    Button btnCalculate;
    Button btnSearchDrivers;
    TextView tvValue;
    EditText etDistance;
    Schedule schedule;
    FreightValueCalculator freightValueCalculator;
    double value;

    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        File databaseFile = new File(getExternalFilesDir(null), "database.json");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (!databaseFile.exists()) {
                    copyAssets();
                }
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

        schedule = new Schedule();
        freightValueCalculator = new FreightValueCalculator();

        spTransportCategory = findViewById(R.id.sp_transport_type);
        spCargoType = findViewById(R.id.sp_cargo_type);
        spAxisNumber = findViewById(R.id.sp_axis_number);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnSearchDrivers = findViewById(R.id.btn_search_drivers);
        tvValue = findViewById(R.id.tv_value);
        etDistance = findViewById(R.id.et_distance);

        ArrayAdapter<CharSequence> transportCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.transport_category_text, android.R.layout.simple_spinner_item);
        transportCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransportCategory.setAdapter(transportCategoryAdapter);

        ArrayAdapter<CharSequence> cargoTypeAdapter = ArrayAdapter.createFromResource(this, R.array.cargo_type_text, android.R.layout.simple_spinner_item);
        cargoTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCargoType.setAdapter(cargoTypeAdapter);

        ArrayAdapter<CharSequence> axisNumberAdapter = ArrayAdapter.createFromResource(this, R.array.axis_number_text, android.R.layout.simple_spinner_item);
        axisNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAxisNumber.setAdapter(axisNumberAdapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String transportCategoryValue = getResources().getStringArray(R.array.transport_category_code)[spTransportCategory.getSelectedItemPosition()];
                String cargoTypeValue = getResources().getStringArray(R.array.cargo_type_code)[spCargoType.getSelectedItemPosition()];
                String axisNumberValue = getResources().getStringArray(R.array.axis_number_code)[spAxisNumber.getSelectedItemPosition()];
                String distance = etDistance.getText().toString();

                schedule.setTransportCategory(Integer.parseInt(transportCategoryValue));
                schedule.setCargoType(Integer.parseInt(cargoTypeValue));
                schedule.setAxisNumber(Integer.parseInt(axisNumberValue));
                schedule.setDistance(Float.parseFloat(distance));

                value = Math.round(freightValueCalculator.calculateFreightValue(new InputSource(getResources().openRawResource(R.raw.antt_values)), schedule) * 100.0) / 100.0;

                tvValue.setText("R$ " + value);

                btnSearchDrivers.setVisibility(View.VISIBLE);
            }
        });

        btnSearchDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AvailableDriversActivity.class);
                i.putExtra("TransportCategory", String.valueOf(schedule.getTransportCategory()));
                i.putExtra("CargoType", String.valueOf(schedule.getCargoType()));
                i.putExtra("AxisNumber", String.valueOf(schedule.getAxisNumber()));
                i.putExtra("Value", value);
                startActivity(i);
            }
        });

    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(getExternalFilesDir(null), filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
