package fyp.plantsapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;

import fyp.plantsapp.R;

public class procedures_list_adapter extends RecyclerView.Adapter<procedures_list_adapter.procedures_list_viewholder> {
    int[] images={R.drawable.wheat0,R.drawable.wheat1,R.drawable.wheat2,R.drawable.wheat3,R.drawable.wheat4,R.drawable.wheat5,R.drawable.wheat6,R.drawable.wheat7};
    @NonNull
    @Override
    public procedures_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new procedures_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wheat_procedures_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull procedures_list_viewholder holder, int position) {
           holder.photoView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
    class procedures_list_viewholder extends RecyclerView.ViewHolder{
       PhotoView photoView;
        public procedures_list_viewholder(@NonNull View itemView) {
            super(itemView);
            photoView=itemView.findViewById(R.id.photoView);
        }
    }
}
