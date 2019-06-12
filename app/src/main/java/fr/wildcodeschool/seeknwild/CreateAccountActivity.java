package fr.wildcodeschool.seeknwild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CreateAccountActivity extends AppCompatActivity {

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

        TextView already = findViewById(R.id.tvAlreadyUser);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignIn = new Intent(CreateAccountActivity.this, SignInActivity.class);
                startActivity(goToSignIn);
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                        Log.d(TAG, getString(R.string.weakPW));
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccountActivity.this);
                                        builder.setTitle(R.string.warning);
                                        builder.setMessage(R.string.needMoreChar);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                        Log.d(TAG, getString(R.string.unformalMail));
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccountActivity.this);
                                        builder.setTitle(R.string.warning);
                                        builder.setMessage(R.string.reWrittingMail);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    } catch (FirebaseAuthUserCollisionException existEmail) {
                                        Log.d(TAG, getString(R.string.existingMail));
                                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateAccountActivity.this);
                                        builder.setTitle(R.string.warning);
                                        builder.setMessage(R.string.existINgAdress);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    } catch (Exception e) {
                                        Log.d(TAG, getString(R.string.onComplete) + e.getMessage());
                                    }
                                }
                            }
                        }
                );
    }
}
