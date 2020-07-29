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
        diseases.add(new Diseases("Powdery Mildew","Insect",sprays2,symptoms2,preventive_measures2,R.drawable.powdery_mildrew));
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
        diseases.add(new Diseases("Aphids","Insect",sprays3,symptoms3,preventive_measures3,R.drawable.aphid));
    }
}