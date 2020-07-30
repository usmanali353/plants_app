package fyp.plantsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;

import fyp.plantsapp.Adapters.procedures_list_adapter;

public class wheat_farming_procedures extends AppCompatActivity {
  PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat_farming_procedures);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pdfView=findViewById(R.id.pdfView);
        pdfView.fromAsset("farming_procedures.pdf").load();
    }

}
