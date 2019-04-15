package machersstudio.aust.com.anytlet;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by user on 4/11/2019.
 */

public class Constants {
    public static String UserIdentity="" ;
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
