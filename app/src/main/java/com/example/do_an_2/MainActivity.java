package com.example.do_an_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME=0;
    private static final int FRAGMENT_PERSONAL=1;
    private static final int FRAGMENT_CART=2;
    private static final int FRAGMENT_SAHCBANCHAY=3;
    private static final int FRAGMENT_SACHMOI=4;
    private static final int FRAGMENT_SACHTHIEUNHI=5;


    private int mCurrentFragment= FRAGMENT_HOME;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private ViewPager2 viewPager2;
    private ViewPager2Adapter viewPager2Adapter;
    private SearchView searchView;
//chon anh tu thu vien
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewpager2
        viewPager2=findViewById(R.id.viewpager2);
        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        //XỬ LÝ VUỐT VIEWPAGER2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        mCurrentFragment=FRAGMENT_HOME;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        mCurrentFragment=FRAGMENT_PERSONAL;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_personal).setChecked(true);
                        break;
                    case 2:
                        mCurrentFragment=FRAGMENT_CART;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_card).setChecked(true);
                        break;
                }
            }
        });

        // XỬ LÝ ICON MENU MỞ TOOLBAR, SAU ĐÓ XỬ LÝ CLICK DANH SÁCH CỦA NAVIGATION LEFT VIEW
        drawerLayout=findViewById(R.id.drawer_Layout);
        toolbar=findViewById(R.id.tool_bar);
        navigationView=findViewById(R.id.navigation_view_left);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




        //menu left



        // xu ly bottomnavigation
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.bottom_home){
                    if(mCurrentFragment!=FRAGMENT_HOME){
                        viewPager2.setCurrentItem(0);
                        mCurrentFragment=FRAGMENT_HOME;

                    }

                } else if(id==R.id.bottom_personal){
                    if(mCurrentFragment!=FRAGMENT_PERSONAL){;
                        viewPager2.setCurrentItem(1);
                        mCurrentFragment=FRAGMENT_PERSONAL;

                    }

                }else if(id==R.id.bottom_card){
                    if(mCurrentFragment!=FRAGMENT_CART){
                        viewPager2.setCurrentItem(2);
                        mCurrentFragment=FRAGMENT_CART;
                    }
                }
                return true;
            }
        });
    }

    //xử lý sự kiện click cho danh sách navigation trong menu left

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.van_hoc_vn)
        {
            Intent intent= new Intent(this,vanhocvn.class);
            startActivity(intent);

        } else if(id==R.id.van_hoc_nuoc_ngoai)
        {
            Intent intent= new Intent(this,vanhocnuocngoai.class);
            startActivity(intent);
        } else if(id==R.id.sach_thieuNhi)
        {
            Intent intent= new Intent(this,sachthieunhi.class);
            startActivity(intent);
        } else if(id==R.id.sach_ban_chay)
        {
            Intent intent= new Intent(this,QuanLySanPham.class);
            startActivity(intent);
        } else if(id==R.id.sach_moi_xb)
        {
            Intent intent= new Intent(this,sachmoi.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

// xu ly nut back
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{ super.onBackPressed(); }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mainmenu,menu);
//        searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
////        searchView.seton
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
////        searchView.setMaxWidth(Integer.MAX_VALUE);
//        return true;
//    }
}


