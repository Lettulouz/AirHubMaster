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
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelWorkerDto;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter that provides a personnel assignment data binding for a given aircraft in a dialog view
 */
public class SetPersonnelAdapter extends ArrayAdapter<SetPersonnelWorkerDto> {

    /**
     * Variable declaration
     */
    int idWorker;
    List<SetPersonnelWorkerDto> setPersonnelWorkerDtoList;
    private List<String> selectedItems = new ArrayList<>();

    public SetPersonnelAdapter(@NonNull Context context, int resource, @NonNull List<SetPersonnelWorkerDto> setPersonnelWorkerDtoList) {
        super(context, resource, setPersonnelWorkerDtoList);
        this.setPersonnelWorkerDtoList = setPersonnelWorkerDtoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_set_personnel, parent, false);
        }
        SetPersonnelWorkerDto item = getItem(position);
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxListSetPersonnel);

        checkBox.setText(item.getFullName());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item.getFullName());
                idWorker = item.getId();
            } else {
                selectedItems.remove(item.getFullName());
            }
        });
        checkBox.setChecked(selectedItems.contains(item.getFullName()));
        return convertView;
    }

    @Override
    public int getCount() {
        return setPersonnelWorkerDtoList.size();
    }

    @Nullable
    @Override
    public SetPersonnelWorkerDto getItem(int position) {
        return setPersonnelWorkerDtoList.get(position);
    }

    public int getSelectedId() {
        return idWorker;
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public void resetList() {
        selectedItems.clear();
    }
}
