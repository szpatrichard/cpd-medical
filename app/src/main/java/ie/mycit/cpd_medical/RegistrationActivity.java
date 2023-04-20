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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Registration Activity Java Class.
 */
public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText editTextFirstname, editTextSurname, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

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
        setContentView(R.layout.activity_registration);

        // Initialise properties
        mAuth = FirebaseAuth.getInstance();
        editTextFirstname = findViewById(R.id.firstname);
        editTextSurname = findViewById(R.id.surname);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        Button registerBtn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progress_bar);
        TextView textViewLoginLink = findViewById(R.id.login_link);

        // Listening for when the register button is clicked
        registerBtn.setOnClickListener(view -> {
            // Make progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            String firstname, surname, email, password;
            firstname = String.valueOf(editTextFirstname.getText());
            surname = String.valueOf(editTextSurname.getText());
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            // Check if the firstname provided is empty
            if (TextUtils.isEmpty(firstname)) {
                Toast.makeText(RegistrationActivity.this, "Enter firstname", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check if the surname provided is empty
            if (TextUtils.isEmpty(surname)) {
                Toast.makeText(RegistrationActivity.this, "Enter surname", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check if the email provided is empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check if the password provided is empty
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Creating a new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        // Make progress bar invisible
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Registration success, add user details to the user collection
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> user = new HashMap<>();
                            user.put("firstname", firstname);
                            user.put("surname", surname);

                            db.collection("users")
                                    .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                    .set(user)
                                    .addOnSuccessListener(documentReference -> {
                                        System.out.println("DocumentSnapshot");
                                    })
                                    .addOnFailureListener(e -> {
                                        System.out.println("Error adding document: " + e);
                                    });

                            // Redirect to the main screen
                            redirect();
                        } else {
                            // If registration fails, display a message to the user
                            Toast.makeText(RegistrationActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Listening for when the login link text view is clicked
        textViewLoginLink.setOnClickListener(view -> {
                    // Start a new Login Activity
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
