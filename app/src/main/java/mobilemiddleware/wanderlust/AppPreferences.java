package mobilemiddleware.wanderlust;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by revathi on 13/03/18.
 */

public class AppPreferences {
    private SharedPreferences preferences;
    private Context ctx;

    public AppPreferences(Context prefscontext) {
        // super();
        this.ctx = prefscontext;
        preferences = prefscontext.getSharedPreferences("com.tigersheet",
                Context.MODE_PRIVATE);
    }

    private SharedPreferences getSharedPrefs() {
        if (preferences == null) {
            preferences = ctx.getSharedPreferences("com.tigersheet",
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }

}
