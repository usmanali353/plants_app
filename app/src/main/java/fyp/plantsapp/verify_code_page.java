package fyp.plantsapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import fr.ganfra.materialspinner.MaterialSpinner;
import fyp.plantsapp.Model.user;


public class verify_code_page extends AppCompatActivity {
    TextInputEditText verification_code;
    Button verify;
    String mobile;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024,1024);
        setContentView(R.layout.activity_phone_verification_layout);
        verification_code=findViewById(R.id.verification_txt);
        verify=findViewById(R.id.verify);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        mobile=getIntent().getStringExtra("mobile");
        mAuth= FirebaseAuth.getInstance();
        sendVerificationCode(mobile);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyVerificationCode(verification_code.getText().toString());
            }
        });

    }
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                verification_code.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(verify_code_page.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };
    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

            //signing the user
            signInWithPhoneAuthCredential(credential);
        }catch(Exception e){
            Toast.makeText(verify_code_page.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(verify_code_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ProgressDialog pd=new ProgressDialog(verify_code_page.this);
                            pd.setMessage("Checking if User Already Exists...");
                            pd.show();
                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    pd.dismiss();
                                    if(documentSnapshot.exists()){
                                        prefs.edit().putString("user_info",new Gson().toJson(documentSnapshot.toObject(fyp.plantsapp.Model.user.class))).apply();
                                        Intent intent = new Intent(verify_code_page.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        AlertDialog profile_dialog=new AlertDialog.Builder(verify_code_page.this)
                                                .setTitle("User Info")
                                                .setMessage("Provide Valid Information")
                                                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).create();
                                        View v= LayoutInflater.from(verify_code_page.this).inflate(R.layout.profile_page,null);
                                        final TextInputEditText name=v.findViewById(R.id.name_txt);
                                        final TextInputEditText email=v.findViewById(R.id.email_txt);
                                        final Button start_date=v.findViewById(R.id.start_date);
                                        start_date.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                DatePickerDialog dpd=new DatePickerDialog(verify_code_page.this, new DatePickerDialog.OnDateSetListener() {
                                                    @Override
                                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                        start_date.setText(String.valueOf(dayOfMonth+"-"+(month+1)+"-"+year));
                                                    }
                                                },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                                dpd.show();
                                                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                                            }
                                        });
                                        profile_dialog.setView(v);
                                        profile_dialog.show();
                                        profile_dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(name.getText().toString().isEmpty()){
                                                    name.setError("Enter Name");
                                                }else if(email.getText().toString().isEmpty()){
                                                    email.setError("Enter Email");
                                                }else if(!isValidEmail(email.getText().toString())){
                                                    email.setError("Invalid Email");
                                                }else if(start_date.getText().toString().equals("Select Start Date")){
                                                    start_date.setError("Select Crop Starting Date");
                                                }else{
                                                    user userinfo=new user(name.getText().toString(),email.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),start_date.getText().toString());
                                                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(userinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                prefs.edit().putString("user_info",new Gson().toJson(new user(name.getText().toString(),email.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),start_date.getText().toString()))).apply();
                                                                Intent intent = new Intent(verify_code_page.this, MainActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(verify_code_page.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(verify_code_page.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(verify_code_page.this,message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
