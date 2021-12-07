package com.example.studentcrimeapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class CrimesRecyclerViewAdapter extends RecyclerView.Adapter<CrimesRecyclerViewAdapter.ViewHolder> {
    private CrimeLab crimeLab;
    private Long currentCrimePosition;
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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTitleTextView().setText(crimeLab.getCrimes().get(position).getTitle());
        viewHolder.getCrimeDateView().setText(crimeLab.getCrimes().get(position).getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crimeIntent = new Intent(viewHolder.itemView.getContext(), CrimePagerActivity.class);
                crimeIntent.putExtra("crimeUUID", crimeLab.getCrimes().get(viewHolder.getAdapterPosition()).getId().toString());
                viewHolder.itemView.getContext().startActivity(crimeIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return crimeLab.getCrimes().size();
    }
}
