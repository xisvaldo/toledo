package br.com.ite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ite.R;
import br.com.ite.activities.BaseActivity;
import br.com.ite.models.StudentGrades;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 01/02/2017.
 */
public class GradesDetailsNormalFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.grades_details_normal_fragment, parent, false);

        TextView testOneValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_test_one_value);
        TextView testTwoValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_test_two_value);
        TextView testThreeValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_test_three_value);
        TextView testFourValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_test_four_value);
        TextView substituteTestOneValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_substitute_test_one_value);
        TextView substituteTestTwoValue = (TextView) fragment
                .findViewById(R.id.grades_details_normal_substitute_test_two_value);
        TextView examValue = (TextView) fragment.findViewById(R.id.grades_details_normal_exam_value);

        Bundle args = getArguments();

        if (args != null) {
            StudentGrades grades = (StudentGrades) args.getSerializable(
                    GlobalNames.ITE_ARGS_SELECTED_NORMAL_GRADES);

            String noGrades = getString(R.string.noGrades);

            ((BaseActivity) getActivity()).changeToolbarTitle(grades.getSubject());
            testOneValue.setText(grades.getTestOne().isEmpty() ? noGrades : grades.getTestOne());
            testTwoValue.setText(grades.getTestTwo().isEmpty() ? noGrades : grades.getTestTwo());
            testThreeValue.setText(grades.getTestThree().isEmpty() ? noGrades : grades.getTestThree());
            testFourValue.setText(grades.getTestFour().isEmpty() ? noGrades : grades.getTestFour());
            substituteTestOneValue.setText(grades.getSubstituteOne().isEmpty()
                    ? noGrades : grades.getSubstituteOne());
            substituteTestTwoValue.setText(grades.getSubstituteTwo().isEmpty()
                    ? noGrades : grades.getSubstituteTwo());
            examValue.setText(grades.getExam().isEmpty() ? noGrades : grades.getExam());
        }

        return fragment;
    }

}
