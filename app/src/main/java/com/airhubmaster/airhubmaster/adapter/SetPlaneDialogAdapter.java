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
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelPlaneDto;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter that provides a plane assignment data binding for a given aircraft in a dialog view
 */
public class SetPlaneDialogAdapter extends ArrayAdapter<SetPersonnelPlaneDto> {

    /**
     * Variable declaration
     */
    int idPlane;
    List<SetPersonnelPlaneDto> setPersonnelPlaneDtoList;
    private List<String> selectedItems = new ArrayList<>();

    public SetPlaneDialogAdapter(@NonNull Context context, int resource, @NonNull List<SetPersonnelPlaneDto> setPersonnelPlaneDtoList) {
        super(context, resource, setPersonnelPlaneDtoList);
        this.setPersonnelPlaneDtoList = setPersonnelPlaneDtoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_set_personnel, parent, false);
        }
        SetPersonnelPlaneDto item = getItem(position);
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxListSetPersonnel);

        checkBox.setText(item.getPlaneName() + ", " + item.getCategoryName());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item.getPlaneName());
                idPlane = item.getId();
            } else {
                selectedItems.remove(item.getPlaneName());
            }
        });
        checkBox.setChecked(selectedItems.contains(item.getPlaneName()));
        return convertView;
    }

    @Nullable
    @Override
    public SetPersonnelPlaneDto getItem(int position) {
        return setPersonnelPlaneDtoList.get(position);
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public int getSelectedId() {
        return idPlane;
    }

    public void resetList() {
        selectedItems.clear();
    }
}
