package com.automatodev.gcadmin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.automatodev.gcadmin.fragment.DeliveredFragment;
import com.automatodev.gcadmin.fragment.PendingFragmennt;

public class FragmentAdapter extends FragmentPagerAdapter {
    int tabs;
    public FragmentAdapter(@NonNull FragmentManager fm, int tabs ){
        super(fm);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new PendingFragmennt();
           case 1:
               return new DeliveredFragment();
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
