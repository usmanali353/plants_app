package fyp.plantsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;

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
