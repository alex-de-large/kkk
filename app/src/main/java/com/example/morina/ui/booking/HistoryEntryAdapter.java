package com.example.morina.ui.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.morina.R;

import java.util.List;

public class HistoryEntryAdapter extends ArrayAdapter<HistoryEntry> {
    private final LayoutInflater inflater;
    private final int layout;

    private final List<HistoryEntry> items;

    public HistoryEntryAdapter(Context context, int resource, List<HistoryEntry> items) {
        super(context, resource, items);
        this.items = items;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView status;
        TextView date;
        TextView from;
        TextView to;
        TextView time;
        TextView tarif;
        TextView card;

        public ViewHolder(
            HistoryEntry object,
            TextView status,
            TextView date,
            TextView from,
            TextView to,
            TextView time,
            TextView tarif,
            TextView card
        ) {
            status.setTag(object);
            date.setTag(object);
            from.setTag(object);
            to.setTag(object);
            time.setTag(object);
            tarif.setTag(object);
            card.setTag(object);

            status.setText(object.getStatus());
            date.setText(object.getDate());
            from.setText(object.getFrom());
            to.setText(object.getTo());
            time.setText(object.getTime());
            tarif.setText(object.getTarif());
            card.setText(object.getCard());

            this.status = status;
            this.date = date;
            this.from = from;
            this.to = to;
            this.time = time;
            this.tarif = tarif;
            this.card = card;
        }
    }

    @SuppressLint("SetTextI18n")
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            final ViewHolder vh = new ViewHolder(
                items.get(position),
                convertView.findViewById(R.id.history_entry_text_status),
                convertView.findViewById(R.id.history_entry_text_date),
                convertView.findViewById(R.id.history_entry_text_from),
                convertView.findViewById(R.id.history_entry_text_to),
                convertView.findViewById(R.id.history_entry_text_time),
                convertView.findViewById(R.id.history_entry_text_tarif),
                convertView.findViewById(R.id.history_entry_text_card)
            );
            convertView.setTag(vh);
        }
        return convertView;
    }
}
