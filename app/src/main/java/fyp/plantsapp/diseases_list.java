package fyp.plantsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fyp.plantsapp.Adapters.disease_list_adapter;
import fyp.plantsapp.Model.Diseases;

public class diseases_list extends AppCompatActivity {
RecyclerView diseases_list;
ArrayList<Diseases> diseases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        diseases=new ArrayList<>();
        diseases_list=findViewById(R.id.diseases_list);
        diseases_list.setLayoutManager(new LinearLayoutManager(this));
        populate_diseases_list();
        diseases_list.setAdapter(new disease_list_adapter(this,diseases));
    }

    private void populate_diseases_list(){
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
        diseases.add(new Diseases("Yellow Stripe Rust","Fungus",sprays1,symptoms1,preventive_measures1,R.drawable.yellow_rust));
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
        diseases.add(new Diseases("Powdery Mildew","Fungus",sprays2,symptoms2,preventive_measures2,R.drawable.powdery_mildrew));
    }
}