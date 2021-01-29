package com.example.morina.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.morina.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_booking, container, false);

        pageViewModel.getEntries().getValue().add(
            new HistoryEntry("Finished",
                "18 April 2020",
                "Dagestan",
                "Azerbaijan",
                "12:05",
                "Economy",
                "Visa"
            )
        );

        pageViewModel.getEntries().getValue().add(
            new HistoryEntry("Canceled",
                "19 April 2020",
                "Azerbaijan",
                "Dagestan",
                "05:12",
                "Platinum",
                "MasterCard"
            )
        );

        pageViewModel.getEntries().getValue().add(
            new HistoryEntry("Finished",
                "20 April 2020",
                "Italy",
                "France",
                "21:50",
                "Economy",
                "MIR"
            )
        );


        final ListView lst = root.findViewById(R.id.list_view_history);
        pageViewModel.getEntries().observe(getViewLifecycleOwner(), list -> {
            ArrayAdapter<HistoryEntry> a = new HistoryEntryAdapter(
                PlaceholderFragment.this.getActivity(),
                R.layout.list_item_history,
                list
            );
            lst.setAdapter(a);
            a.notifyDataSetChanged();
        });

        return root;
    }
}