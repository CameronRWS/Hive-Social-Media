package com.example.hivefrontend.Hive;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The view model for a hive page
 */
public class HiveViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public HiveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hive activity");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
