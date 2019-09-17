package com.spaktask.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.storage.StorageReference;
import com.spaktask.R;
import com.spaktask.databinding.ActivityMainBinding;
import com.spaktask.utils.AppUtils;
import com.spaktask.utils.GridSpacingItemDecoration;
import com.spaktask.view.adapter.GalleryAdapter;
import com.spaktask.viewmodel.GalleryViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // Binding component instance
    private ActivityMainBinding binding;

    //Gallery adapter instance
    private GalleryAdapter galleryAdapter;

    private GalleryViewModel viewModel;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity__main);
        viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        // initialize view
        initializeView();

        // Populate data
        getImagesList();


    }

    private void initializeView() {
        // Recyclerview init
        binding.rvGaller.setLayoutManager(new GridLayoutManager(this, 3));
        int spanCount = 3; // 3 columns
        int spacing = 30; // margin
        boolean includeEdge = false;
        binding.rvGaller.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        galleryAdapter = new GalleryAdapter(this);
        binding.rvGaller.setAdapter(galleryAdapter);

        //Initialize progressbar
        progressDialog = AppUtils.initProgressDialog(this, getString(R.string.dialog_detail));

        //Upload image click listener
        binding.fbUpload.setOnClickListener(view -> {

            TedImagePicker.with(this)
                    .start(uri -> {
                        CropImage.activity(uri)
                                .start(this);
                        Log.d(TAG, "URI: " + uri);
                    });
        });

        // Callback for successful image uploading
        viewModel.getStatus().observe(this, status -> {
            switch (status.getStatus()) {
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    if (status.getData() instanceof String)
                        Log.d(TAG, "upload image status: " + status);
                    else {
                        galleryAdapter.setData((List<StorageReference>) status.getData());
                        Log.d(TAG, "initializeView: " + status.toString());
                    }
                    //Hide progressbar dialog
                    AppUtils.hideProgressDialog(progressDialog);
                    break;
                case COMPLETE:
                case ERROR:
                    //Hide progressbar dialog
                    AppUtils.hideProgressDialog(progressDialog);
                    break;
            }

        });

    }

    private void getImagesList() {
        viewModel.getImagesList();
    }

    private void uploadImage(Uri uri) {
        viewModel.uploadImage(uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referesh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Get all images click listener
        if (item.getItemId() == R.id.mi_refresh) {
            // Refresh Gallery list
            getImagesList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //Show progress bar dialog
                uploadImage(resultUri);
                // Toast.makeText(MainActivity.this, "URI:" + resultUri, Toast.LENGTH_SHORT).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(TAG, "onActivityResult: ", error);
            }
        }
    }
}
