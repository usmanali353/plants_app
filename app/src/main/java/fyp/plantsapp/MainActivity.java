package fyp.plantsapp;
import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import fr.ganfra.materialspinner.MaterialSpinner;
import fyp.plantsapp.Model.Diseases;
import fyp.plantsapp.Model.Notifications;
import fyp.plantsapp.Model.user;
import fyp.plantsapp.Utilities.Forecast;
import fyp.plantsapp.Utilities.ForecastAdapter;


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
                        populate_diseases_list();
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
            FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").document().set(new Notifications(title,message,formattedDate,null,null)).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").document().set(new Notifications(title,message,formattedDate,videoId,null)).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    private void populate_diseases_list(){
        ArrayList<Diseases> diseases=new ArrayList<>();
        List<String> preventive_measures1=new ArrayList<>();
        preventive_measures1.add("Cultivate disease resistant variables if available");
        preventive_measures1.add("Ensure adequate nitrogen fertilization,Avoid excess nitrogen use.");
        preventive_measures1.add("Screen fields regularly for volunteer plants and remove then");
        preventive_measures1.add("Plow and dig crop residues deep in the soil after harvest");
        List<String> symptoms1=new ArrayList<>();
        symptoms1.add("Tiny Rusty postules arranged in stripes");
        symptoms1.add("stems and head can also be affected");
        List<String> sprays1=new ArrayList<>();
        sprays1.add("bacillus pumilus");
        sprays1.add("Azoxystrobin 18.2%,Cyproconazole 7.3% SC");
        sprays1.add("Azoxystrobin 18.2%,Difenoconazole 11.4% SC");
        sprays1.add("Tebuconazole 50.0%,WG,Trifloxystrobin 25.0% WG");
        diseases.add(new Diseases("Yellow Stripe Rust","Fungus",sprays1,symptoms1,preventive_measures1,R.drawable.yellow_rust,"The symptoms are caused by the fungus Puccinia striiformis, an obligate parasite that requires living plant material to survive. Spores are dispersed up to hundreds of kilometers by wind currents and can initiate seasonal epidemics of the disease. The fungus enters the plant via the stomata and gradually colonizes the leaf tissues. The disease mainly occurs early in the growing season. Favorable conditions for the development of the fungus and the infection are high altitude, high humidity (dews), rainfall and cool temperatures between 7 and 15°C. Infection tends to cease when temperatures consistently exceed 21-23°C because at these temperatures the life cycle of the fungus is interrupted. Alternative hosts are wheat, barley, and rye."));
        List<String> preventive_measures2=new ArrayList<>();
        preventive_measures2.add("Choose resistant variables if available");
        preventive_measures2.add("Do not sow too early in the season");
        preventive_measures2.add("Modify sowing density to allow for good aeration of cultures and reduce humidity");
        preventive_measures2.add("Mornitor field regularly for first signs of disease");
        preventive_measures2.add("plan crop rotation with non host plants");
        List<String> symptoms2=new ArrayList<>();
        symptoms2.add("White fluffy patches on the leaves");
        symptoms2.add("in some crops patches can appear as large raised postules instead");
        symptoms2.add("Powdery Zones turns gray as disease progresses");
        symptoms2.add("Conspicious black specks may appear late in the season");
        symptoms2.add("Densly sowed plants and excessive nitrogen application are main causes");
        List<String> sprays2=new ArrayList<>();
        sprays2.add("Milk Solutions");
        sprays2.add("Azoxystrobin 18.2%,Cyproconazole 7.3% SC");
        sprays2.add("Azoxystrobin 18.2%,Difenoconazole 11.4% SC");
        sprays2.add("Tebuconazole 50.0%,WG,Trifloxystrobin 25.0% WG");
        diseases.add(new Diseases("Powdery Mildew","Fungus",sprays2,symptoms2,preventive_measures2,R.drawable.powdery_mildrew,"The symptoms are caused by the fungus Blumeria graminis, an obligate biotroph that can only grow and reproduce on a living host. If no hosts are available, it overwinters between seasons as dormant structures on plant debris in the field. Apart from the cereals, it can colonize dozens of other plants, that it may use to bridge two seasons. When conditions are favorable, it resumes growth and produces spores that are later scattered by wind to healthy plants. Once it lands on a leaf, the spore germinates and produce feeding structures that takes up nutrients from the host cells to support the growth of the fungus. Relatively cool and humid conditions (95% humidity) and cloudy weather favor its development. However, leaf moisture is not needed for the germination of the spores and can actually inhibit it. Ideal temperatures are between 16 °C and 21 °C with temperatures above 25 °C being detrimental. No known quarantine regulations exist for this pathogen because of its widespread distribution and airborne dissemination."));
        List<String> preventive_measures3=new ArrayList<>();
        preventive_measures3.add("Maintain a high number of different varieties of plants around fields.");
        preventive_measures3.add("Use reflective mulches to repel invading populations of aphids.");
        preventive_measures3.add("Monitor fields regularly to assess the incidence of a disease or pest and determine their severity.");
        preventive_measures3.add("Remove infected plant parts.");
        preventive_measures3.add("Check weeds in and around the fields.");
        preventive_measures3.add("Do not over-water or over-fertilize.");
        preventive_measures3.add("Control ant populations that protect aphids with sticky bands.");
        List<String> symptoms3=new ArrayList<>();
        symptoms3.add("Curled and deformed leaves. ");
        symptoms3.add("Small insects under the leaves and shoots.");
        symptoms3.add("Stunted growth.");
        List<String> sprays3=new ArrayList<>();
        sprays3.add("In case of mild infestation, use an insecticidal soap solution or solution based on plant oils, e.g. neem oil (3 ml/l). Aphids are also very susceptible to fungal diseases when it is humid. A spray of water on affected plants can also remove them.");
        sprays3.add("Always consider an integrated approach with preventive measures together with biological treatments if available. Be aware that the use of chemical pesticides can cause aphids to become resistant to those that are used. Stem application with flonicamid and water (1:20) ratio at 30, 45, 60 days after sowing (DAS) can be planned. Fipronil 2ml or thiamethoxam (0.2g) or flonicamid (0.3g) or acetamiprid (0.2 per liter of water) can also be used. However, these chemicals can have negative impacts on predators, parasitoids, and pollinators.");
        diseases.add(new Diseases("Aphids","Insect",sprays3,symptoms3,preventive_measures3,R.drawable.aphid,"Aphids are small, soft-bodied insects with long legs and antennae. Their size ranges from 0.5 to 2mm and the color of their body can be yellow, brown, red or black, depending on the species. Their aspect ranges from the wingless varieties, that are generally predominant, to the winged, waxy or woolly types. They usually settle and feed in clusters on the underside of well-fed young leaves and shoot tips. They use their long mouthparts to pierce tender plant tissues and suck out fluids. Low to moderate numbers are not damaging to the crops.  After an initial invasion in late spring or early summer, the aphid population usually diminishes naturally due to natural enemies. Several species carry plant viruses that can lead to the development of other diseases."));
        int id= new Random().nextInt(diseases.size());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").document().set(new Notifications("Your Crop may get "+diseases.get(id).getName()+" that is the "+diseases.get(id).getType(),diseases.get(id).getTrigger(),formattedDate,null,diseases.get(id))).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("disease_notifications", "Disease Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("inform about Diseases and its detail");
            channel.enableLights(true);
            channel.setLightColor(Color.MAGENTA);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
        Intent notificationIntent = new Intent(MainActivity.this, disease_detail.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("disease_data",new Gson().toJson(diseases.get(id)));
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(getBaseContext(), "disease_notifications");
        nb.setContentIntent(pendingIntent);
        nb.setLargeIcon(BitmapFactory.decodeResource(getResources(),diseases.get(id).getImage()));
        nb.setContentTitle("Your Crop may get "+diseases.get(id).getName()+" that is the "+diseases.get(id).getType());
        nb.setContentText(diseases.get(id).getTrigger());
        nb.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),diseases.get(id).getImage())));
        nb.setPriority(NotificationCompat.PRIORITY_HIGH);
        nb.setSmallIcon(R.drawable.logo);
        nb.setSound(RingtoneManager.getActualDefaultRingtoneUri(getBaseContext(), RingtoneManager.TYPE_NOTIFICATION));
        nb.setDefaults(NotificationCompat.FLAG_SHOW_LIGHTS | NotificationCompat.DEFAULT_VIBRATE);
        nb.setLights(Color.BLUE, 5000, 5000);
        nb.setWhen(System.currentTimeMillis());
        nb.setTicker("New Disease Based Info");
        NotificationManagerCompat nmc = NotificationManagerCompat.from(MainActivity.this);
        nmc.notify(100003, nb.build());
    }
    }
