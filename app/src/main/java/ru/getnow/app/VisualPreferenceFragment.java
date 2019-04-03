package ru.getnow.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.getnow.app.model.VisualPreference;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class VisualPreferenceFragment extends Fragment {
    private static final String VISUAL_PREF = "visual_pref";
    private static final String DEFAULT_PRICE = "price";
    private static final String DEFAULT_SELECTION = "selection";

    private VisualPreference visualPref;
    private double price;
    private String selectedTitle;

    public VisualPreferenceFragment() {
        // Required empty public constructor
    }

    static VisualPreferenceFragment newInstance(VisualPreference visualPref, double price, String selectedTitle) {
        VisualPreferenceFragment fragment = new VisualPreferenceFragment();
        Bundle args = new Bundle();
        args.putParcelable(VISUAL_PREF, visualPref);
        args.putDouble(DEFAULT_PRICE, price);
        args.putString(DEFAULT_SELECTION, selectedTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            visualPref = getArguments().getParcelable(VISUAL_PREF);
            price = getArguments().getDouble(DEFAULT_PRICE);
            selectedTitle = getArguments().getString(DEFAULT_SELECTION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_visual_preference, container, false);
        TextInputEditText priceEditor = fragmentView.findViewById(R.id.money_to_spend);
        priceEditor.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    saveDefaultPrice(0);
                } else {
                    saveDefaultPrice(Float.parseFloat(s.toString()));
                }
            }
        });
        priceEditor.setText(String.valueOf(price));
        GridView gridLayout = fragmentView.findViewById(R.id.buttons);
        gridLayout.setAdapter(new ButtonsAdapter(requireContext(),
                visualPref.getPossibleSelections(),
                visualPref.getIconIds(), visualPref.getTabDescription()+"_", selectedTitle));
        return fragmentView;
    }

    private void saveDefaultPrice(float defaultPrice) {
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        preferences.putFloat(visualPref.getTabDescription(), defaultPrice);
        preferences.apply();
    }
}
