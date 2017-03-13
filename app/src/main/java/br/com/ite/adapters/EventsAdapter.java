package br.com.ite.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.ite.R;
import br.com.ite.models.Event;

/**
 * Created by leonardo.borges on 14/02/2017.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Context context;
    private List<Event> events;
    private LayoutInflater inflater;

    public EventsAdapter(Fragment fragment) {
        this.context = fragment.getContext();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View eventStatus;
        TextView eventDescription;
        TextView eventStartDate;
        TextView eventEndDate;

        public ViewHolder(View view) {
            super(view);

            eventStatus = view.findViewById(R.id.event_status);
            eventDescription = (TextView) view.findViewById(R.id.event_description);
            eventStartDate = (TextView) view.findViewById(R.id.event_start_date);
            eventEndDate = (TextView) view.findViewById(R.id.event_end_date);        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        int position = viewHolder.getAdapterPosition();

        viewHolder.eventDescription.setText(events.get(position).getDescription());

        String startDate = events.get(position).getStartDate() == null ?
                "" : events.get(position).getStartDate();
        viewHolder.eventStartDate.setText(context.getResources().getString(R.string.eventStartDate)
                        .concat(" " + startDate));

        String endDate = events.get(position).getEndDate() == null ?
                "" : events.get(position).getEndDate();

        Date today = new Date();
        Date finalDate;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            if (endDate.isEmpty()) {
                finalDate = format.parse(startDate);
                viewHolder.eventEndDate.setVisibility(View.GONE);
            } else {
                finalDate = format.parse(endDate);
                viewHolder.eventEndDate.setText(context.getResources().getString(R.string.eventEndDate)
                        .concat(" " + endDate));
            }

            if (finalDate.getTime() < today.getTime()) {
                viewHolder.eventStatus
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            }
            else {
                viewHolder.eventStatus
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.lightGreen));
            }
        }
        catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return (events == null || events.get(position) == null ? 0 : events.get(position).getID());
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public void setData(List<Event> events) {
        this.events = events;
    }
}
