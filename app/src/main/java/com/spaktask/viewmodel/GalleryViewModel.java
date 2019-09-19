package com.spaktask.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spaktask.utils.AppConstants;
import com.spaktask.utils.StateLiveData;

import java.util.UUID;

public class GalleryViewModel extends ViewModel {

    private FirebaseStorage storage;
    private StorageReference reference;


    // Observer listener instance
    private StateLiveData<Object> status = new StateLiveData<Object>();

    public StateLiveData<Object> getStatus() {
        return status;
    }

    public GalleryViewModel() {
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
    }

    public void getImagesList() {
        status.postLoading();
        StorageReference ref = reference.child(AppConstants.FIREBASE_BUCKET);
        ref.listAll().addOnSuccessListener(listResult -> {
            status.postSuccess(listResult.getItems());
        });
    }

    public void uploadImage(Uri uri) {
        status.postLoading();
        StorageReference ref = reference.child(AppConstants.FIREBASE_BUCKET + UUID.randomUUID().toString());
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    status.postSuccess("successful");

                })
                .addOnFailureListener(e -> {
                    status.postError(e);

                })
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    status.postProgress(progress);

                });
    }
}
