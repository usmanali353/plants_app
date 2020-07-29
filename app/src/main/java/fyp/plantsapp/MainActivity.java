package fyp.plantsapp;
import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;
import fyp.plantsapp.Utilities.Forecast;
import fyp.plantsapp.Utilities.ForecastAdapter;
import fyp.plantsapp.Utilities.SetListViewHeight;


public class MainActivity extends AppCompatActivity implements Listener {
    private TextView mTemp, mHumidity, mTempHigh, mTempLow, mName, mWeather;
    TextView mWeatherIcon;
    private ListView mListViewForecast;
    SharedPreferences prefs;
    EasyWayLocation easyWayLocation;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView nv;
    user userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Typeface weatherFont = Typeface.createFromAsset(getAssets(), "fonts/Weather-Fonts.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather");
        userInfo=new Gson().fromJson(prefs.getString("user_info",null),user.class);
        mListViewForecast =  findViewById(R.id.listView);
        mTemp = findViewById(R.id.temp);
        nv=findViewById(R.id.nav_view);
        mHumidity = findViewById(R.id.humidity);
        mTempHigh =  findViewById(R.id.tempHigh);
        mTempLow =  findViewById(R.id.tempLow);
        mName =  findViewById(R.id.name);
        mWeather = findViewById(R.id.weather);
        mWeatherIcon =  findViewById(R.id.weatherIcon);
        mWeatherIcon.setTypeface(weatherFont);
        mTemp.setTypeface(robotoThin);
        mName.setTypeface(robotoLight);
        mWeather.setTypeface(robotoLight);
        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.farming_procedures){
                    startActivity(new Intent(MainActivity.this,wheat_farming_procedures.class));
                }else if(item.getItemId()==R.id.crop_videos){
                    startActivity(new Intent(MainActivity.this,crop_Videos.class));
                }else if(item.getItemId()==R.id.help){
                    startActivity(new Intent(MainActivity.this,chatbot.class).putExtra("url","https://console.dialogflow.com/api-client/demo/embedded/1ab8276d-12d3-4c9c-8cc0-e176bab1c385"));
                }else if(item.getItemId()==R.id.sign_out){
                    FirebaseAuth.getInstance().signOut();
                    prefs.edit().remove("user_info").apply();
                    startActivity(new Intent(MainActivity.this,Login.class));
                    finish();
                }else if(item.getItemId()==R.id.notification){
                    startActivity(new Intent(MainActivity.this,notifications_page.class));
                }else if(item.getItemId()==R.id.profile){
                    edit_profile();
                }else if(item.getItemId()==R.id.insecticide){
                   // startActivity(new Intent(MainActivity.this,chatbot.class).putExtra("url",""));
                    startActivity(new Intent(MainActivity.this,diseases_list.class));
                }
                return true;
            }
        });
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        easyWayLocation = new EasyWayLocation(this,request, false, this);
        //arrayListForecast = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                easyWayLocation.startLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        easyWayLocation.endUpdates();
        super.onDestroy();
    }

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
    public void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();
    }
    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        if (location != null) {
           try{
               List<Address> addresses=new Geocoder(MainActivity.this).getFromLocation(location.getLatitude(),location.getLongitude(),1);
               mName.setText(addresses.get(0).getLocality());
               new fetch_weather().execute(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    @Override
    public void locationCancelled() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    easyWayLocation.startLocation();
//                            new RemoteFetch(this).execute(lat,lng);
//                            new fetch_five_day_weather(this,mListViewForecast).execute(lat,lng);
                    } else {
                        Toast.makeText(MainActivity.this, "We need acess to location to view Weather", Toast.LENGTH_LONG).show();
                        // Explain to the user that the feature is unavailable because
                        // the features requires a permission that the user has denied.
                        // At the same time, respect the user's decision. Don't link to
                        // system settings in an effort to convince the user to change
                        // their decision.
                    }
        }
    }

    //One Call Weather Api
    class fetch_weather extends AsyncTask<String,Void,String>{
    ProgressDialog pd;
     StringBuilder json;
     ArrayList<Forecast> forecasts;
     boolean showNotification;
        public fetch_weather() {
            this.pd = new ProgressDialog(MainActivity.this);
            this.pd.setMessage("Fetching Weather");
            json=new StringBuilder();
            forecasts=new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!pd.isShowing()){
                pd.show();
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url=new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+strings[0]+"&"+"lon="+strings[1]+"&"+"appid=d9162a43685ed20605c4736f1a7a01c1"+"&"+"exclude=hourly,minutely,current"+"&"+"units=metric");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);connection.setDoOutput(true);
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line=reader.readLine())!=null){
                    json.append(line);
                }
             return json.toString();
            }catch(Exception e){
                e.printStackTrace();
            }

            return json.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            easyWayLocation.endUpdates();
            if (s != null) {
                try {
                    JSONObject weatherObj = new JSONObject(s);
                    JSONArray sevenDayWeatherArray = weatherObj.getJSONArray("daily");
                    mHumidity.setText(String.valueOf(sevenDayWeatherArray.getJSONObject(0).get("humidity")));
                    mTemp.setText(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("day")) + (char) 0x00B0);
                    mTempLow.setText("MIN: " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("min")) + (char) 0x00B0);
                    mTempHigh.setText("MAX: " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("max")) + (char) 0x00B0);
                    mWeather.setText(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")));
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

                    String formattedDate = df.format(c);
                    if(prefs.getString("current_date",null)==null||!prefs.getString("current_date",null).equals(formattedDate)) {
                        if(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Rain")||String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Clouds")&&userInfo.getCropCurrentStage().equals("Harvesting")){
                            create_notification("Today Temperature is " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("day")) + (char) 0x00B0+" and  "+String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).toLowerCase(), "There are Chances of Rain not a better time for Harvesting");
                        }else if(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Rain")||String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Clouds")&&userInfo.getCropCurrentStage().equals("Sowing Seeds")){
                            create_notification("Today Temperature is " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("day")) + (char) 0x00B0+" and  "+String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).toLowerCase(), "There are Chances of Rain ");
                        }else if(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Rain")||String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).contains("Clouds")&&userInfo.getCropCurrentStage().equals("Irrigation")){
                            create_notification("Today Temperature is " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("day")) + (char) 0x00B0+" and  "+String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).toLowerCase(), "There are Chances of Rain rain will provide necessery moisture to Crops");
                        }else {
                            create_notification("Today Temperature is " + String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONObject("temp").get("day")) + (char) 0x00B0+" and  "+String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description")).toLowerCase(), "Act According to Weather");
                        }
                        if(userInfo.getCropCurrentStage().equals("Preparation of Land")){
                             create_notification_for_procedures("Your Crop is on Preparation of Land Stage","Land Preparation Tips","F0VbCrUxLMk");
                        }else if(userInfo.getCropCurrentStage().equals("Sowing of Seeds")){
                            create_notification_for_procedures("Your Crop is on Sowing of Seed Stage","comparison between Drill and Broadcast Sowing Method in Wheat","gVqwCICv-1Q");
                        }else if(userInfo.getCropCurrentStage().equals("Irrigation")){
                            create_notification_for_procedures("Your Crop is Irrigation Stage","Tips on Irrigation","wJyLrxLCJ6U");
                        }else if(userInfo.getCropCurrentStage().equals("Adding Fertilizers")){
                            create_notification_for_procedures("Your Crop is Adding Fertilizers Stage","Tips on Using Fertilizers in Wheat Crop","bh1QzRf2wfQ");
                        }else if(userInfo.getCropCurrentStage().equals("Removal of Weeds")){
                            create_notification_for_procedures("Your Crop is on Removing Stage","Removing Weed Tips","JnxXX2cjqVo");
                        }else if(userInfo.getCropCurrentStage().equals("Harvesting")){
                            create_notification_for_procedures("Your Crop is on Harvesting Stage","Harvesting Info","CkKdOnaRQn0");
                        }
                    }
                    setWeatherIcon(Integer.parseInt(String.valueOf(sevenDayWeatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("id"))), mWeatherIcon);
                    for (int i = 1; i < sevenDayWeatherArray.length(); i++) {
                        forecasts.add(new Forecast(String.valueOf(sevenDayWeatherArray.getJSONObject(i).getJSONObject("temp").get("max")), String.valueOf(sevenDayWeatherArray.getJSONObject(i).getJSONObject("temp").get("min")), String.valueOf(sevenDayWeatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("description")), String.valueOf(sevenDayWeatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("id")), String.valueOf(sevenDayWeatherArray.getJSONObject(i).get("dt"))));
                    }
                    mListViewForecast.setAdapter(new ForecastAdapter(MainActivity.this, forecasts));

                // SetListViewHeight.setListViewHeight(mListViewForecast);
            }catch(Exception e){
                e.printStackTrace();
            }

            }
            super.onPostExecute(s);
        }
        private void create_notification (String title, String message){
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").document().set(new Notifications(title,message,formattedDate,null)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
            prefs.edit().putString("current_date",formattedDate).apply();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("weather_notifications", "Weather Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("inform about current weather and safety precautions");
                channel.enableLights(true);
                channel.setLightColor(Color.MAGENTA);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            }
            Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder nb = new NotificationCompat.Builder(getBaseContext(), "weather_notifications");
            nb.setContentIntent(pendingIntent);
            nb.setContentTitle(title);
            nb.setContentText(message);
            nb.setPriority(NotificationCompat.PRIORITY_HIGH);
            nb.setSmallIcon(R.drawable.logo);
            nb.setSound(RingtoneManager.getActualDefaultRingtoneUri(getBaseContext(), RingtoneManager.TYPE_NOTIFICATION));
            nb.setDefaults(NotificationCompat.FLAG_SHOW_LIGHTS | NotificationCompat.DEFAULT_VIBRATE);
            nb.setLights(Color.BLUE, 5000, 5000);
            nb.setWhen(System.currentTimeMillis());
            nb.setTicker("New Weather Based Suggestion");
            NotificationManagerCompat nmc = NotificationManagerCompat.from(MainActivity.this);
            nmc.notify(100001, nb.build());
        }
        private void create_notification_for_procedures (String title, String message,String videoId) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").document().set(new Notifications(title,message,formattedDate,videoId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
            prefs.edit().putString("current_date",formattedDate).apply();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("procedure_notifications", "Procedure Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("inform about Pocedure and video on how to perform that Procedure");
                channel.enableLights(true);
                channel.setLightColor(Color.MAGENTA);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            }
            Intent notificationIntent = new Intent(MainActivity.this, procedure_details.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.putExtra("videoId",videoId);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder nb = new NotificationCompat.Builder(getBaseContext(), "procedure_notifications");
            nb.setContentIntent(pendingIntent);
            nb.setContentTitle(title);
            nb.setContentText(message);
            nb.setPriority(NotificationCompat.PRIORITY_HIGH);
            nb.setSmallIcon(R.drawable.logo);
            nb.setSound(RingtoneManager.getActualDefaultRingtoneUri(getBaseContext(), RingtoneManager.TYPE_NOTIFICATION));
            nb.setDefaults(NotificationCompat.FLAG_SHOW_LIGHTS | NotificationCompat.DEFAULT_VIBRATE);
            nb.setLights(Color.BLUE, 5000, 5000);
            nb.setWhen(System.currentTimeMillis());
            nb.setTicker("New Procedure Based Info");
            NotificationManagerCompat nmc = NotificationManagerCompat.from(MainActivity.this);
            nmc.notify(100002, nb.build());
        }

    }
    private void edit_profile(){
        View v= LayoutInflater.from(MainActivity.this).inflate(R.layout.profile_page,null);
        final TextInputEditText name=v.findViewById(R.id.name_txt);
        final TextInputEditText email=v.findViewById(R.id.email_txt);
        MaterialSpinner cropcurrentStage=v.findViewById(R.id.cropcurrentstage);
        name.setText(userInfo.getName());
        email.setText(userInfo.getEmail());
        for (int i=0;i<getResources().getStringArray(R.array.crop_stages).length;i++){
            if(getResources().getStringArray(R.array.crop_stages)[i].equals(userInfo.getCropCurrentStage())){
                cropcurrentStage.setSelection(i+1);
            }
        }
        //cropcurrentStage.setSelection();
        android.app.AlertDialog profile_dialog=new AlertDialog.Builder(MainActivity.this)
                .setTitle("Profile")
                .setMessage("Provide Valid Information")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setView(v).create();
        profile_dialog.show();
        profile_dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Enter Name");
                }else if(email.getText().toString().isEmpty()){
                    email.setError("Enter Email");
                }else if(!isValidEmail(email.getText().toString())){
                    email.setError("Invalid Email");
                }else if(cropcurrentStage.getSelectedItem()==null){
                    cropcurrentStage.setError("Select Crop Current Stage");
                }else{
                    Map<String,Object> map=new HashMap<>();
                    map.put("name",name.getText().toString());
                    map.put("email",email.getText().toString());
                    map.put("cropCurrentStage",cropcurrentStage.getSelectedItem().toString());
                    ProgressDialog pd=new ProgressDialog(MainActivity.this);
                    pd.setMessage("Updating Profile...");
                    pd.show();
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            if(task.isSuccessful()){
                                prefs.edit().putString("user_info",new Gson().toJson(new user(name.getText().toString(),email.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),cropcurrentStage.getSelectedItem().toString()))).apply();
                                startActivity(new Intent(MainActivity.this,MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}