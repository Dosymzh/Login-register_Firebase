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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private EditText emailEditText,passwordEditText;
    private Button loginButton,registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


    }


    public void loginButton(View view)
    {
        String email,password;
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (!email.isEmpty() && !password.isEmpty())
        {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Login Succesfull!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }

        else {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_SHORT).show();
        }



    }
    public void registerButton(View view)
    {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

}