package br.com.ite.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ite.R;
import br.com.ite.interfaces.OnItemClickTransition;
import br.com.ite.models.News;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private OnItemClickTransition onClick;
    private List<News> news;

    public NewsAdapter(Fragment fragment, List<News> news) {
        this.context = fragment.getContext();
        this.onClick = (OnItemClickTransition) fragment;
        this.news = news;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView newsImage;
        TextView newsTitle;
        TextView newsCreationDate;
        TextView newsDescription;

        public NewsViewHolder(View v) {
            super(v);

            this.newsImage = (ImageView) v.findViewById(R.id.news_image);
            this.newsTitle = (TextView) v.findViewById(R.id.news_title);
            this.newsCreationDate = (TextView) v.findViewById(R.id.news_creation_date);
            this.newsDescription = (TextView) v.findViewById(R.id.news_description);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int pos) {
        final int position = holder.getAdapterPosition();
        final NewsViewHolder viewHolder = holder;

        Picasso.with(context).cancelRequest(viewHolder.newsImage);

        viewHolder.newsTitle.setText(news.get(position).getTitle());
        viewHolder.newsCreationDate.setText(news.get(position).getCreationDate());
        viewHolder.newsDescription.setText(news.get(position).getDescription());


        if (news.get(position).getImageURL() != null
                && !news.get(position).getImageURL().isEmpty()) {

            Picasso.with(context)
                    .load(news.get(position).getImageURL())
                    .placeholder(R.drawable.image_placeholder)
                    .into(viewHolder.newsImage);
        }

        ViewCompat.setTransitionName(viewHolder.newsImage, "newsImage");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable(GlobalNames.ITE_ARGS_SELECTED_NEWS, news.get(position));
                onClick.onItemClicked(viewHolder, position, args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (this.news == null ? 0 : this.news.size());
    }
}
