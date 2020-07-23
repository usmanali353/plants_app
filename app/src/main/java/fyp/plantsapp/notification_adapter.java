package fyp.plantsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.notification_viewholder> {
 ArrayList<Notifications> notificationsArrayList;

    public notification_adapter(ArrayList<Notifications> notificationsArrayList) {
        this.notificationsArrayList = notificationsArrayList;
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
    }

    @Override
    public int getItemCount() {
        return notificationsArrayList.size();
    }

    class notification_viewholder extends RecyclerView.ViewHolder{
      TextView title,message,date;
        public notification_viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            message=itemView.findViewById(R.id.message);
            date=itemView.findViewById(R.id.date);
        }
    }
}
