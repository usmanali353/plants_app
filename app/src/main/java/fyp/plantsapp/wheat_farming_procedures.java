package fyp.plantsapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class wheat_farming_procedures extends AppCompatActivity {
  RecyclerView proceduresList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat_farming_procedures);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       proceduresList=findViewById(R.id.procedures_list);
       proceduresList.setLayoutManager(new LinearLayoutManager(this));
       proceduresList.setAdapter(new procedures_list_adapter());
    }

}
