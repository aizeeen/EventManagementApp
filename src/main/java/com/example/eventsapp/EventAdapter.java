package com.example.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;
    private OnEventClickListener listener;

    public EventAdapter(Context context, List<Event> eventList, OnEventClickListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewEventName.setText(event.getName());
        

        switch(event.getId()) {
            case 1:
                holder.textViewEventShortDesc.setText("Transform lives through entrepreneurial action");
                break;
            case 2:
                holder.textViewEventShortDesc.setText("Learn and build Android apps together");
                break;
            case 3:
                holder.textViewEventShortDesc.setText("Innovate and collaborate in technology");
                break;
            case 4:
                holder.textViewEventShortDesc.setText("Bringing fun and entertainment to campus");
                break;
        }
        

        holder.imageViewEventIcon.setImageResource(event.getIconResourceId());
        
        holder.buttonJoinEvent.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewEventIcon;
        TextView textViewEventName;
        TextView textViewEventShortDesc;
        Button buttonJoinEvent;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewEventIcon = itemView.findViewById(R.id.imageViewEventIcon);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            textViewEventShortDesc = itemView.findViewById(R.id.textViewEventShortDesc);
            buttonJoinEvent = itemView.findViewById(R.id.buttonJoinEvent);
        }
    }

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }
}
