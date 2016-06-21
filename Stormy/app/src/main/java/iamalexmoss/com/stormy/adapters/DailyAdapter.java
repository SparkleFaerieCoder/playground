package iamalexmoss.com.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import iamalexmoss.com.stormy.R;
import iamalexmoss.com.stormy.model.Daily;

/**
 * Created by amoss on 6/14/2016.
 */
public class DailyAdapter extends BaseAdapter {

    private Context mContext;
    private Daily[] mDailyForecast;

    //Constructor
    public DailyAdapter(Context context, Daily[] days) {
        mContext = context;
        mDailyForecast = days;
    }

    //Methods
    @Override
    public int getCount() {
        return mDailyForecast.length;
    }

    @Override
    public Object getItem(int position) {
        return mDailyForecast[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            // for each brand new item view
            //Create LayoutInflater object takes xml layout and turn it into a view in code.
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.daily_list_item, parent, false);

            //Initialize ViewHolder
            holder = new ViewHolder();

            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayNameLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Daily daily = mDailyForecast[position];
        holder.iconImageView.setImageResource(daily.getIconId());
        holder.temperatureLabel.setText(daily.getTemperatureMax() + "");

        if (position == 0) {
            holder.dayNameLabel.setText("Today");
        }else{
            holder.dayNameLabel.setText(daily.getDayOfTheWeek());
        }

        return convertView;

    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayNameLabel;
    }
}
