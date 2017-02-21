package br.com.ite.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ite.R;
import br.com.ite.models.Solicitation;

/**
 * Created by leonardo.borges on 20/02/2017.
 */
public class SolicitationSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private ArrayList<Solicitation> solicitations;

    public SolicitationSpinnerAdapter(Fragment fragment, ArrayList<Solicitation> solicitations) {
        this.context = fragment.getContext();
        this.solicitations = solicitations;
    }

    @Override
    public int getCount() {
        return (solicitations == null ? 0 : solicitations.size());
    }

    @Override
    public Object getItem(int position) {
        return (solicitations == null ? 0 : solicitations.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setPadding(16, 16, 16, 16);
        text.setTextSize(16);
        text.setGravity(Gravity.CENTER);
        text.setText(solicitations.get(position).getDescription());
        text.setTextColor(ContextCompat.getColor(context, R.color.green2));

        Drawable icon = context.getDrawable(R.drawable.ic_down);
        icon.setColorFilter(context.getResources().getColor(R.color.green2), PorterDuff.Mode.SRC_ATOP);
        text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null);
        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setPadding(16, 16, 16, 16);
        text.setTextSize(18);
        text.setGravity(Gravity.CENTER_VERTICAL);
        text.setText(solicitations.get(position).getDescription());
        text.setTextColor(ContextCompat.getColor(context, R.color.green2));
        return text;
    }
}
