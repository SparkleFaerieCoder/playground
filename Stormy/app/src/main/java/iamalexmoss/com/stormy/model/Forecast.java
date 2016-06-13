package iamalexmoss.com.stormy.model;

/**
 * Created by amoss on 6/13/2016.
 */
public class Forecast {
    private Current mCurrent;
    private Hourly[] mHourlyForecast;
    private Daily[] mDailyForecast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hourly[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hourly[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Daily[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Daily[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }
}
