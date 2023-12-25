package com.example.btl_nhom2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<Boolean> dataChanged = new MutableLiveData<>();

    public LiveData<Boolean> getDataChanged() {
        return dataChanged;
    }

    public void notifyDataChanged() {
        dataChanged.setValue(true);
    }
}
