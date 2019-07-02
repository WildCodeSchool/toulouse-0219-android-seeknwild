package fr.wildcodeschool.seeknwild.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.User;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView tvCreateAccount = findViewById(R.id.tvHaveAccount);
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, CreateAccountActivity.class));
            }
        });

        Button btConnect = findViewById(R.id.btSignIn);
        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMail = findViewById(R.id.etMailSign);
                EditText etPw = findViewById(R.id.etPwSign);
                if (!etMail.getText().toString().isEmpty() && !etPw.getText().toString().isEmpty()) {
                    HashCode hashCode = Hashing.sha256().hashString(etPw.getText().toString(), Charset.defaultCharset());
                    final User newUser = new User();
                    newUser.setEmail(etMail.getText().toString());
                    newUser.setPassword(etPw.getText().toString());
                    newUser.setPassword(hashCode.toString());
                    VolleySingleton.getInstance(getApplicationContext()).getUserByEmail(newUser, new Consumer<User>() {
                        @Override
                        public void accept(User user) {
                            if (user == null) {
                                //TODO afficher message d'erreur
                            } else {
                                UserSingleton.getInstance().setUser(user);
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setTitle(getString(R.string.votreEmail));
                    builder.setMessage(getString(R.string.veuillezRenseigner));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}