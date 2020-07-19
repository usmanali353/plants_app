package fyp.plantsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class video_list_adapter extends RecyclerView.Adapter<video_list_adapter.video_list_viewholder> {
    ArrayList<String> videos;

    public video_list_adapter() {
        videos=new ArrayList<>();
        videos.add("YTix0yymVWM");
        videos.add("bh1QzRf2wfQ");
        videos.add("JnxXX2cjqVo");
    }

    @NonNull
    @Override
    public video_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new video_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull video_list_viewholder holder, int position) {
       holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
           @Override
           public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videos.get(position),0);
           }
       });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class video_list_viewholder extends RecyclerView.ViewHolder{
        YouTubePlayerView youTubePlayerView;
        public video_list_viewholder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView=itemView.findViewById(R.id.youtube_player_view);
        }
    }
}
