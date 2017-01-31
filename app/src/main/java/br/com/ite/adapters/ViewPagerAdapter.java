package br.com.ite.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import br.com.ite.R;
import br.com.ite.activities.BaseActivity;
import br.com.ite.fragments.AboutFragment;
import br.com.ite.fragments.EventsFragment;
import br.com.ite.fragments.GradesFragment;
import br.com.ite.fragments.NewsFragment;
import br.com.ite.fragments.SolicitationsFragment;

/**
 * Created by leonardo.borges on 09/08/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int[] icons = {
            R.drawable.ic_news,
            R.drawable.ic_event,
            R.drawable.ic_grades,
            R.drawable.ic_solicitations,
            R.drawable.icon
    };

    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {

            if (position == BaseActivity.VIEW_PAGER_OPTIONS.NEWS.ordinal()) {
                return new NewsFragment();

            } else if (position == BaseActivity.VIEW_PAGER_OPTIONS.EVENTS.ordinal()) {
                return new EventsFragment();

            } else if (position == BaseActivity.VIEW_PAGER_OPTIONS.GRADES.ordinal()) {
                return new GradesFragment();

            } else if (position == BaseActivity.VIEW_PAGER_OPTIONS.SOLICITATIONS.ordinal()) {
                return new SolicitationsFragment();

            } else if (position == BaseActivity.VIEW_PAGER_OPTIONS.ABOUT.ordinal()) {
                return new AboutFragment();
            }
        }
        catch (Exception ex) {
            Log.e("ViewPagerAdapter", "getItem()");
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCount() {
        return BaseActivity.VIEW_PAGER_OPTIONS.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable icon = ContextCompat.getDrawable(context, icons[position]);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan is = new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(is, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}