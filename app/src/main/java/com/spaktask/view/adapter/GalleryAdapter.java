package com.spaktask.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.spaktask.R;
import com.spaktask.databinding.VhGalleryItemBinding;
import com.spaktask.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolderGallery> {

    // Binding instance
    private VhGalleryItemBinding binding;
    private Context context;
    private List<StorageReference> listItem = new ArrayList<>();

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vh_gallery__item, parent, false);

        return new ViewHolderGallery(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGallery holder, int position) {
        holder.bind(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public void setData(List<StorageReference> list) {
        this.listItem = list;
        notifyDataSetChanged();
    }

    public class ViewHolderGallery extends RecyclerView.ViewHolder {

        VhGalleryItemBinding localBinding;

        public ViewHolderGallery(VhGalleryItemBinding view) {
            super(view.getRoot());
            this.localBinding = view;
        }

        public void bind(StorageReference storageReference) {
            GlideApp.with(context)
                    .load(storageReference)
                    .placeholder(context.getDrawable(R.mipmap.ic_launcher))
                    .into(localBinding.ivItem);
        }
    }
}
