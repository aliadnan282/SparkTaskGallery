package com.spaktask.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.storage.StorageReference;
import com.spaktask.R;
import com.spaktask.databinding.ActivityMainBinding;
import com.spaktask.utils.GridSpacingItemDecoration;
import com.spaktask.view.adapter.GalleryAdapter;
import com.spaktask.viewmodel.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // Binding component instance
    private ActivityMainBinding binding;

    //Gallery adapter instance
    private GalleryAdapter galleryAdapter;

    private GalleryViewModel viewModel;

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
        binding.rvGaller.setLayoutManager(new GridLayoutManager(this,3));
        int spanCount = 3; // 3 columns
        int spacing = 30; // margin
        boolean includeEdge = false;
        binding.rvGaller.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        galleryAdapter = new GalleryAdapter(this);
        binding.rvGaller.setAdapter(galleryAdapter);

        //Upload image click listener
        binding.fbUpload.setOnClickListener(view -> {
            TedImagePicker.with(this)
                    .start(uri -> {
                        uploadImage(uri);
                        Toast.makeText(MainActivity.this, "URI:" + uri, Toast.LENGTH_SHORT).show();
                    });
        });

        // Callback for successful image uploading
        viewModel.getStatus().observe(this, status -> {
            if (status instanceof String)
            Toast.makeText(this, "Status:" + status, Toast.LENGTH_SHORT).show();
            else {
                galleryAdapter.setData((List<StorageReference>) status);
                Log.d(TAG, "initializeView: "+status.toString());
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
        getMenuInflater().inflate(R.menu.menu_referesh,menu);
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
}
