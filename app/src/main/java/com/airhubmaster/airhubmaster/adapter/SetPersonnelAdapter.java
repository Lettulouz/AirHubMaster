package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airhubmaster.airhubmaster.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter that provides a personnel assignment data binding for a given aircraft in a dialog view
 */
public class SetPersonnelAdapter extends ArrayAdapter<String> {

    /**
     * Variable declaration
     */
    private List<String> selectedItems = new ArrayList<>();

    public SetPersonnelAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_set_personnel, parent, false);
        }
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxListSetPersonnel);
        String item = getItem(position);
        checkBox.setText(item);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item);
            } else {
                selectedItems.remove(item);
            }
        });
        checkBox.setChecked(selectedItems.contains(item));
        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public void resetList() {
        selectedItems.clear();
    }
}
