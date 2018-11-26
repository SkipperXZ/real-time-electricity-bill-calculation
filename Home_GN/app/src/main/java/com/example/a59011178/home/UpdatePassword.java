package com.example.a59011178.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity{

    private static final String TAG = "UpdatePassword";
         Button bDone ;
        private EditText npass;
        FirebaseAuth auth;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.update_password);


            npass = findViewById(R.id.up_pass);
            bDone = findViewById(R.id.done);


            auth = FirebaseAuth.getInstance();

            bDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user!=null){

                    user.updatePassword(npass.getText().toString())

                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"Your password has been chaged",Toast.LENGTH_LONG).show();
                                        auth.signOut();
                                        Intent i = new Intent(UpdatePassword.this, LoginActivity.class);
                                        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                else {
                                        Toast.makeText(getApplicationContext(),"Password cold not be changed",Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                }
        }
});
        }}


