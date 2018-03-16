package mobilemiddleware.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mobilemiddleware.application.WanderLustApplication;
import mobilemiddleware.mappers.Mappers;
import mobilemiddleware.responses.RegistrationResponse;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nameEditText, passwordEditText, emailEditText, reEnterPasswordEditText;
    private Button registerButton;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WanderLustApplication.setCurrentActivityContext(this);
        initializeViews();

    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.name_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        reEnterPasswordEditText = findViewById(R.id.re_enter_password_edit_text);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WanderLustApplication.getInstance().isConnectedToInterNet()) {
                    if (validateViews()) {
                        register(emailEditText.getText().toString(), nameEditText.getText().toString(), passwordEditText.getText().toString());
                    }
                } else {
                    WanderLustApplication.getInstance().showToast("Please connect to the internet");
                }
            }
        });
        loginTextView = findViewById(R.id.login_text_view);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean validateViews() {
        boolean isValid = true;
        if (nameEditText.getText().toString().length() < 5) {
            isValid = false;
            nameEditText.setError("enter atleast 5 chars");
        } else if (passwordEditText.getText().toString().length() < 5) {
            isValid = false;
            passwordEditText.setError("enter atleast 5 chars");
        } else if (!isValidEmail(emailEditText.getText().toString())) {
            isValid = false;
            emailEditText.setError("enter proper email");
        } else if (!(passwordEditText.getText().toString().equals(reEnterPasswordEditText.getText().toString()))) {
            isValid = false;
            reEnterPasswordEditText.setError("passwords do not match");
        }
        return isValid;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void register(String emailId, String name, String password) {
        Mappers apiMapper = new Mappers();
        apiMapper.register(emailId, password, name, registrationListener);
    }

    private Mappers.RegistrationListener registrationListener = new Mappers.RegistrationListener() {

        @Override
        public void onSuccess(RegistrationResponse registrationResponse) {
            if (registrationResponse != null) {
                if (registrationResponse.getStatus().equals("OK")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, PreferenceActivity.class));

                } else if (registrationResponse.getStatus().equals("E")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());

                } else {
                    WanderLustApplication.getInstance().showToast("Registration failed");
                }
            } else {
                WanderLustApplication.getInstance().showToast("Registration failed");
            }
        }

        @Override
        public void onFailure(String message) {
            WanderLustApplication.getInstance().showToast("error occurred");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        WanderLustApplication.setCurrentActivityContext(this);
    }
}