package fr.wildcodeschool.seeknwild;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        Button btRegister = findViewById(R.id.btCreate);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etEmail = findViewById(R.id.etMail);
                EditText etPassword = findViewById(R.id.etPw);
                createAccount(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        TextView already = findViewById(R.id.tvAlreadyDone);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignIn = new Intent(CreateAccount.this, SignIn.class);
                startActivity(goToSignIn);
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                {
                                    try
                                    {
                                        throw task.getException();
                                    }
                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        Log.d(TAG, "onComplete: weak_password");
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccount.this);
                                        builder.setTitle("Attention");
                                        builder.setMessage("Merci de renseigner un mot de passe de 8 lettres ou plus");
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d(TAG, "onComplete: malformed_email");
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccount.this);
                                        builder.setTitle("Attention");
                                        builder.setMessage("L’adresse email n’est pas correctement formatée.");
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        Log.d(TAG, "onComplete: exist_email");
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccount.this);
                                        builder.setTitle("Attention");
                                        builder.setMessage("Un compte existe déjà avec cette adresse email, souhaitez-vous vous connecter ?");
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    }
                                    catch (Exception e)
                                    {
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                    }
                                }
                            }
                        }
                );
    }
}
