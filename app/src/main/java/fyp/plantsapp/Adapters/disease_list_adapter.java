package fyp.plantsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.plantsapp.Model.Diseases;
import fyp.plantsapp.R;
import fyp.plantsapp.disease_detail;

public class disease_list_adapter extends RecyclerView.Adapter<disease_list_adapter.disease_list_viewholder> {
   Context context;
   ArrayList<Diseases> diseases;

    public disease_list_adapter(Context context, ArrayList<Diseases> diseases) {
        this.context = context;
        this.diseases = diseases;
    }

    @NonNull
    @Override
    public disease_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new disease_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wheat_disease_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull disease_list_viewholder holder, int position) {
       holder.name.setText(diseases.get(position).getName());
       holder.type.setText(diseases.get(position).getType());
       holder.image.setImageResource(diseases.get(position).getImage());
       holder.disease_card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               context.startActivity(new Intent(context, disease_detail.class).putExtra("disease_data",new Gson().toJson(diseases.get(position))));
           }
       });
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    class disease_list_viewholder extends RecyclerView.ViewHolder{
       ImageView image;
       TextView name,type;
       CardView disease_card;
       public disease_list_viewholder(@NonNull View itemView) {
           super(itemView);
           image=itemView.findViewById(R.id.disease_img);
           name=itemView.findViewById(R.id.name);
           type=itemView.findViewById(R.id.type);
           disease_card=itemView.findViewById(R.id.disease_card);
       }
   }
}
