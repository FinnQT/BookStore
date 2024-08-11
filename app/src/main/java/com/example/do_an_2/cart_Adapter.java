package com.example.do_an_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class cart_Adapter extends RecyclerView.Adapter<cart_Adapter.CartHolder>{

    private List<Book_demo1> mbook;
    private Activity mActivity;


    public cart_Adapter(List<Book_demo1> mbook, Activity mActivity) {
        this.mbook = mbook;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new  CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        final Book_demo1 book=mbook.get(position);
        if(book==null) {
            return;
        }

        holder.checkBox_buycart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    BookCheked(book.getId());
                }else{
                    BookUnChecked(book.getId());
                }

            }
        });
        holder.btn_delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteBookCart(book.getId());
            }
        });
        if(book.getImage()!=null&&!book.getImage().equals(""))
        {
            Glide.with(mActivity).load(book.getImage()).error(R.drawable.ic_no_image).into(holder.loadimage_cart);
        }else
        {
            holder.loadimage_cart.setImageResource(R.drawable.ic_no_image);
        }
        holder.name_cart.setText(book.getName());
        holder.price_cart.setText(String.valueOf(book.getPrice()));
        holder.amount_cart.setText(String.valueOf(book.getAmount()));
        holder.btn_down_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownAmountCart(book.getId(),book.getAmount());
            }
        });
        holder.btn_up_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpAmountCart(book.getId(),book.getAmount());
            }
        });

    }

    private void BookUnChecked(String id) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        HashMap<String,Object> result= new HashMap<>();
        result.put("check",false);
        myRef.child(user.getUid()).child(id).updateChildren(result);
    }

    private void BookCheked(String id) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        HashMap<String,Object> result= new HashMap<>();
        result.put("check",true);
        myRef.child(user.getUid()).child(id).updateChildren(result);
    }

    private void UpAmountCart(String id, int amount) {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        HashMap<String,Object> result= new HashMap<>();
        result.put("amount",amount+1);
        myRef.child(user.getUid()).child(id).updateChildren(result);
    }

    private void DownAmountCart(String id, int amount) {
        if(amount>1){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");
        HashMap<String,Object> result= new HashMap<>();
        result.put("amount",amount-1);
        myRef.child(user.getUid()).child(id).updateChildren(result);}
    }

    private void DeleteBookCart(String id) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cart");

        myRef.child(user.getUid()).child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(mActivity, "Delete item cart success", Toast.LENGTH_SHORT).show();
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

    public  class CartHolder extends RecyclerView.ViewHolder {


        private CheckBox checkBox_buycart;
        private Button btn_delete_cart;
        private ImageView loadimage_cart;
        private TextView name_cart;
        private TextView price_cart;
        private TextView amount_cart;
        private Button btn_down_cart;
        private Button btn_up_cart;




        public CartHolder(@NonNull View itemView) {
            super(itemView);
            checkBox_buycart=itemView.findViewById(R.id.checkbox_item);
            btn_delete_cart=itemView.findViewById(R.id.btn_delete_cart);
            loadimage_cart=itemView.findViewById(R.id.img_cartBook);
            name_cart=itemView.findViewById(R.id.tv_name_book_cart);
            price_cart=itemView.findViewById(R.id.tv_price_cart);
            amount_cart=itemView.findViewById(R.id.tv_amount_cart);
            btn_down_cart=itemView.findViewById(R.id.btn_down_cart);
            btn_up_cart=itemView.findViewById(R.id.btn_up_cart);


        }
    }

}
