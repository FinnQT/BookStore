package com.example.do_an_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentViewHolder>{
    private List<Commment> mlist;

    public AdapterComment(List<Commment> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Commment comment=mlist.get(position);
        if(comment==null){
            return;
        }
        holder.tv_nameOfcomment.setText(comment.getUid());
        holder.tv_noidungOfcomment.setText(comment.text);
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
        {
            return mlist.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nameOfcomment;
        TextView tv_noidungOfcomment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameOfcomment=itemView.findViewById(R.id.tv_name_comment);
            tv_noidungOfcomment=itemView.findViewById(R.id.tv_noidung_comment);
        }
    }
}
