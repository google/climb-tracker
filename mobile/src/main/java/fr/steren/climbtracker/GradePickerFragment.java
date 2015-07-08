/*
Copyright 2014 Google Inc. All rights reserved.
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package fr.steren.climbtracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import fr.steren.climblib.GradeList;
import fr.steren.climblib.Path;

public class GradePickerFragment extends DialogFragment {

    public interface GradeDialogListener {
        public void onGradeSelected(String grade);
    }

    GradeDialogListener mListener;

    GradeList mGradeList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO: create a custom layout with a nicer Grade selector (centered?), see http://developer.android.com/guide/topics/ui/dialogs.html

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences((Activity) mListener);
        String gradeSystemTypePref = sharedPref.getString(Path.PREF_GRAD_SYSTEM_TYPE, GradeList.SYSTEM_DEFAULT);

        mGradeList = new GradeList((Activity) mListener, gradeSystemTypePref);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<String> grades = mGradeList.getGradeStringList();
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