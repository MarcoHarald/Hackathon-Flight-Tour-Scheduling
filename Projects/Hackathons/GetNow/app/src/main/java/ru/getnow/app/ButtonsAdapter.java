package ru.getnow.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ButtonsAdapter extends ArrayAdapter<String> {
    private String[] buttons;
    private String selectedTitle;
    private String selected;
    private int[] buttonImages;

    ButtonsAdapter(@NonNull Context context, String[] buttons, int[] buttonImages, String selectedTitle, String selected) {
        super(context, R.layout.button);
        this.buttons = buttons;
        this.selectedTitle = selectedTitle;
        this.selected = selected;
        this.buttonImages = buttonImages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder = null;

        if (itemView == null)
        {
            final LayoutInflater layoutInflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.button, parent, false);

            holder = new ViewHolder();
            holder.imgItem = itemView.findViewById(R.id.selection_button);
            itemView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) itemView.getTag();
        }

        Drawable img = ContextCompat.getDrawable(getContext(), buttonImages[position]);
        holder.imgItem.setImageDrawable(img);
        modifyBGColor(holder, selected, buttons[position]);
        holder.imgItem.setContentDescription(buttons[position]);
        ViewHolder finalHolder = holder;
        holder.imgItem.setOnClickListener(v -> {
            if (selected.equals(buttons[position])) {
                selected = "";
                modifyBGColor(finalHolder, selected, buttons[position]);
            } else {
                selected = buttons[position];
                notifyDataSetChanged();
            }
            savePreference();
        });

        return itemView;
    }

    private void modifyBGColor(ViewHolder holder, String selected, String button) {
        if (selected != null && selected.equals(button)) {
            holder.imgItem.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        } else {
            holder.imgItem.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryDark));
        }
    }

    private void savePreference() {
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        preferences.putString(selectedTitle, selected);
        preferences.apply();
    }


    @Override
    public int getCount() {
        return buttonImages.length;
    }

    static class ViewHolder
    {
        ImageButton imgItem;
    }
}
