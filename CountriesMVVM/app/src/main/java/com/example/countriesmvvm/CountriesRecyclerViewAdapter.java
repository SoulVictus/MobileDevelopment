package com.example.countriesmvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countriesmvvm.database.Country;

import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>{
    private List<Country> allCountries;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView countryNameTextView;
        private TextView countryCapitolTextView;

        public ViewHolder(View view) {
            super(view);

            countryNameTextView = (TextView) view.findViewById(R.id.countryNameView);
            countryCapitolTextView = (TextView) view.findViewById(R.id.countryCapitolView);
        }

        public TextView getCountryNameTextView() {
            return countryNameTextView;
        }

        public TextView getCountryCapitolTextView() {
            return countryCapitolTextView;
        }
    }

    public CountriesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setAllCountries(List<Country> list) {
        this.allCountries = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_row, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCountryNameTextView().setText(allCountries.get(position).getCountryName());
        viewHolder.getCountryCapitolTextView().setText(allCountries.get(position).getCountryCapitol());
    }

    @Override
    public int getItemCount() {
        if (allCountries != null) {
            return allCountries.size();
        }
        else
            return 0;
    }

}
