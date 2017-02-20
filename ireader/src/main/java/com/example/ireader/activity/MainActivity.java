package com.example.ireader.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.ireader.R;
import com.example.ireader.fragment.CurrencyFragment;
import com.example.ireader.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String[] TITLES = {
            "首页","最新","排行","分类","全本"
    };

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView tvTitle;
    @Bind(R.id.login)
    Button loginBtn;
    @Bind(R.id.register)
    Button registerBtn;

    @Bind(R.id.tab)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private HomeFragment homeFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar(Toolbar toolbar, boolean isBack) {
        super.initToolBar(toolbar, isBack);
        tvTitle.setText(getResources().getString(R.string.app_name));
    }

    @Override
    protected void initView() {
        initToolBar(toolbar,false);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(CurrencyFragment.getInstance(1));
        fragments.add(CurrencyFragment.getInstance(2));
        fragments.add(CurrencyFragment.getInstance(3));
        fragments.add(CurrencyFragment.getInstance(4));


        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOffscreenPageLimit(fragments.size());
        SelectorAdapter adapter = new SelectorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.login)
    public void onClickLogin(){
        startActivity(new Intent(this,Two.class));
    }

    @OnClick(R.id.register)
    public void onClickRegister(){

    }

    private class SelectorAdapter extends FragmentStatePagerAdapter {

        public SelectorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
