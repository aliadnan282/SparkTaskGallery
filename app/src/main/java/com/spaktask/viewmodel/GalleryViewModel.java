package com.spaktask.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GalleryViewModel extends AndroidViewModel {
    public MutableLiveData<String> getStatus() {
        return status;
    }

    private MutableLiveData<String> status=new MutableLiveData<>();
    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }
    public void getImagesList(){
        status.postValue("successfull");
    }
    public void uploadImage(String url){

    }
}
