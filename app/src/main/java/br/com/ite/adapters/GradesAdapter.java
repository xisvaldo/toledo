package br.com.ite.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.interfaces.OnItemClickTransition;
import br.com.ite.models.StudentGrades;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 01/02/2017.
 */
public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private OnItemClickTransition onClick;
    private List<StudentGrades> gradesList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class NormalViewHolder extends ViewHolder {

        public TextView subject;
        TextView failures;

        public NormalViewHolder(View v) {
            super(v);
            this.subject = (TextView) v.findViewById(R.id.grades_normal_item_subject);
            this.failures = (TextView) v.findViewById(R.id.grades_normal_item_failures_value);
        }
    }

    public class PostViewHolder extends ViewHolder {

        TextView subject;
        TextView presencePercentage;
        TextView finalConcept;

        public PostViewHolder(View v) {
            super(v);
            this.subject = (TextView) v.findViewById(R.id.grades_post_item_subject);
            this.presencePercentage = (TextView) v
                    .findViewById(R.id.grades_post_item_presence_percentage_value);
            this.finalConcept = (TextView) v.findViewById(R.id.grades_post_item_final_concept_value);
        }
    }

    public GradesAdapter(Fragment fragment) {
        inflater = LayoutInflater.from(fragment.getContext());
        this.onClick = (OnItemClickTransition) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == GlobalNames.ITE_STUDENT_GRADES_ITEM_TYPE_NORMAL) {
            v = inflater.inflate(R.layout.grades_normal_item, parent, false);
            return new NormalViewHolder(v);
        }
        else {
            v = inflater.inflate(R.layout.grades_post_item, parent, false);
            return new PostViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();
        final StudentGrades grades = gradesList.get(position);

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.white);
        }
        else {
            holder.itemView.setBackgroundResource(R.color.lightGray);
        }

        if (holder.getItemViewType() == GlobalNames.ITE_STUDENT_GRADES_ITEM_TYPE_NORMAL) {
            final NormalViewHolder viewHolder = (NormalViewHolder) holder;
            viewHolder.subject.setText(grades.getSubject());
            viewHolder.failures.setText(grades.getFailures());

            ViewCompat.setTransitionName(viewHolder.subject, "gradesNormalSubject");

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable(GlobalNames.ITE_ARGS_SELECTED_NORMAL_GRADES, grades);
                    onClick.onItemClicked(viewHolder, position, args);
                }
            });

        }
        else if (holder.getItemViewType() == GlobalNames.ITE_STUDENT_GRADES_ITEM_TYPE_POST) {
            final PostViewHolder viewHolder = (PostViewHolder) holder;
            viewHolder.subject.setText(grades.getSubject());
            viewHolder.presencePercentage.setText(grades.getPresencePercentage());
            viewHolder.finalConcept.setText(grades.getFinalConcept());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.gradesList.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return (gradesList == null ? 0 :  gradesList.size());
    }

    public void setData(List<StudentGrades> grades) {
        this.gradesList = grades;
    }
}
