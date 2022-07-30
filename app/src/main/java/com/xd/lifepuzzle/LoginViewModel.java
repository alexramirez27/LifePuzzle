package com.xd.lifepuzzle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<List<String>> names;

    public LiveData<List<String>> getNames() {
        if (names == null){
            names = new MutableLiveData<List<String>>();

        }
        return names;
    }

    public void addName(String name) {
        names = new MutableLiveData<>();
        List<String> test = names.getValue();
        test.add(name);

        names.setValue(test);
    }

}
