package fr.steren.climbtracker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

public class GradeList {

    private ArrayList<Grade> grades;

    GradeList(Context context) {
        grades = new ArrayList<Grade>();

        // load a resource file containing the grades definition
        Resources res = context.getResources();
        TypedArray gradeDefinition = res.obtainTypedArray(R.array.grades_uuia);
        for( int i = 0; i < gradeDefinition.length(); i++) {
            grades.add(new Grade(gradeDefinition.getString(i)));
        }
    }

    Grade get(int index) {
        return grades.get(index);
    }

    int size() {
        return grades.size();
    }

}
