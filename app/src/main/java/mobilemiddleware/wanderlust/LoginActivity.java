package mobilemiddleware.wanderlust;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mobilemiddleware.application.WanderLustApplication;
import mobilemiddleware.mappers.Mappers;
import mobilemiddleware.responses.RegistrationResponse;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private Dialog dialog;
    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WanderLustApplication.setCurrentActivityContext(this);
        initializeViews();
    }

    private void initializeViews() {
        forgotPasswordTextView = findViewById(R.id.forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
        emailEditText = findViewById(R.id.login_email_edit_text);
        passwordEditText = findViewById(R.id.login_password_edit_text);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WanderLustApplication.getInstance().isConnectedToInterNet()) {
                    if (isValidEmail(emailEditText.getText().toString())) {
                        login(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                    }
                } else {
                    WanderLustApplication.getInstance().showToast("Please connect to the internet");
                }
            }
        });
        signUpTextView = findViewById(R.id.sign_up_text_view);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void login(String emailId, String password) {
        Mappers apiMapper = new Mappers();
        apiMapper.login(emailId, password, loginListener);
    }

    private Mappers.LoginListener loginListener = new Mappers.LoginListener() {

        @Override
        public void onSuccess(RegistrationResponse registrationResponse) {
            if (registrationResponse != null) {
                if (registrationResponse.getStatus().equals("OK")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                } else if (registrationResponse.getStatus().equals("E")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());

                } else {
                    WanderLustApplication.getInstance().showToast("Login failed");
                }
            } else {
                WanderLustApplication.getInstance().showToast("Login failed");
            }
        }

        @Override
        public void onFailure(String message) {
            WanderLustApplication.getInstance().showToast("error occurred");
        }
    };

    /**
     * Displays forgot password dialog
     */
    private void showForgotPasswordDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_reset_password);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);

        final EditText mForgotPwdEmailEditText = dialog.findViewById(R.id.reset_email_edit_text);
        Button resetPasswordButton = dialog.findViewById(R.id.reset_password_button);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WanderLustApplication.getInstance().isConnectedToInterNet()) {
                    if (isValidEmail(mForgotPwdEmailEditText.getText().toString().trim())) {
                        resetPassword(mForgotPwdEmailEditText.getText().toString().trim());
                    }
                } else {
                    WanderLustApplication.getInstance().showToast("Please connect to the internet");
                }
            }
        });

        dialog.show();
    }

    private void resetPassword(String emailId) {
        Mappers apiMapper = new Mappers();
        apiMapper.resetPassword(emailId, resetPasswordListener);
    }

    private Mappers.ResetPasswordListener resetPasswordListener = new Mappers.ResetPasswordListener() {

        @Override
        public void onSuccess(RegistrationResponse registrationResponse) {
            if (registrationResponse != null) {
                if (registrationResponse.getStatus().equals("OK")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());
                    dialog.dismiss();

                } else if (registrationResponse.getStatus().equals("E")) {
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());

                } else {
                    WanderLustApplication.getInstance().showToast("Reset failed");
                }
            } else {
                WanderLustApplication.getInstance().showToast("Reset failed");
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