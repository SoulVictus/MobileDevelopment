package com.example.studentcrimeapp;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class CrimePagerAdapter extends RecyclerView.Adapter<CrimePagerAdapter.ViewHolder> {
    private CrimeLab crimeLab;
    private ViewPager2 viewPager;
    private FragmentManager fragmentManager;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox solvedCheckBox;
        private TextView titleTextView;
        private EditText titleEditText;
        private Button dateEditButton;
        private Button deleteButton;
        private Button firstButton;
        private Button lastButton;

        public ViewHolder(View view) {
            super(view);

            solvedCheckBox = (CheckBox) view.findViewById(R.id.isSolvedCheckBox);
            titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            titleEditText = (EditText) view.findViewById(R.id.titleEditText);
            dateEditButton = (Button) view.findViewById(R.id.dateEditButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            firstButton = (Button) view.findViewById(R.id.firstItemButton);
            lastButton = (Button) view.findViewById(R.id.lastItemButton);
        }

        public CheckBox getSolvedCheckBox() { return solvedCheckBox; }
        public TextView getTitleTextView() { return titleTextView; }
        public EditText getTitleEditText() { return titleEditText; }
        public Button getDateEditButton() { return dateEditButton; }
        public Button getDeleteButton() { return deleteButton; }
        public Button getFirstButton() { return firstButton; }
        public Button getLastButton() { return lastButton; }
    }

    public CrimePagerAdapter(CrimeLab crime, ViewPager2 viewPager, FragmentManager fragmentManager) {
        this.crimeLab = crime;
        this.viewPager = viewPager;
        this.fragmentManager = fragmentManager;
        setDatePicker();
    }

    private void setDatePicker() {
        dateTimeFragment = (SwitchDateTimeDialogFragment) fragmentManager.findFragmentByTag("TAG_DATETIME_FRAGMENT");
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    "DateTime",
                    "Ok",
                    "Cancel"
            );
        }
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {

        }
    }

    @Override
    public CrimePagerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_crime, viewGroup,false);

        return new CrimePagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CrimePagerAdapter.ViewHolder viewHolder, final int position) {
        Crime currentCrime = crimeLab.getCrimes().get(position);

        viewHolder.getDateEditButton().setText(currentCrime.getDate());
        viewHolder.getTitleTextView().setText(currentCrime.getTitle());
        viewHolder.getSolvedCheckBox().setChecked(currentCrime.getIsSolved());

        if (position == 0)
            viewHolder.getFirstButton().setEnabled(false);
        else
            viewHolder.getFirstButton().setEnabled(true);

        if (position == getItemCount()-1)
            viewHolder.getLastButton().setEnabled(false);
        else
            viewHolder.getLastButton().setEnabled(true);

        viewHolder.getSolvedCheckBox().setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentCrime.setSolved(isChecked);
            }
        });

        viewHolder.getTitleEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                currentCrime.setTitle(v.getText().toString());
                viewHolder.getTitleTextView().setText(currentCrime.getTitle());
                return false;
            }
        });

        viewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crimeLab.deleteCrime(currentCrime.getId());
                int deletedPosition = viewHolder.getBindingAdapterPosition();
                viewHolder.getBindingAdapter().notifyItemRemoved(deletedPosition);
                if (deletedPosition == 0)
                    viewHolder.getBindingAdapter().notifyItemChanged(0);
                if (deletedPosition == getItemCount())
                    viewHolder.getBindingAdapter().notifyItemChanged(getItemCount()-1);
            }
        });

        viewHolder.getFirstButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        viewHolder.getLastButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItemCount()-1);
            }
        });

        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                currentCrime.setDate(date);
                viewHolder.getDateEditButton().setText(date.toString());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists

            }
        });

        viewHolder.getDateEditButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar().getTime());
                dateTimeFragment.show(fragmentManager, "TAG_DATETIME_FRAGMENT");
            }
        });


    }

    @Override
    public int getItemCount() {
        return crimeLab.getCrimes().size();
    }
}
