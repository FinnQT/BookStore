package com.example.do_an_2;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ViewPager2Adapter extends FragmentStateAdapter {

    private int dangnhap=0;
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new home_fragment();

            case 1:

                return new personal_fragment();

            case 2:

                return new cart_fragment();

            default:
                return new home_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
