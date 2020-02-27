package com.automatodev.gcadmin.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrdersActivity extends AppCompatActivity  {
    public static boolean status;
    private TabLayout tab_orders;
    private ViewPager view_order;
    private FragmentAdapter fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        tab_orders = findViewById(R.id.tab_orders);
        view_order = findViewById(R.id.view_orders);

        tab_orders.addTab(tab_orders.newTab().setIcon(R.drawable.ic_list_icon));
        tab_orders.addTab(tab_orders.newTab().setIcon(R.drawable.ic_cardapio_sgv));
        tab_orders.addTab(tab_orders.newTab().setIcon(R.drawable.ic_send_black));
        tab_orders.setTabGravity(TabLayout.GRAVITY_FILL);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),tab_orders.getTabCount());
        view_order.setAdapter(fragmentAdapter);
        view_order.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_orders));

        tab_orders.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_order.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }
}
