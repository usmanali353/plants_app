package fyp.plantsapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import java.util.ArrayList;

import fyp.plantsapp.Adapters.notification_adapter;
import fyp.plantsapp.Model.Notifications;

public class notifications_page extends AppCompatActivity {
  RecyclerView notificationList;
  ArrayList<Notifications> notificationsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notificationsArrayList=new ArrayList<>();
        notificationList=findViewById(R.id.notification_list);
        notificationList.setLayoutManager(new LinearLayoutManager(this));
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Fetching Notifications...");
        pd.show();
        FirebaseFirestore.getInstance().collection("notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("user_notification").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for (int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        notificationsArrayList.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Notifications.class));
                    }
                    notificationList.setAdapter(new notification_adapter(notificationsArrayList,notifications_page.this));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(notifications_page.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
