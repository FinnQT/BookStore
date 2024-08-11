package com.example.do_an_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ManagerBookAdapter extends RecyclerView.Adapter<ManagerBookAdapter.BookMangerViewHolder>{

    private List<Book_demo> mlistBookDemo;
    private Activity mActivity;

    public ManagerBookAdapter(List<Book_demo> mlistBookDemo, Activity activity) {
        this.mlistBookDemo = mlistBookDemo;
        this.mActivity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookMangerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_manager,parent,false);

        return new BookMangerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMangerViewHolder holder, int position) {

        Book_demo book_demo=mlistBookDemo.get(position);
        if(book_demo==null){
            return;
        }
        holder.author.setText(book_demo.getAuthor());
        holder.amount.setText(String.valueOf(book_demo.getAmount()));
        holder.id.setText(book_demo.getId());
        holder.name.setText(book_demo.getName());
        holder.price.setText(String.valueOf(book_demo.getPrice()));
        holder.type.setText(book_demo.getType());


        if(book_demo.getImage()!=null&&!book_demo.getImage().equals(""))
        {
            Glide.with(mActivity).load(book_demo.getImage()).error(R.drawable.ic_no_image).into(holder.load_image);
        }else
        {
            holder.load_image.setImageResource(R.drawable.ic_no_image);
        }

        holder.btn_delete_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteBook(book_demo);
            }
        });
        holder.btn_update_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mActivity);
                dialog.setContentView(R.layout.dialog_update_newbook);
                TextView id_book_update=dialog.findViewById(R.id.edt_update_id_book);
                EditText bookname_update=dialog.findViewById(R.id.edt_update_name_book);
                EditText author_update=dialog.findViewById(R.id.edt_update_author);
                EditText type_update=dialog.findViewById(R.id.edt_update_type);
                EditText amount_update=dialog.findViewById(R.id.edt_update_amount);
                EditText price_update=dialog.findViewById(R.id.edt_update_price);
                EditText image_update=dialog.findViewById(R.id.edt_update_image);
                Button btn_cancle_update=dialog.findViewById(R.id.btn_update_cancle_add);
                Button btn_oke_update=dialog.findViewById(R.id.btn_oke_update);


                id_book_update.setText(book_demo.getId());
                bookname_update.setText(book_demo.getName());
                author_update.setText(book_demo.getAuthor());
                type_update.setText(book_demo.getType());
                amount_update.setText(String.valueOf(book_demo.getAmount()));
                price_update.setText(String.valueOf(book_demo.getPrice()));
                image_update.setText(book_demo.getImage());




                btn_cancle_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_oke_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_add=bookname_update.getText().toString().trim();
                        String author_add=author_update.getText().toString().trim();
                        String type_add=type_update.getText().toString().trim();
                        String amount_add=amount_update.getText().toString().trim();
                        int value_amount=0;
                        if(!"".equals(amount_add))
                        {
                            value_amount=Integer.parseInt(amount_add);
                        }
                        String pirce_add= price_update.getText().toString().trim();
                        float value_price= 0;
                        if(!"".equals(pirce_add))
                        {
                            value_price=Float.parseFloat(pirce_add);
                        }
                        String image_add= image_update.getText().toString().trim();
                        Book_demo bookupdate=new Book_demo(value_amount,author_add,book_demo.getId(),image_add,name_add,value_price,type_add);
                        onClickokeUpdate(book_demo,bookupdate,dialog);
                    }
                });
                dialog.show();

            }
        });


    }

    private void onClickDeleteBook(Book_demo book_demo) {

       new AlertDialog.Builder(mActivity)
               .setTitle("xóa sản phẩm")
               .setMessage("Bạn có chắc chắn xóa cuốn sách này hay không")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       FirebaseDatabase database=FirebaseDatabase.getInstance();
                       DatabaseReference myRef=database.getReference("sachdemo");

                       myRef.child(book_demo.getId()).removeValue(new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                               Toast.makeText(mActivity, "Delete data success", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }).setNegativeButton("CANCLE",null)
               .show();

    }

    private void onClickokeUpdate(Book_demo book_demo, Book_demo bookupdate, Dialog dialog) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("sachdemo");
        HashMap<String,Object> result= new HashMap<>();
        result.put("amount",bookupdate.getAmount());
        result.put("author",bookupdate.getAuthor());
        result.put("image",bookupdate.getImage());
        result.put("name",bookupdate.getName());
        result.put("price",bookupdate.getPrice());
        result.put("type",bookupdate.getType());
        myRef.child(book_demo.getId()).updateChildren(result);
        dialog.dismiss();
        Toast.makeText(mActivity, "update success", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        if(mlistBookDemo!=null)
        {
            return mlistBookDemo.size();
        }
        return 0;
    }

    public class BookMangerViewHolder extends RecyclerView.ViewHolder {

        private TextView amount;
        private TextView author;
        private TextView id;
        private TextView name;
        private TextView price;
        private TextView type;
        private ImageView load_image;
        private Button btn_update_book;
        private Button btn_delete_book;

        public BookMangerViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.tv_amount);
            author=itemView.findViewById(R.id.tv_author);
            id=itemView.findViewById(R.id.tv_id_book);
            name=itemView.findViewById(R.id.tv_name_book);
            price=itemView.findViewById(R.id.tv_price);
            type=itemView.findViewById(R.id.tv_type);
            load_image=itemView.findViewById(R.id.image_book_manager);
            btn_update_book=itemView.findViewById(R.id.btn_update_info_book);
            btn_delete_book=itemView.findViewById(R.id.btn_delete_book);


        }
    }
}
