package com.spaktask.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spaktask.R;
import com.spaktask.databinding.ActivityMainBinding;
import com.spaktask.view.adapter.GalleryAdapter;
import com.spaktask.viewmodel.GalleryViewModel;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class MainActivity extends AppCompatActivity {

    // Binding component instance
    private ActivityMainBinding binding;

    //Gallery adapter instance
    private GalleryAdapter galleryAdapter;

    private GalleryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        // initialize view
        initializeView();
    }

    private void initializeView() {
        // Recyclerview init
        binding.rvGaller.setLayoutManager(new LinearLayoutManager(this));
        galleryAdapter = new GalleryAdapter();
        binding.rvGaller.setAdapter(galleryAdapter);

        //Upload image click listener
        binding.fbUpload.setOnClickListener(view -> {
            TedImagePicker.with(this)
                    .start(uri -> {
                        Toast.makeText(this, "URI:" + uri, Toast.LENGTH_SHORT).show();
                    });
        });

        //Get all images click listener

        // Callback for successful image uploading
        viewModel.getStatus().observe(this, status -> {
            Toast.makeText(this, "Status:" + status, Toast.LENGTH_SHORT).show();
        });

        // Callback for getting all images list
        viewModel.getStatus().observe(this, status -> {
            Toast.makeText(this, "Status:" + status, Toast.LENGTH_SHORT).show();
        });

    }

    private void getImagesList() {
        viewModel.getImagesList();
    }

    private void uploadImage(String url) {
        viewModel.uploadImage(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uploadImage(data.toString());
    }
}
