package com.sshahini.constraintsampleweather.views.activities;

import android.location.Location;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sshahini.constraintsampleweather.controllers.LocationController;
import com.sshahini.constraintsampleweather.R;
import com.sshahini.constraintsampleweather.adapters.ForecastsAdapter;
import com.sshahini.constraintsampleweather.api.CurrentWeatherInfoRequest;
import com.sshahini.constraintsampleweather.api.ForecastWeatherDataRequest;
import com.sshahini.constraintsampleweather.events.CityNameChanged;
import com.sshahini.constraintsampleweather.events.CurrentWeatherInfoEvent;
import com.sshahini.constraintsampleweather.events.ForecastWeatherInfoEvent;
import com.sshahini.constraintsampleweather.events.LocationChangedEvent;
import com.sshahini.constraintsampleweather.models.WeatherInfo;
import com.sshahini.constraintsampleweather.utils.WeatherIconParser;
import com.sshahini.constraintsampleweather.views.dialogs.LocationChangerDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    // Primary views
    ///////////////////////////////////////////////////////////////////////////
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    // Weather info related fields
    ///////////////////////////////////////////////////////////////////////////
    private TextView txtHumidity;
    private TextView txtMinMaxTemp;
    private TextView txtWindSpeed;
    private TextView txtCurrentTemp;
    private TextView txtCity;
    private TextView txtDescription;
    private ImageView currentWeatherIconImageView;
    private WeatherIconParser weatherIconParser;

    // Forecasts weather data
    ///////////////////////////////////////////////////////////////////////////
    private RecyclerView forecastsRecyclerView;
    private List<WeatherInfo> forecasts = new ArrayList<>();
    private ForecastsAdapter forecastsAdapter;

    private static final String STRING_DEFAULT_CITY="NewYork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIconParser = new WeatherIconParser(this);
        initViews();
        if (getCurrentLocation() != null) {
            getWeather(getCurrentLocation());
            getForecastData(getCurrentLocation());
        }else {
            getWeather(STRING_DEFAULT_CITY);
            getForecastData(STRING_DEFAULT_CITY);
        }
    }

    private void initViews() {
        initToolbar();
        initNavigationView();
        initList();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        txtHumidity = (TextView) findViewById(R.id.textview_humidity);
        txtMinMaxTemp = (TextView) findViewById(R.id.textview_min_max_temp);
        txtWindSpeed = (TextView) findViewById(R.id.textview_wind_speed);
        txtCurrentTemp = (TextView) findViewById(R.id.textview_current_temp);
        txtCity = (TextView) findViewById(R.id.textview_city);
        txtDescription = (TextView) findViewById(R.id.current_temp_desc);
        currentWeatherIconImageView = (ImageView) findViewById(R.id.current_temp_icon);
        FloatingActionButton btnChangeCityName = (FloatingActionButton) findViewById(R.id.change_city_name);

        btnChangeCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LocationChangerDialog().show(getSupportFragmentManager(), null);
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);
    }

    private void initNavigationView() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initList() {

        forecastsRecyclerView = (RecyclerView) findViewById(R.id.forecasts_list);
        forecastsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        forecastsAdapter = new ForecastsAdapter(this, forecasts);
        forecastsRecyclerView.setAdapter(forecastsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Location getCurrentLocation() {
        LocationController locationController = new LocationController(this);
        return locationController.getCurrentLocation();
    }

    private void getWeather(Location location) {
        if (location != null) {
            getWeather(location.getLatitude(), location.getLongitude());
        } else {
            Log.w(TAG, "getWeather: Location is Null!!!");
        }
    }

    private void getWeather(String cityName) {
        progressBar.setVisibility(View.VISIBLE);
        new CurrentWeatherInfoRequest(this).sendRequest(cityName);
    }

    private void getWeather(final double latitude, final double longitude) {
        progressBar.setVisibility(View.VISIBLE);
        new CurrentWeatherInfoRequest(this).sendRequest(latitude, longitude);
    }

    private void getForecastData(Location location) {
        if (location != null) {
            getForecastData(location.getLatitude(), location.getLongitude());
        }
    }

    private void getForecastData(final double latitude, final double longitude) {
        progressBar.setVisibility(View.VISIBLE);
        new ForecastWeatherDataRequest(this).sendRequest(latitude, longitude);
    }

    private void getForecastData(String cityName) {
        progressBar.setVisibility(View.VISIBLE);
        new ForecastWeatherDataRequest(this).sendRequest(cityName);
    }

    private void showErrorMessage(View.OnClickListener onClickListener) {
        Snackbar.make(coordinatorLayout, "Something wrong!!!", Snackbar.LENGTH_LONG).setAction("Retry", onClickListener).setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary)).show();
    }

    // Subscribe On Events
    ///////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCurrentWeatherInfoEvent(CurrentWeatherInfoEvent currentWeatherInfoEvent) {
        progressBar.setVisibility(View.GONE);
        if (currentWeatherInfoEvent.getException() == null) {
            WeatherInfo weatherInfo = currentWeatherInfoEvent.getWeatherInfo();
            txtHumidity.setText(weatherInfo.getHumidityUiText());
            txtMinMaxTemp.setText(weatherInfo.getMinMaxTempUiText());
            txtWindSpeed.setText(weatherInfo.getWindSpeedUiText());
            txtCurrentTemp.setText(weatherInfo.getCurrentTempUiText());
            txtCity.setText(weatherInfo.getCity());
            txtDescription.setText(weatherInfo.getDescription());
            VectorDrawableCompat vectorDrawableCompat = weatherIconParser.getIcon(weatherInfo.getId());
            vectorDrawableCompat.setTint(ContextCompat.getColor(this, android.R.color.white));
            currentWeatherIconImageView.setImageDrawable(vectorDrawableCompat);
        } else {
            showErrorMessage(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getCurrentLocation() != null) {
                        getWeather(getCurrentLocation());

                    }
                }
            });
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onForecastWeatherInfoEvent(ForecastWeatherInfoEvent forecastWeatherInfoEvent) {
        progressBar.setVisibility(View.GONE);
        if (forecastWeatherInfoEvent.getException() == null) {
            forecastsAdapter = new ForecastsAdapter(this, forecastWeatherInfoEvent.getWeatherInfo());
            forecastsRecyclerView.setAdapter(forecastsAdapter);
        } else {
            showErrorMessage(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getCurrentLocation() != null) {
                        getForecastData(getCurrentLocation());
                    }
                }
            });
        }
    }
    @SuppressWarnings("unused")
    @Subscribe
    public void onLocationChangedEvent(LocationChangedEvent locationChangedEvent) {
        getWeather(locationChangedEvent.getLatitude(), locationChangedEvent.getLongitude());
        getForecastData(locationChangedEvent.getLatitude(), locationChangedEvent.getLongitude());
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onCityNameChangedEvent(CityNameChanged cityNameChanged) {
        getWeather(cityNameChanged.getNewCityName());
        getForecastData(cityNameChanged.getNewCityName());
    }


}
