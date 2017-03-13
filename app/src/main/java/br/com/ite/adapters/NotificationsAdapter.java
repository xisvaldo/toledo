package br.com.ite.adapters;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.models.Notification;

/**
 * Created by leonardo.borges on 14/02/2017.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context context;
    private List<Notification> notifications;
    private LayoutInflater inflater;

    public NotificationsAdapter(DialogFragment fragment) {
        this.context = fragment.getActivity().getApplicationContext();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notificationDescription;
        TextView notificationStartDate;
        TextView notificationEndDate;

        public ViewHolder(View view) {
            super(view);

            notificationDescription = (TextView) view.findViewById(R.id.notification_description);
            notificationStartDate = (TextView) view.findViewById(R.id.notification_start_date);
            notificationEndDate = (TextView) view.findViewById(R.id.notification_end_date);        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        int position = viewHolder.getAdapterPosition();

        viewHolder.notificationDescription.setText(notifications.get(position).getMessage());
        viewHolder.notificationStartDate.setText(notifications.get(position).getStartDate());
        viewHolder.notificationEndDate.setText(notifications.get(position).getEndDate());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return notifications == null ? 0 : notifications.size();
    }

    public void setData(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
