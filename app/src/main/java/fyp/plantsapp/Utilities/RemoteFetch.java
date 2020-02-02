package fyp.plantsapp.Utilities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fyp.plantsapp.R;

public class RemoteFetch extends AsyncTask<String,Void,String> {
   StringBuffer json=new StringBuffer();
  ProgressDialog waiting_dialog;
  Context context;
  public RemoteFetch(Context context){
      this.context=context;
      waiting_dialog=new ProgressDialog(context);
      waiting_dialog.setMessage("Fetching Current Weather");
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
            URL url=new URL("http://openweathermap.org/data/2.5/weather?lat="+strings[0]+"&lon="+strings[1]+"&appid=b6907d289e10d714a6e88b30761fae22");
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
        if(waiting_dialog.isShowing())
        waiting_dialog.cancel();
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("weather_json").putExtra("weather_json",s));
    }
}
