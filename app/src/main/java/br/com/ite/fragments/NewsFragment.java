package br.com.ite.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.adapters.NewsAdapter;
import br.com.ite.interfaces.NewsAPI;
import br.com.ite.interfaces.OnItemClickTransition;
import br.com.ite.models.News;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class NewsFragment extends Fragment implements OnItemClickTransition {

    private RecyclerView newsList;
    private TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.news_fragment, parent, false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final ProgressBar progressBar = (ProgressBar) fragment.findViewById(R.id.news_loading);
        progressBar.setVisibility(View.VISIBLE);

        empty = (TextView) fragment.findViewById(R.id.empty);
        empty.setVisibility(View.GONE);

        newsList = (RecyclerView) fragment.findViewById(R.id.news_list);
        newsList.setLayoutManager(layoutManager);

        NewsAPI newsAPI = ServiceGenerator.createService(NewsAPI.class);
        Call<List<News>> call = newsAPI.getNews(GlobalNames.ITE_CONTENT_TYPE_APPLICATION_JSON);
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);

                NewsAdapter adapter = new NewsAdapter(NewsFragment.this, response.body());
                newsList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        });

        return fragment;
    }

    @Override
    public void onItemClicked(Object viewHolder, int position, Bundle args) {
        try {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.addTransition(new ChangeTransform());
            transitionSet.addTransition(new ChangeImageTransform());

            setExitTransition(new Fade());

            NewsAdapter.NewsViewHolder newsViewHolder = (NewsAdapter.NewsViewHolder) viewHolder;
            NewsDetailsFragment detailsFragment = new NewsDetailsFragment();

            News news = (News) args.get(GlobalNames.ITE_ARGS_SELECTED_NEWS);
            news.setImage(((BitmapDrawable)newsViewHolder.newsImage.getDrawable()).getBitmap());
            args.putSerializable(GlobalNames.ITE_ARGS_SELECTED_NEWS, news);
            detailsFragment.setArguments(args);

            detailsFragment.setSharedElementEnterTransition(transitionSet);
            detailsFragment.setEnterTransition(new Fade());
            detailsFragment.setSharedElementReturnTransition(transitionSet);

            getFragmentManager().beginTransaction()
                    .addSharedElement(newsViewHolder.newsImage, "newsImage")
                    .replace(R.id.news_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
