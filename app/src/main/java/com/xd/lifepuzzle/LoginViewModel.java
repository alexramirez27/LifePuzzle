package com.xd.lifepuzzle;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<List<String>> _names;
    private List<String> temp;

    public LiveData<List<String>> getNames() {
        if (_names == null){
            _names = new MutableLiveData<List<String>>();

        }
        return _names;
    }

    public void addName(String name) {
        temp = _names.getValue();
        if (temp == null){
            temp = new ArrayList<>();
        }
        temp.add(name);
        _names.setValue(temp);
    }

}
