package sk.tuke.mygame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.gbuttons.GoogleSignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText emailLogin, passwordLogin;
    GoogleSignInButton buttonGoogle;
//    Nastavenie pre prihlasovanie cez Google
    GoogleSignInOptions optionsGoogle;
//    Klient pre prihlasovanie cez Google
    GoogleSignInClient clientGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Inicializácia
        emailLogin = findViewById(R.id.email_login);
        passwordLogin = findViewById(R.id.password_login);
        TextView toSignUp = findViewById(R.id.to_signup);
        Button buttonLogin = findViewById(R.id.button_login);
        buttonGoogle = findViewById(R.id.button_google);
        auth = FirebaseAuth.getInstance();

//        Čo sa deje pri stlačení na tlačidlo prihlásenia
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Získanie hodnôt
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

//                Je email prázdny a má správny formát?
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    Je heslo prázdne
                    if (!password.isEmpty()) {
//                        Prihlásenie pomocou Firebase autentifikácie
                        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            Úspech -> výpis hlášky a presmerovanie na novú aktivitu
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, First.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
//                            Neúspech -> zobrazenie chybovej hlášky
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Failed to login.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
//                        Ak je heslo prázdne
                        passwordLogin.setError("I would fill that in...");
                    }
                } else if (email.isEmpty()) {
//                    Ak je email prázdny
                    emailLogin.setError("I would fill that in...");
                } else {
//                    Ak emial nemá správny formát
                    emailLogin.setError("Email is incorrect.");
                }
            }
        });

//        Na kliknutie pre Sign Up
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(LoginActivity.this, SignUpActivity.class)));
            }
        });

//        Na prihlásenie cez Google
        optionsGoogle = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        clientGoogle = GoogleSignIn.getClient(this, optionsGoogle);

//        Je užívateľ už prihlásený cez Google?
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null) {
            finish();
            Intent intent = new Intent(LoginActivity.this, First.class);
            startActivity(intent);
        }

//        Spustenie Google prihlasovania
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = o.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                    try {
//                        Získanie údajov o Google účte
                        task.getResult(ApiException.class);
//                        OK == 1, ďalšia aktivita
                        finish();
                        Intent intent1 = new Intent(LoginActivity.this, First.class);
                        startActivity(intent1);
                    } catch (ApiException e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//        Kliknutie na Google tlačidlo
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = clientGoogle.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
    }
}