package fyp.plantsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;


public class Login extends Activity {
        int num=0;
        TextInputEditText phone_txt;
        Button proceed;
        SharedPreferences prefs;
        FirebaseUser user;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            getWindow().setFlags(1024,1024);
            setContentView(R.layout.activity_login_);
            phone_txt=findViewById(R.id.phone_txt);
            proceed=findViewById(R.id.proceed);
            user= FirebaseAuth.getInstance().getCurrentUser();
            prefs= PreferenceManager.getDefaultSharedPreferences(this);
            if(prefs.getString("user_info",null)!=null){
                fyp.plantsapp.user u=new Gson().fromJson(prefs.getString("user_info",null), fyp.plantsapp.user.class);
                if(u!=null){
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }
            }
            phone_txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    num=count;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().startsWith("0")){
                        phone_txt.setText(s.toString().replace("0",""));
                    }
                    if(!s.toString().startsWith("+92") && num!=0){
                        s.insert(0, "+923");
                    }
                }
            });
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(phone_txt.getText().toString().length()<13){
                        phone_txt.setError("Mobile too Short");
                    }else {
                        startActivity(new Intent(Login.this, verify_code_page.class).putExtra("mobile", phone_txt.getText().toString()));
                    }
                }
            });

        }

    }



