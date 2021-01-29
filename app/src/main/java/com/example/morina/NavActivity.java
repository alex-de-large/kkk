package com.example.morina;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.example.morina.api.Data;
import com.example.morina.api.OnResponseListener;
import com.example.morina.api.Requests;
import com.example.morina.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity implements OnResponseListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageButton button;
    private Data data;
    private ArrayList<Car> cars;
    private Requests requests;

    private final static int REQUEST_CARS = 1;
    private final static int REQUEST_LOGOUT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = Data.get();
        requests = new Requests(this);
        initCars();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        button = findViewById(R.id.nav_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_notification) {
                    startActivity(new Intent(NavActivity.this, NotificationActivity.class));
                    return true;
                } else if (id == R.id.nav_booking) {
                    startActivity(new Intent(NavActivity.this, BookingActivity.class));
                    return true;
                } else if (id == R.id.nav_help_center){
                    startActivity(new Intent(NavActivity.this, HelpCenterActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initCars() {
        cars = new ArrayList<>();
        requests.get("http://cars.areas.su/cars", REQUEST_CARS);
    }

    @Override
    public void onResponse(JSONObject json, int requestsCode) {

    }

    @Override
    public void onResponse(JSONArray json, int requestsCode) {
        if (requestsCode == REQUEST_CARS) {
            try {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsonObject = json.getJSONObject(i);
                    Car car = new Car(
                            jsonObject.getString("id"),
                            jsonObject.getString("model"),
                            jsonObject.getString("lat"),
                            jsonObject.getString("lon")
                    );
                    cars.add(car);
                }
                data.setCars(cars);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error, int requestsCode) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        JSONObject json = new JSONObject();
        try {
            json.put("username", data.getLogin());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requests.post("http://cars.areas.su/logout", json, REQUEST_LOGOUT);
    }
}