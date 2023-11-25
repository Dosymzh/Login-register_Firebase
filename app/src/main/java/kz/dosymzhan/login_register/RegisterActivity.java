package kz.dosymzhan.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private EditText nameEditText,emailEditText,passwordEditText,confPasswordEditText;
    private Button loginButton,registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confPasswordEditText = findViewById(R.id.confPasswordEditText);

    }

    public void registerButton(View view) {
        String name,email,password,confpassword;
        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confpassword = confPasswordEditText.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confpassword.isEmpty())
        {
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name",name);
                        userData.put("email",email);
                        userData.put("password",password);

                        firestore.collection("collection1").document(user.getUid()).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    Toast.makeText(RegisterActivity.this, "Register Succesfull!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Enter edit-Text!", Toast.LENGTH_SHORT).show();
        }

    }

    public void loginButton(View view)
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}