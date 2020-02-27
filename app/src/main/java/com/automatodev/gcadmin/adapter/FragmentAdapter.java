package com.automatodev.gcadmin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.automatodev.gcadmin.fragment.EntregarFragment;
import com.automatodev.gcadmin.fragment.PreparacaoFragment;
import com.automatodev.gcadmin.fragment.ConfirmarFragment;

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
               return new ConfirmarFragment();
           case 1:
               return new PreparacaoFragment();
           case 2:
               return new EntregarFragment();
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
