package com.example.freight.Freight.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class SchedulerActivity extends AppCompatActivity {

    Spinner spTransportCategory;
    Spinner spCargoType;
    Spinner spAxisNumber;
    Button btnCalculate;
    TextView tvValue;
    EditText etDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        final Schedule schedule = new Schedule();
        final FreightValueCalculator freightValueCalculator = new FreightValueCalculator();

        spTransportCategory = findViewById(R.id.sp_transport_type);
        spCargoType = findViewById(R.id.sp_cargo_type);
        spAxisNumber = findViewById(R.id.sp_axis_number);
        btnCalculate = findViewById(R.id.btn_calculate);
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

                tvValue.setText("R$ " + Math.round(freightValueCalculator.calculateFreightValue(new InputSource(getResources().openRawResource(R.raw.antt_values)), schedule) * 100.0) / 100.0);
            }
        });

    }
}
