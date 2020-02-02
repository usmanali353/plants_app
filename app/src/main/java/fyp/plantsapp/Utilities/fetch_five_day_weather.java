package fyp.plantsapp.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class fetch_five_day_weather extends AsyncTask<String,Void,String> {
    StringBuffer json=new StringBuffer();
    ProgressDialog waiting_dialog;
    Context context;
    ListView listView;

    ArrayList<String> dates=new ArrayList<>();
    ArrayList<String> time_removed_dates=new ArrayList<>();
    ArrayList<Forecast> forecasts=new ArrayList<>();
    public fetch_five_day_weather(Context context, ListView listView){
        this.context=context;
        this.listView=listView;
        waiting_dialog=new ProgressDialog(context);
        waiting_dialog.setMessage("Fetching Five Day Weather Weather");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!waiting_dialog.isShowing()){
            waiting_dialog.show();
        }
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url=new URL("http://openweathermap.org/data/2.5/forecast?lat="+strings[0]+"&lon="+strings[1]+"9&appid=b6907d289e10d714a6e88b30761fae22");
            Log.e("url",url.toString());
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line=reader.readLine())!=null){
                json.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("fetched_json",json.toString());
        return json.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        waiting_dialog.cancel();
        Forecast f=null;
        try {
            JSONObject obj =new JSONObject(s);
            JSONArray list=obj.getJSONArray("list");
            for(int i=0;i<list.length();i++){
                JSONObject weather_info_obj=list.getJSONObject(i);
                 f=new Forecast();
                f.setHighTemp(weather_info_obj.getJSONObject("main").getString("temp_max"));
                f.setLowTemp(weather_info_obj.getJSONObject("main").getString("temp_min"));
//                f.setWeather(weather_info_obj.getJSONObject("main").getString("description"));
                f.setWeatherId(weather_info_obj.getJSONArray("weather").getJSONObject(0).getString("id"));
                f.setDate(weather_info_obj.getString("dt_txt"));
                forecasts.add(f);
            }
             for(int j=0;j<forecasts.size();j++){
                 dates.addAll(Arrays.asList(forecasts.get(j).getDate().split("\\s")));
             }
            for(int j=0;j<dates.size();j++){
                if(dates.get(j).contains("-")){
                    time_removed_dates.add(dates.get(j));
                }
            }
            Log.e("dates",String.valueOf(dates.size()));
            Log.e("time_removed_dates",String.valueOf(dates.size()));
            Log.e("forecasts",String.valueOf(forecasts.size()));
            listView.setAdapter(new ForecastAdapter(context,forecasts));
            SetListViewHeight.setListViewHeight(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
