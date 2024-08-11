package com.example.do_an_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoSliderAdapter extends RecyclerView.Adapter<PhotoSliderAdapter.PhotoViewHolder>{

    private List<photoslider> listphotosliders;

    public PhotoSliderAdapter(List<photoslider> photosliders) {
        this.listphotosliders = photosliders;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_slider,parent,false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        photoslider photoslider= listphotosliders.get(position);
            if (photoslider == null) {
                return;
            }
            holder.imagePhoto.setImageResource(photoslider.getResource_id());

    }

    @Override
    public int getItemCount() {
        if(listphotosliders!=null)
        {
            return listphotosliders.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagePhoto;


        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhoto=itemView.findViewById(R.id.photo_slider);
        }
    }
}
