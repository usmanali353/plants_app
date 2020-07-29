package fyp.plantsapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.plantsapp.Fragments.preventive_mesaures_fragment;
import fyp.plantsapp.Fragments.sprays_fragment;
import fyp.plantsapp.Fragments.symptoms_fragment;

public class disease_detail extends AppCompatActivity {
    ViewPager vp;
    TabLayout tb;
    ImageView collaspingtoolbarimage;
    CollapsingToolbarLayout ctbl;
    Diseases diseases;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctbl=findViewById(R.id.collapse_toolbar);
        diseases=new Gson().fromJson(getIntent().getStringExtra("disease_data"),Diseases.class);
        collaspingtoolbarimage=findViewById(R.id.bgheader);
        if(diseases!=null){
            collaspingtoolbarimage.setImageResource(diseases.getImage());
        }

        vp=(ViewPager) findViewById(R.id.view);
        tb=(TabLayout) findViewById(R.id.tabs);
        setviewpager();
        tb.setupWithViewPager(vp);
    }
    public void setviewpager(){

        viewpageradapter vpa=new viewpageradapter(getSupportFragmentManager());

        vpa.addfragment(new symptoms_fragment(),"Symptoms");

        vpa.addfragment(new preventive_mesaures_fragment(),"Preventive Measures");

        vpa.addfragment(new sprays_fragment(),"Sprays");

        vp.setAdapter(vpa);

        vp.setOffscreenPageLimit(4);

    }
    //adapter for viewpager

    class viewpageradapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> listofFragment;

        ArrayList<String>fragmenttitles;



        public viewpageradapter(FragmentManager fm) {

            super(fm);

            listofFragment=new ArrayList<Fragment>();

            fragmenttitles=new ArrayList<String>();

        }

        @Override

        public Fragment getItem(int position) {

            return listofFragment.get(position);

        }



        @Override

        public int getCount() {

            return listofFragment.size();

        }

        public void addfragment(Fragment f, String name){

            listofFragment.add(f);

            fragmenttitles.add(name);

        }

        @Override

        public CharSequence getPageTitle(int position) {

            return fragmenttitles.get(position);

        }

    }
}
