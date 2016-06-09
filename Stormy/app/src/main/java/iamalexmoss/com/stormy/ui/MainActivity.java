package iamalexmoss.com.stormy.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import iamalexmoss.com.stormy.model.CurrentWeather;
import iamalexmoss.com.stormy.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather mCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        String apiKey = "2f27f3f419c3edfa37ea1945e7f8bf19";
        double latitude = 37.8267;
        double longitude = -122.423;
        String forecastURL = "https://api.forecast.io/forecast/"
                            + apiKey
                            + "/"
                            + latitude
                            + ","
                            + longitude;

        //Check for Network connection
        if (isNetworkAvailable()) {
        //OkHttpClient Request Initialization
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);

            //OkHttpClient Request
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                //On Response
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                        } else {
                            errorAlert();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }

    }

    //Method for Error Alert Message
    private void errorAlert() {
        ErrorDialogFragment dialog = new ErrorDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
    //Method to check Network available
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = true;
        }

        return isAvailable;
    }

    //JSON Object Returned
    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {

        //Forecast - Parent Object
        JSONObject forecast = new JSONObject(jsonData);
        //Timezone variable
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        //Currently - Child object of Forecast
        JSONObject currently = forecast.getJSONObject("currently");

        //Create new instance of CurrentWeather Class
        CurrentWeather currentWeather = new CurrentWeather();

        //That calls on the method and passes in JSON key to SET the new value.
        //Set humidity
        currentWeather.setHumidity(currently.getDouble("humidity"));

        //Set time
        currentWeather.setTime(currently.getLong("time"));

        //Set timezone
        currentWeather.setTimeZone(timezone);

        //Set Icon
        currentWeather.setIcon(currently.getString("icon"));

        //Set Temperature
        currentWeather.setTemperature(currently.getDouble("temperature"));

        //Set Precip Chance
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));

        //Set Summary
        currentWeather.setSummary(currently.getString("summary"));

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }

}
