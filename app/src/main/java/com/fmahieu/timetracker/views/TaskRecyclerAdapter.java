package com.fmahieu.timetracker.views;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.TaskLogic;

import java.util.ArrayList;
import java.util.List;


public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskHolder> {
    private final String TAG = "__TaskAdapter";

    private List<String> tasks;
    private TaskLogic taskLogic;
    private Context context;

    private int notificationIdCounter = 0;

    public TaskRecyclerAdapter(Context context){
        this.taskLogic = new TaskLogic();
        this.context = context;
        this.tasks = taskLogic.getTasksNames();

        createNotificationChannel();
        setNotifications();

        if(this.tasks == null){
            this.tasks = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        return new TaskHolder(layoutInflater, parent, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bind(this.tasks.get(position), notificationIdCounter++, notificationBuilder);
    }

    @Override
    public int getItemCount() {
        int newNumTasks = taskLogic.getNumberOfTasks();

        // Update the list of tasks if new tasks have been added
        if(this.tasks.size() != newNumTasks) {
            this.tasks = taskLogic.getTasksNames();
        }
        return newNumTasks;
    }

    /* NOTIFICATION HANDLER */
    private String CHANNEL_ID = "mainChannel";
    NotificationCompat.Builder notificationBuilder;
    private String STOP_NOTIFICATION_ID = "stopNotificationId";
    private String STOP_ACTION = "stopAction";

    private void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = context.getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressLint("StringFormatInvalid")
    private void setNotifications(){

        // Create intent that will be run when user tap notification
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // button inside notification
        Intent stopIntent = new Intent(context, MainActivity.class);
        stopIntent.setAction(STOP_ACTION);
        stopIntent.putExtra(STOP_NOTIFICATION_ID, 0);
        PendingIntent stopPendingIntent =
                PendingIntent.getBroadcast(context, 0, stopIntent, 0);


        // create basic notification format
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon_notification_vec)
                .setContentTitle(context.getString(R.string.title_notification))
                .setContentIntent(pendingIntent)
                .setAutoCancel(false) // notification stays on when user tap it
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_icon_stop_action_notification, context.getString(R.string.stop_action_notification), stopPendingIntent);

        // how to call the notification
        /*
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        */
    }
}
