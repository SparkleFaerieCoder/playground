package iamalexmoss.com.stormy.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final double latitude = 37.8267;
        final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });
        getForecast(latitude, longitude);
        Log.d(TAG, "Main UI code is running!");
    }

    private void getForecast(double latitude, double longitude) {
        //Variables
        String apiKey = "2f27f3f419c3edfa37ea1945e7f8bf19";
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
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
            Toast.makeText(this, "Network is unavailable!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updateDisplay() {
        mTemperatureLabel.setText(mCurrentWeather.getTemperature() + "");
        mTimeLabel.setText("At " + mCurrentWeather.getFormattedTime() + " it will be");
        mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrentWeather.getSummary() + "");

        Drawable drawable = ContextCompat.getDrawable(this, mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
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

}
