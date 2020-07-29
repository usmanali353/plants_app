package fyp.plantsapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import fyp.plantsapp.Adapters.disease_details_adapter;
import fyp.plantsapp.Model.Diseases;
import fyp.plantsapp.R;

public class symptoms_fragment extends Fragment {
    Diseases diseases;
    RecyclerView details;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.disease_details_list,container,false);
        diseases=new Gson().fromJson(getActivity().getIntent().getStringExtra("disease_data"),Diseases.class);
        details=v.findViewById(R.id.details_list);
        details.setLayoutManager(new LinearLayoutManager(getActivity()));
        details.setAdapter(new disease_details_adapter(getActivity(),diseases.getSymptoms()));
        return v;
    }
}
