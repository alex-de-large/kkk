package com.example.morina.ui.booking;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private final MutableLiveData<List<HistoryEntry>> entries = new MutableLiveData<>();

    public PageViewModel() {
        entries.setValue(new ArrayList<>());
    }

    public PageViewModel(List<HistoryEntry> entries) {
        this.entries.setValue(entries);
    }

    public MutableLiveData<List<HistoryEntry>> getEntries() {
        return entries;
    }
}