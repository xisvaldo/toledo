package br.com.ite.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;

import br.com.ite.R;
import br.com.ite.adapters.ViewPagerAdapter;
import br.com.ite.utils.PicassoUtils;
import br.com.xisvaldo.android.dialog.AndroidDialog;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class BaseActivity extends AppCompatActivity {

    public enum VIEW_PAGER_OPTIONS {
        NEWS,
        EVENTS,
        GRADES,
        SOLICITATIONS,
        ABOUT;

        public static VIEW_PAGER_OPTIONS getMenuOptionFromInt(int option) {
            return VIEW_PAGER_OPTIONS.values()[option];
        }
    }

    private TabLayout baseTabLayout;
    private ViewPager baseViewPager;
    private Toolbar baseToolbar;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        this.configureViewsAndEvents();
        new PicassoUtils(getApplicationContext());
    }

    private void configureViewsAndEvents() {

        this.baseViewPager = (ViewPager) findViewById(R.id.base_viewpager);
        this.baseTabLayout = (TabLayout) findViewById(R.id.base_tabs);
        this.baseToolbar = (Toolbar) findViewById(R.id.base_toolbar);

        baseToolbar.setNavigationIcon(R.drawable.small_logo_white);
        baseToolbar.setTitle(getResources().getString(R.string.app_name));

        setSupportActionBar(this.baseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        this.baseViewPager.setAdapter(adapter);

        final Drawable backImage = getResources().getDrawable(R.drawable.ic_back);
        backImage.setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        this.baseTabLayout.setupWithViewPager(this.baseViewPager);
        this.baseTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (VIEW_PAGER_OPTIONS.getMenuOptionFromInt(tab.getPosition())) {

                    case NEWS:
                        baseToolbar.setTitle(getResources().getString(R.string.app_name));
                        baseToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon));
                        baseToolbar.setNavigationOnClickListener(null);
                        break;

                    case EVENTS:
                        baseToolbar.setTitle(getResources().getString(R.string.events));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToTimelineClick);
                        break;

                    case GRADES:
                        baseToolbar.setTitle(getResources().getString(R.string.grades));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToTimelineClick);
                        break;

                    case SOLICITATIONS:
                        baseToolbar.setTitle(getResources().getString(R.string.solicitations));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToTimelineClick);
                        break;

                    case ABOUT:
                        baseToolbar.setTitle(getResources().getString(R.string.about));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToTimelineClick);
                        break;
                }
                baseViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View.OnClickListener backToTimelineClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            baseViewPager.setCurrentItem(VIEW_PAGER_OPTIONS.NEWS.ordinal(), true);
        }
    };

    public void changeToolbarTitle(String title) {
        baseToolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        AndroidDialog dialog = new AndroidDialog();
        try {
            dialog.show(this, AndroidDialog.Type.QUESTION, getString(R.string.app_name),
                    getString(R.string.generalExit), new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg != null) {
                                if (msg.what == AndroidDialog.Result.YES.ordinal()) {
                                    finish();
                                }
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
