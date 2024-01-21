package sk.tuke.mygame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.gbuttons.GoogleSignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText emailLogin, passwordLogin;
    private TextView toSignUp;
    private Button buttonLogin;
    TextView forgotPassword;
    GoogleSignInButton buttonGoogle;
    GoogleSignInOptions optionsGoogle;
    GoogleSignInClient clientGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.email_login);
        passwordLogin = findViewById(R.id.password_login);
        toSignUp = findViewById(R.id.to_signup);
        buttonLogin = findViewById(R.id.button_login);
        forgotPassword = findViewById(R.id.forgot_password);
        buttonGoogle = findViewById(R.id.button_google);
        auth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, First.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Failed to login.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        passwordLogin.setError("I would fill that in...");
                    }
                } else if (email.isEmpty()) {
                    emailLogin.setError("I would fill that in...");
                } else {
                    emailLogin.setError("Email is incorrect.");
                }
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(LoginActivity.this, SignUpActivity.class)));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.forgot, null);
            }
        });
    }
}