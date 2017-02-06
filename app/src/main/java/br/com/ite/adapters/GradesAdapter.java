package br.com.ite.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ite.R;
import br.com.ite.interfaces.OnItemClickTransition;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 01/02/2017.
 */
public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private OnItemClickTransition onClick;
    private String[] months;

    public GradesAdapter(Fragment fragment, String[]months) {
        inflater = LayoutInflater.from(fragment.getContext());
        this.months = months;
        this.onClick = (OnItemClickTransition) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grades_months_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();
        holder.gradesMonthItemText.setText(months[position]);

        ViewCompat.setTransitionName(holder.gradesMonthItemText, "gradesMonthItemText");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(GlobalNames.ITE_ARGS_SELECTED_GRADES_MONTHS, months[position]);

                onClick.onItemClicked(holder, position, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return months.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView gradesMonthItemText;

        public ViewHolder(View itemView) {
            super(itemView);
            gradesMonthItemText = (TextView) itemView.findViewById(R.id.grades_months_item_text);
        }
    }
}
