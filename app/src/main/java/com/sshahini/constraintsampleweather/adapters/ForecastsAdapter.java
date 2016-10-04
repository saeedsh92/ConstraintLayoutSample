package com.sshahini.constraintsampleweather.adapters;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sshahini.constraintsampleweather.R;
import com.sshahini.constraintsampleweather.models.WeatherInfo;
import com.sshahini.constraintsampleweather.utils.WeatherIconParser;

import java.util.List;

/**
 * Created by saeed on 7/16/16.
 */
public class ForecastsAdapter extends RecyclerView.Adapter<ForecastsAdapter.ForecastViewHolder>{

    private Context context;
    private List<WeatherInfo> weatherInfos;
    private WeatherIconParser weatherIconParser;
    public ForecastsAdapter(Context context, List<WeatherInfo> weatherInfos){
        this.context=context;
        this.weatherInfos=weatherInfos;
        this.weatherIconParser =new WeatherIconParser(context);
    }
    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_abstract_weather,parent,false));
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        WeatherInfo weatherInfo=weatherInfos.get(position);
        holder.minTempTextView.setText(String.valueOf(weatherInfo.getMinTemp())+WeatherInfo.TEMPERATURE_CELSIUS);
        holder.maxTempTextView.setText(String.valueOf(weatherInfo.getMaxTemp())+WeatherInfo.TEMPERATURE_CELSIUS);
        holder.dayNameTextView.setText(weatherInfo.getDate());
        VectorDrawableCompat weatherIcon=weatherIconParser.getIcon(weatherInfo.getId());
        weatherIcon.setTint(ContextCompat.getColor(context,R.color.colorAccent));
        holder.weatherImageView.setImageDrawable(weatherIcon);
    }

    @Override
    public int getItemCount() {
        return weatherInfos.size() ;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder{
        private TextView minTempTextView;
        private TextView maxTempTextView;
        private TextView dayNameTextView;
        private ImageView weatherImageView;
        public ForecastViewHolder(View itemView) {
            super(itemView);
            minTempTextView=(TextView)itemView.findViewById(R.id.historical_weather_min_temp);
            maxTempTextView=(TextView)itemView.findViewById(R.id.historical_weather_max_temp);
            dayNameTextView=(TextView)itemView.findViewById(R.id.histrocal_weather_day_name);
            weatherImageView=(ImageView)itemView.findViewById(R.id.historical_weather_icon);
        }
    }
}
