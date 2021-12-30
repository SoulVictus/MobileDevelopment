package com.example.studentcrimeapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcrimeapp.database.Crime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CrimesRecyclerViewAdapter extends RecyclerView.Adapter<CrimesRecyclerViewAdapter.ViewHolder> implements Filterable {
    private CrimeLab crimeLab;
    List<Crime> crimeListToView;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView crimeTitleView;
        private final TextView crimeDateView;

        public ViewHolder(View view) {
            super(view);

            crimeTitleView = (TextView) view.findViewById(R.id.crimeTitleView);
            crimeDateView = (TextView) view.findViewById(R.id.crimeDateView);
        }


        public TextView getTitleTextView() {
            return crimeTitleView;
        }
        public TextView getCrimeDateView() { return crimeDateView; }
    }

    public CrimesRecyclerViewAdapter(CrimeLab crimeLab) {
        this.crimeLab = crimeLab;
        this.crimeListToView = new ArrayList<>();
        crimeListToView.addAll(crimeLab.getCrimes());
    }

    public void refreshListToView() {
       crimeListToView.clear();
       crimeListToView.addAll(crimeLab.getCrimes());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTitleTextView().setText(crimeListToView.get(position).getTitle());
        viewHolder.getCrimeDateView().setText(crimeListToView.get(position).getDate().toString());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crimeIntent = new Intent(viewHolder.itemView.getContext(), CrimePagerActivity.class);
                crimeIntent.putExtra("crimeUUID", crimeListToView.get(viewHolder.getAdapterPosition()).getId().toString());
                viewHolder.itemView.getContext().startActivity(crimeIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return crimeListToView.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Crime> filteredListOfCrimes = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredListOfCrimes.addAll(crimeLab.getCrimes());
            }
            else {
                for (Crime crime : crimeLab.getCrimes()) {
                    if (crime.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListOfCrimes.add(crime);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredListOfCrimes;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            crimeListToView.clear();
            crimeListToView.addAll((Collection<? extends Crime>) results.values);
            notifyDataSetChanged();
        }
    };
}
