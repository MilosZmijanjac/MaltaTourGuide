package com.example.maltatourguide.ui.general;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GeneralViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public GeneralViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Malta");
    }
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> setText(String s) {
        mText.setValue(s); return mText;
    }
}
