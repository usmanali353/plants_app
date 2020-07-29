package fyp.plantsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fyp.plantsapp.R;

public class disease_details_adapter extends RecyclerView.Adapter<disease_details_adapter.disease_details_viewholder> {
    Context context;
    List<String> list;

    public disease_details_adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public disease_details_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new disease_details_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull disease_details_viewholder holder, int position) {
       holder.detail.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class disease_details_viewholder extends RecyclerView.ViewHolder{
         TextView detail;
        public disease_details_viewholder(@NonNull View itemView) {
            super(itemView);
            detail=itemView.findViewById(R.id.details);
        }
    }
}
