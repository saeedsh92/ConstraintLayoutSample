package com.sshahini.constraintsampleweather.views.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sshahini.constraintsampleweather.R;
import com.sshahini.constraintsampleweather.events.CityNameChanged;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by saeed on 7/16/16.
 */
public class LocationChangerDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_location,null,false);
        initViews(view);


        builder.setView(view);
        return builder.create();
    }

    private void initViews(View view) {
        final EditText editTextCityName=(EditText)view.findViewById(R.id.location_name);
        final TextView btnOk=(TextView)view.findViewById(R.id.ok);
        final TextView btnCancel=(TextView)view.findViewById(R.id.cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCityName.getText().toString().length()>0) {
                    dismiss();
                    EventBus.getDefault().post(new CityNameChanged(editTextCityName.getText().toString()));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
