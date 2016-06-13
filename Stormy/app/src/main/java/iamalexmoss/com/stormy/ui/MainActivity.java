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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamalexmoss.com.stormy.model.Current;
import iamalexmoss.com.stormy.R;
import iamalexmoss.com.stormy.model.Daily;
import iamalexmoss.com.stormy.model.Forecast;
import iamalexmoss.com.stormy.model.Hourly;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Forecast mForecastWeather;

    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        final double latitude = 45.51;
        final double longitude = -122.675;

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

            //Animate refresh icon
            animateRefreshIcon();

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animateRefreshIcon();
                        }
                    });
                    errorAlert();
                }
                //On Response

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animateRefreshIcon();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mForecastWeather = parseForecastDetails(jsonData);
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
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private void animateRefreshIcon() {
        //Toggle refresh icon views
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }  else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Current current = mForecastWeather.getCurrent();
        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary() + "");

        Drawable drawable = ContextCompat.getDrawable(this, current.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

     private Forecast parseForecastDetails(String jsonData) throws JSONException{
         Forecast forecast = new Forecast();

         forecast.setCurrent(getCurrentForecast(jsonData));
         forecast.setHourlyForecast(getHourlyForecast(jsonData));
         forecast.setDailyForecast(getDailyForecast(jsonData));
         return forecast;
     }

    private Daily[] getDailyForecast(String jsonData) throws JSONException {
        //Forecast - JSON Parent Object
        JSONObject forecast = new JSONObject(jsonData);
        //Timezone variable
        String timezone = forecast.getString("timezone");

        //Access Hourly Object
        JSONObject daily = forecast.getJSONObject("daily");

        //Access data array
        JSONArray data = daily.getJSONArray("data");

        //Create new array to store json data
        Daily[] days = new Daily[data.length()];

        //For each object in the array
        for (int i = 0; i < data.length(); i ++) {
            JSONObject jsonDay = data.getJSONObject(i);

            Daily day = new Daily();
            //Get the summary
            day.setSummary(jsonDay.getString("summary"));
            day.setTemperature(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTimeZone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hourly[] getHourlyForecast(String jsonData) throws JSONException {
        //Forecast - JSON Parent Object
        JSONObject forecast = new JSONObject(jsonData);

        //Timezone variable
        String timezone = forecast.getString("timezone");

        //Access Hourly Object
        JSONObject hourly = forecast.getJSONObject("hourly");

        //Access data array
        JSONArray data = hourly.getJSONArray("data");

        //Create new array to store json data
        Hourly[] hours = new Hourly[data.length()];

        //For each object in the array
        for (int i = 0; i < data.length(); i ++) {
            JSONObject jsonHour = data.getJSONObject(i);

            Hourly hour = new Hourly();
            //Get the summary
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);

            hours[i] = hour;
        }

        return hours;
    }


    //JSON Object Returned for Current Weather
    private Current getCurrentForecast(String jsonData) throws JSONException {

        //Forecast - Parent Object
        JSONObject forecast = new JSONObject(jsonData);
        //Timezone variable
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        //Currently - Child object of Forecast
        JSONObject currently = forecast.getJSONObject("currently");

        //Create new instance of Current Class
        Current currentWeather = new Current();
        //That calls on the method and passes in JSON key to SET the new value.

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setTimeZone(timezone);
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
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
