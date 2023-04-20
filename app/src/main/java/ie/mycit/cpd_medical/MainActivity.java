package ie.mycit.cpd_medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Main Activity Java Class.
 */
public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView textViewUserDetails;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise properties
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Redirect to the login screen if no user
        if (user == null) redirect();

        textViewUserDetails = findViewById(R.id.user_details);
        logoutBtn = findViewById(R.id.logout_btn);
        StringBuilder fullname = new StringBuilder();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("users").document(user.getUid());

        docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                fullname
                        .append(snapshot.getData().get("firstname"))
                        .append(snapshot.getData().get("surname"));
            }
        });

        // Welcome the user printing out their email address
        textViewUserDetails.setText(String.format("Welcome %s!", fullname));

        // Listening for when the logout button is clicked
        logoutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            redirect();
        });
    }

    /**
     * Start a new Login Activity.
     */
    protected void redirect() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
