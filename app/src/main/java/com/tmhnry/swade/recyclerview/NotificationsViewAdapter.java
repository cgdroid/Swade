package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Notification;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationsViewAdapter extends RecyclerView.Adapter<NotificationsViewAdapter.ViewHolder> {
    List<Notification> notifications;
    Context context;
    OnNotificationClickListener listener;


    public NotificationsViewAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
        listener = (OnNotificationClickListener) context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title, msg, date;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.notif_title);
            msg = itemView.findViewById(R.id.notif_msg);
            date = itemView.findViewById(R.id.notif_date);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_notification, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.itemView.setOnClickListener(v -> {
            listener.onNotificationClick(notification, notification.getId());
//            Map<String, Object> data = new HashMap<>();
//            data.put(Notification.SENDER_KEY, notification.senderKey);
//            data.put(Notification.COMPANY_KEY, notification.companyKey);
//            data.put(User.POSITION, User.SUBORDINATE);
//            Company.addUser(data);
        });
        holder.title.setText(notification.title);
        holder.msg.setText(notification.message);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateTime = dateFormat.format(notification.date);
        holder.date.setText("from " + notification.senderName + " last " + notification.date.toLocaleString());
    }

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification, int notificationId);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
