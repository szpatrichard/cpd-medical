package ie.mycit.cpd_medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Login Activity Java Class.
 */
public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextInputEditText editTextEmail, editTextPassword;
    Button loginBtn;
    ProgressBar progressBar;
    TextView textViewRegisterLink;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in and redirect to the main screen
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) redirect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialise properties
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        textViewRegisterLink = findViewById(R.id.register_link);

        // Listening for when the login button is clicked
        loginBtn.setOnClickListener(view -> {
            // Make progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            // Check if the email provided is empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check if the password provided is empty
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Signing in the user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        // Make progress bar invisible
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Redirect user to the main screen
                            redirect();
                        } else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Listening for when the registration link text view is clicked
        textViewRegisterLink.setOnClickListener(view -> {
                    // Start a new Registration Activity
                    Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
    }

    /**
     * Start a new Main Activity.
     */
    protected void redirect() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
