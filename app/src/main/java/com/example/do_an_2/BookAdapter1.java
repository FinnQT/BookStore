package com.example.do_an_2;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class BookAdapter1 extends RecyclerView.Adapter<BookAdapter1.BookViewHolder> {
    private List<Book_demo> mbook;
    private Activity mActivity;
    private final IClickItemListener iClickItemListener;



    public BookAdapter1(List<Book_demo> mbook, Activity activity, IClickItemListener listener) {
        this.mbook = mbook;
        this.mActivity=activity;
        this.iClickItemListener=listener;

    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book1,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
    final Book_demo book=mbook.get(position);
    if(book==null) {
        return;
    }

    if(book.getImage()!=null&&!book.getImage().equals(""))
    {
        Glide.with(mActivity).load(book.getImage()).error(R.drawable.ic_no_image).into(holder.imgBook);
    }else
    {
        holder.imgBook.setImageResource(R.drawable.ic_no_image);
    }
    holder.tvTitle.setText(book.getName());
    holder.layoutitem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            iClickItemListener.onClickitem(book);
        }
    });
    }


    @Override
    public int getItemCount() {
        if(mbook!=null)
        {
            return mbook.size();
        }
        return 0;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgBook;
        private TextView tvTitle;
        private LinearLayout layoutitem;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutitem=itemView.findViewById(R.id.layout_item);
            imgBook=itemView.findViewById(R.id.img_book);
            tvTitle=itemView.findViewById(R.id.tv_title);

        }
    }

}
