package br.com.ite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ite.R;
import br.com.ite.models.News;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public class NewsDetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.news_details_fragment, parent, false);

        ImageView closeButton = (ImageView) fragment.findViewById(R.id.news_details_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        ImageView image = (ImageView) fragment.findViewById(R.id.news_image);
        TextView title = (TextView) fragment.findViewById(R.id.news_details_title);
        TextView createdOn = (TextView) fragment.findViewById(R.id.news_details_creation_date);
        TextView description = (TextView) fragment.findViewById(R.id.news_details_description);

        News news = (News) getArguments().get(GlobalNames.ITE_ARGS_SELECTED_NEWS);

        if (news != null) {
            title.setText(news.getTitle());
            createdOn.setText(news.getFormattedCreatedOn(getContext()));
            description.setText(news.getDescription());

            Picasso.with(getContext())
                    .load(news.getImageURL())
                    .placeholder(R.drawable.image_placeholder)
                    .into(image);
        }

        return fragment;

    }
}
