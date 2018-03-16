package mobilemiddleware.wanderlust;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import mobilemiddleware.application.WanderLustApplication;
import mobilemiddleware.mappers.Mappers;
import mobilemiddleware.responses.RegistrationResponse;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        WanderLustApplication.setCurrentActivityContext(this);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkUserLogin();

            }
        }, 10000);

    }

    private void checkUserLogin() {
        Mappers apiMapper = new Mappers();
        apiMapper.checkUserSession(checkUserSessionListener);
    }

    private Mappers.checkUserListener checkUserSessionListener = new Mappers.checkUserListener() {

        @Override
        public void onSuccess(RegistrationResponse registrationResponse) {
            if (registrationResponse != null) {
                if (registrationResponse.getStatus().equals("OK")) {
                    finish();
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                } else if (registrationResponse.getStatus().equals("E")) {
                    finish();
                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                    WanderLustApplication.getInstance().showToast(registrationResponse.getMessage());

                } else {
                    WanderLustApplication.getInstance().showToast("request failed");
                }
            } else {
                WanderLustApplication.getInstance().showToast("request failed");
            }
        }

        @Override
        public void onFailure(String message) {
            WanderLustApplication.getInstance().showToast("error occurred");
        }
    };
}
