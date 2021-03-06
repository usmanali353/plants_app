package fyp.plantsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fyp.plantsapp.Adapters.video_list_adapter;

public class crop_Videos extends AppCompatActivity {
 RecyclerView videos_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024,1024);
        setContentView(R.layout.activity_crop_videos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        videos_list=findViewById(R.id.video_list);
        videos_list.setLayoutManager(new LinearLayoutManager(this));
        videos_list.setAdapter(new video_list_adapter());
    }

}
