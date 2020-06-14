package com.example.freight.Freight.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.freight.Freight.Adapter.PersonListAdapter;
import com.example.freight.Freight.Helper.JSONMethods;
import com.example.freight.Freight.Model.Person;
import com.example.freight.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AvailableDriversActivity extends AppCompatActivity {

    ArrayList<Person> personList;
    Intent intent;
    ListView lvAvailableDrivers;

    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_drivers);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvAvailableDrivers = findViewById(R.id.lv_available_drivers);

        InputStream is = null;
        String json = null;
        personList = new ArrayList<>();
        intent = getIntent();

        String transportType = intent.getStringExtra("TransportCategory");
        String cargoType = intent.getStringExtra("CargoType");
        String axisNumber = intent.getStringExtra("AxisNumber");
        final double value = intent.getDoubleExtra("Value", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    is = getApplicationContext().getAssets().open("database.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");

                    File fileJson = new File(getApplicationContext().getExternalFilesDir(null), "database.json");

                    JSONObject jsonObject = new JSONObject(JSONMethods.getStringFromFile(fileJson.toString()));
                    JSONArray jsonArray = jsonObject.getJSONArray("person");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.getString("type").equals("1")) {
                            JSONArray jsonTruckArray = jsonObject.getJSONArray("truck");

                            for (int j = 0; j < jsonTruckArray.length(); j++) {
                                JSONObject jsonTruckObject = jsonTruckArray.getJSONObject(j);

                                if (jsonTruckObject.getString("transport_type").equals(transportType) &&
                                        jsonTruckObject.getString("cargo_type").equals(cargoType) &&
                                        jsonTruckObject.getString("axis_number").equals(axisNumber)) {

                                    Person person = new Person();
                                    person.setId(Integer.parseInt(jsonObject.getString("id")));
                                    person.setName(jsonObject.getString("name"));
                                    person.setCnh(jsonObject.getString("cnh"));
                                    person.setRg(jsonObject.getString("rg"));
                                    personList.add(person);
                                }
                            }
                        }
                    }

                    lvAvailableDrivers.setAdapter(new PersonListAdapter(this, R.layout.driver_row, personList));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

        lvAvailableDrivers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person p = (Person)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                intent.putExtra("DriverId", p.getId());
                intent.putExtra("Name", p.getName());
                intent.putExtra("CNH", p.getCnh());
                intent.putExtra("RG", p.getRg());
                intent.putExtra("ClientId", 2);
                intent.putExtra("Value", value);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
