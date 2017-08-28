package com.hansellramos.todo.client.android.activities.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hansellramos.todo.client.android.R;
import com.hansellramos.todo.client.android.dto.Task;

import java.util.List;

/**
 * Created by hansell.ramos on 28/08/17.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_task, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        Task item = getItem(position);

        nameTextView.setText(item.getName());

        return convertView;
    }
}
