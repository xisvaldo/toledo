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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

import br.com.ite.R;
import br.com.ite.adapters.ViewPagerAdapter;
import br.com.ite.fragments.GradesFragment;
import br.com.ite.fragments.LoginFragment;
import br.com.ite.fragments.NotificationsFragment;
import br.com.ite.utils.PicassoUtils;
import br.com.ite.utils.UserStorage;
import br.com.xisvaldo.android.dialog.AndroidDialog;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class BaseActivity extends AppCompatActivity {

    public enum VIEW_PAGER_OPTIONS {
        NEWS,
        EVENTS,
        GRADES,
        SOLICITATIONS;

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

        final Drawable loginLogout = getResources().getDrawable(R.drawable.ic_login_logout);
        loginLogout.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        baseToolbar.setNavigationIcon(loginLogout);

        setSupportActionBar(this.baseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        baseToolbar.setTitle(getString(R.string.news));

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
                        baseToolbar.setTitle(getString(R.string.news));
                        baseToolbar.setNavigationIcon(loginLogout);
                        baseToolbar.setNavigationOnClickListener(loginLogoutClick);
                        break;

                    case EVENTS:
                        baseToolbar.setTitle(getResources().getString(R.string.events));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToNewsClick);
                        break;

                    case GRADES:
                        baseToolbar.setTitle(getResources().getString(R.string.grades));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToNewsClick);
                        break;

                    case SOLICITATIONS:
                        baseToolbar.setTitle(getResources().getString(R.string.solicitations));
                        baseToolbar.setNavigationIcon(backImage);
                        baseToolbar.setNavigationOnClickListener(backToNewsClick);
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

        baseToolbar.setNavigationOnClickListener(loginLogoutClick);
    }

    private View.OnClickListener loginLogoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (UserStorage.isLogged(getApplicationContext())) {
                try {
                    AndroidDialog.show(BaseActivity.this,
                            AndroidDialog.Type.QUESTION,
                            getResources().getString(R.string.app_name),
                            getResources().getString(R.string.logoutConfirmation),
                            new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);

                                    if (msg.what == AndroidDialog.Result.YES.ordinal()) {
                                        logout();
                                    }
                                }
                            }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                new LoginFragment().show(getSupportFragmentManager(), "LOGIN_FRAGMENT");
            }
        }
    };

    private View.OnClickListener backToNewsClick = new View.OnClickListener() {
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

            if (getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:" + R.id.base_viewpager + ":" + baseViewPager.getCurrentItem())
                    instanceof GradesFragment) {
                baseToolbar.setTitle(getResources().getString(R.string.grades));
            }

            getSupportFragmentManager().popBackStack();
            return;
        }

        try {
            AndroidDialog.show(this, AndroidDialog.Type.QUESTION, getString(R.string.app_name),
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        Drawable drawable = menu.findItem(R.id.menu_notification_item).getIcon();

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        menu.findItem(R.id.menu_notification_item).setIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!UserStorage.isLogged(getApplicationContext())) {

            try {
                AndroidDialog.show(this,
                        AndroidDialog.Type.INFO,
                        getString(R.string.app_name),
                        getString(R.string.loginRequired),
                        new Handler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            NotificationsFragment fragment = new NotificationsFragment();
            fragment.show(getSupportFragmentManager(), "DIALOG");
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        UserStorage.clearData(getApplicationContext());
    }

    public void goBackToNews() {
        baseViewPager.setCurrentItem(VIEW_PAGER_OPTIONS.NEWS.ordinal(), true);
    }
}
