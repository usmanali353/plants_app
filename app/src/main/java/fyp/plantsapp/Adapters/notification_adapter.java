package fyp.plantsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.plantsapp.Model.Notifications;
import fyp.plantsapp.R;
import fyp.plantsapp.disease_detail;
import fyp.plantsapp.procedure_details;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.notification_viewholder> {
 ArrayList<Notifications> notificationsArrayList;
 Context context;
    public notification_adapter(ArrayList<Notifications> notificationsArrayList, Context context) {
        this.notificationsArrayList = notificationsArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public notification_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notification_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull notification_viewholder holder, int position) {
        holder.message.setText(notificationsArrayList.get(position).getMessage());
        holder.title.setText(notificationsArrayList.get(position).getTitle());
        holder.date.setText(notificationsArrayList.get(position).getDate());
        holder.notificationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationsArrayList.get(position).getVideoId()!=null){
                    context.startActivity(new Intent(context, procedure_details.class).putExtra("videoId",notificationsArrayList.get(position).getVideoId()));
                }else if(notificationsArrayList.get(position).getDiseases()!=null){
                    context.startActivity(new Intent(context, disease_detail.class).putExtra("disease_data",new Gson().toJson(notificationsArrayList.get(position).getDiseases())));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationsArrayList.size();
    }

    class notification_viewholder extends RecyclerView.ViewHolder{
      TextView title,message,date;
      CardView notificationCard;
        public notification_viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            message=itemView.findViewById(R.id.message);
            date=itemView.findViewById(R.id.date);
            notificationCard=itemView.findViewById(R.id.notification_card);
        }
    }
}
