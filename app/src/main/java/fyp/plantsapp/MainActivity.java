package fyp.plantsapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.firebase.auth.FirebaseAuth;

import fyp.plantsapp.Utilities.Forecast;
import fyp.plantsapp.Utilities.ForecastAdapter;
import fyp.plantsapp.Utilities.RemoteFetch;
import fyp.plantsapp.Utilities.SetListViewHeight;
import fyp.plantsapp.Utilities.fetch_five_day_weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity implements Listener {
    private TextView mTemp, mHumidity, mTempHigh, mTempLow, mName, mWeather;
    TextView mWeatherIcon;
    private ListView mListViewForecast;
    String lat,lng;
    SharedPreferences prefs;
    EasyWayLocation easyWayLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("weather_json"));
        Typeface weatherFont = Typeface.createFromAsset(getAssets(), "fonts/Weather-Fonts.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.sign_out){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,Login.class));
                    finish();
                }
                return true;
            }
        });
        mListViewForecast = (ListView) findViewById(R.id.listView);
        mListViewForecast.setEnabled(false);
        mTemp = (TextView) findViewById(R.id.temp);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mTempHigh = (TextView) findViewById(R.id.tempHigh);
        mTempLow = (TextView) findViewById(R.id.tempLow);
        mName = (TextView) findViewById(R.id.name);
        mWeather = (TextView) findViewById(R.id.weather);
        mWeatherIcon = (TextView) findViewById(R.id.weatherIcon);

        mWeatherIcon.setTypeface(weatherFont);
        mTemp.setTypeface(robotoThin);
        mName.setTypeface(robotoLight);
        mWeather.setTypeface(robotoLight);
        //arrayListForecast = new ArrayList<>();
        easyWayLocation = new EasyWayLocation(this, true,this);
        if(lat!=null&&lng!=null){

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        easyWayLocation.startLocation();
    }




    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String json = intent.getStringExtra("weather_json");
                try {
                    JSONObject obj = new JSONObject(json);
                    JSONArray weather_short_info_array = obj.getJSONArray("weather");
                    mWeather.setText(weather_short_info_array.getJSONObject(0).getString("description"));
                    JSONObject waather_detail_info_obj = obj.getJSONObject("main");
                    mTemp.setText(waather_detail_info_obj.getString("temp") + (char) 0x00B0);
                    mHumidity.setText(waather_detail_info_obj.getString("humidity"));
                    mTempHigh.setText("MAX " + waather_detail_info_obj.getString("temp_max") + (char) 0x00B0);
                    mTempLow.setText("MIN " + waather_detail_info_obj.getString("temp_min") + (char) 0x00B0);
                     setWeatherIcon(weather_short_info_array.getJSONObject(0).getInt("id"),mWeatherIcon);
                    mName.setText(obj.getString("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void setWeatherIcon(int actualId, TextView txt) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            icon = getResources().getString(R.string.weather_sunny);
        } else {
            switch (id) {
                case 1:
                    icon = getResources().getString(R.string.weather_sunny);
                    break;
                case 2:
                    icon = getResources().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getResources().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getResources().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getResources().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getResources().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getResources().getString(R.string.weather_rainy);
                    break;
            }
            txt.setText(icon);
        }
    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        if(location!=null) {
            lat= String.valueOf(location.getLatitude());
            lng=String.valueOf(location.getLongitude());
            new RemoteFetch(MainActivity.this).execute(lat, lng);
            new fetch_five_day_weather(MainActivity.this, mListViewForecast).execute(lat, lng);

        }
    }

    @Override
    public void locationCancelled() {

    }
}
