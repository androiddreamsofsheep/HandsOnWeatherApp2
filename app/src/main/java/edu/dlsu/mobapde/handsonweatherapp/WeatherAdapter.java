package edu.dlsu.mobapde.handsonweatherapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by courtneyngo on 06/10/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    ArrayList<Weather> weather;
    private OnItemClickListener onItemClickListener;
    /*Variation*/
    boolean showTemperature = false;
    /*Variation*/

    int clickedPosition=0;

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
        notifyDataSetChanged();
    }

    /*Variation*/
    public void showTemperature(boolean showTemperature){
        this.showTemperature = showTemperature;
        notifyDataSetChanged();
    }
    /*Variation*/

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WeatherAdapter(ArrayList<Weather> weather){
        this.weather = weather;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, final int position) {
        Weather currentWeather = weather.get(position);
        holder.tvDate.setText(currentWeather.getDate());
        holder.ivIcon.setImageResource(currentWeather.getIcon());
        holder.container.setTag(currentWeather);

        /*Variation*/
        if(showTemperature){
            holder.tvTemp.setText(String.valueOf(currentWeather.getTemp()) + (char) 0x00B0 + "C");
        }else{
            holder.tvTemp.setText("");
        }
        /*Variation*/

        if(position==clickedPosition){
            holder.container.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }else{
            holder.container.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick((Weather) view.getTag());
                int temp = clickedPosition;
                clickedPosition = position;
                notifyItemChanged(temp);
                notifyItemChanged(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate, tvTemp;
        ImageView ivIcon;
        /*Variation*/
        View container;
        /*Variation*/

        public WeatherViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            container = itemView.findViewById(R.id.container);
        }

    }


    public interface OnItemClickListener{
        public void onItemClick(Weather w);
    }

}
