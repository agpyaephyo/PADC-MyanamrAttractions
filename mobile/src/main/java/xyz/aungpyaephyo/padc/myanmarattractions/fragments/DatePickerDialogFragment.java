package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;

/**
 * Created by aung on 6/25/16.
 */
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DataEvent.DatePickedEvent event = new DataEvent.DatePickedEvent(year + "/" + monthOfYear + "/" + dayOfMonth);
        EventBus.getDefault().post(event);
    }
}
