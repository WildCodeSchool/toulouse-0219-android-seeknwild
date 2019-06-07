package fr.wildcodeschool.seeknwild;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        Button btLogin = findViewById(R.id.btSignIn);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLogin = findViewById(R.id.etMailSign);
                EditText etPassword = findViewById(R.id.etPwSign);
                signIn(etLogin.getText().toString(), etPassword.getText().toString());
            }
        });

    }

    private void signIn(String email, String password) {
        final ConstraintLayout singInL = findViewById(R.id.signIn_layout);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent goToMapsActivity = new Intent(SignIn.this, HomeActivity.class);
                            startActivity(goToMapsActivity);
                        } else {
                            Log.w(TAG, getString(R.string.failure), task.getException());
                            Toast.makeText(SignIn.this, getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
