package com.spaktask.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spaktask.utils.AppConstants;

import java.util.UUID;

public class GalleryViewModel extends AndroidViewModel {

    private FirebaseStorage storage;
    private StorageReference reference;

    public MutableLiveData<Object> getStatus() {
        return status;
    }
    // Observer listener instance
    private MutableLiveData<Object> status = new MutableLiveData<>();

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
    }

    public void getImagesList() {
        StorageReference ref = reference.child(AppConstants.FIREBASE_BUCKET);
        ref.listAll().addOnSuccessListener(listResult -> {
            status.postValue(listResult.getItems());
        });
    }

    public void uploadImage(Uri uri) {
        StorageReference ref = reference.child(AppConstants.FIREBASE_BUCKET + UUID.randomUUID().toString());
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    status.postValue("successfull");

                })
                .addOnFailureListener(e -> {
                    status.postValue(e.getLocalizedMessage());

                })
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    status.postValue(String.valueOf(progress));

                });
    }
}
