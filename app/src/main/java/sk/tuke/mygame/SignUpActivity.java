package sk.tuke.mygame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailSignUp, passwordSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        Inštancia pre firebase a inicializácia premenných
        auth = FirebaseAuth.getInstance();
        emailSignUp = findViewById(R.id.email_signup);
        passwordSignUp = findViewById(R.id.password_signup);
        Button buttonSignUp = findViewById(R.id.button_signup);
        TextView toLogin = findViewById(R.id.to_login);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Prevzatie a uloženie emailu a hesla do premenných
                String user = emailSignUp.getText().toString().trim();
                String password = passwordSignUp.getText().toString().trim();


//                Ak je email alebo heslo prázdne, vypíše sa chybová hláška/upozornenie
                if (user.isEmpty()) {
                    emailSignUp.setError("If I were you, I would fill that email.");
                }

                if (password.isEmpty()) {
                    passwordSignUp.setError("If I were you, I would fill that password.");
                } else {
//                    Inak sa vytvorí nový používateľ s daným emailom a heslom
                    auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Alright, signed up.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else {
//                                V prípade, že to neprebehne úspešne -> toast
                                Toast.makeText(SignUpActivity.this, "Something went wrong." + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

//        Stlačenie na text -> presmerovanie na Login aktivitu
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}