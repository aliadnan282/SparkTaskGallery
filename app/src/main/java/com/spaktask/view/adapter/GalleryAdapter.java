package com.spaktask.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.spaktask.R;
import com.spaktask.databinding.VhGalleryItemBinding;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolderGallery> {

    // Binding instance
    private VhGalleryItemBinding binding;

    @NonNull
    @Override
    public ViewHolderGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.vh_gallery_item, parent, false);

        return new ViewHolderGallery(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGallery holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderGallery extends RecyclerView.ViewHolder {


        public ViewHolderGallery(VhGalleryItemBinding view) {
            super(view.getRoot());
        }

        public void bind() {

        }
    }
}
