package fr.steren.climbtracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import fr.steren.climblib.Grade;
import fr.steren.climblib.GradeList;

public class GradePickerFragment extends DialogFragment {

    public interface GradeDialogListener {
        public void onGradeSelected(String grade);
    }

    GradeDialogListener mListener;

    GradeList mGradeList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO: create a custom layout with a nicer Grade selector (centered?), see http://developer.android.com/guide/topics/ui/dialogs.html

        mGradeList = new GradeList((Activity) mListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<String> grades = GradeList.getGradeStringArray((Activity) mListener);
        CharSequence[] cs = grades.toArray(new CharSequence[grades.size()]);
        builder.setTitle(R.string.pick_grade)
            .setItems(cs, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onGradeSelected(mGradeList.get(which).label);
                        }

                    });

        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (GradeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}