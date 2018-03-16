package mobilemiddleware.wanderlust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import mobilemiddleware.application.WanderLustApplication;

public class PreferenceActivity extends AppCompatActivity {
    private Button submitButton;
    private int[] checkBoxArray = {R.id.historic_check_box, R.id.food_check_box, R.id.entertainment_check_box, R.id.religious_check_box};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        WanderLustApplication.setCurrentActivityContext(this);
        initializeViews();
    }

    private void initializeViews() {
        submitButton = findViewById(R.id.submit_button);
        if (WanderLustApplication.getInstance().preferences[0] != null) {
            for (int i = 0; i < checkBoxArray.length; i++) {
                if (WanderLustApplication.getInstance().preferences[i].length() > 1) {
                    ((CheckBox) findViewById(checkBoxArray[i])).setChecked(true);
                } else {
                    ((CheckBox) findViewById(checkBoxArray[i])).setChecked(false);
                }
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < checkBoxArray.length; i++) {
                    if (((CheckBox) findViewById(checkBoxArray[i])).isChecked()) {
                        WanderLustApplication.getInstance().preferences[i] = ((CheckBox) findViewById(checkBoxArray[i])).getText().toString();
                        WanderLustApplication.getInstance().showToast("Saved preferences");
                        finish();
                        startActivity(new Intent(PreferenceActivity.this, LoginActivity.class));
                    } else {
                        WanderLustApplication.getInstance().preferences[i] = "";
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        WanderLustApplication.setCurrentActivityContext(this);
    }
}