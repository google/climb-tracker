package fr.steren.climblib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

public class GradeList {

    private ArrayList<Grade> grades;

    public GradeList(Context context) {
        grades = new ArrayList<Grade>();

        // load a resource file containing the grades definition
        Resources res = context.getResources();
        TypedArray gradeDefinition = res.obtainTypedArray(R.array.grades_uuia);
        for( int i = 0; i < gradeDefinition.length(); i++) {
            grades.add(new Grade(gradeDefinition.getString(i)));
        }
    }

    public static ArrayList<String> getGradeStringArray(Context context) {
        Resources res = context.getResources();
        TypedArray gradeArray = res.obtainTypedArray(R.array.grades_uuia);

        ArrayList<String> result = new ArrayList<String>();

        for( int i = 0; i < gradeArray.length(); i++) {
            result.add(gradeArray.getString(i));
        }

        return result;
    }

    public Grade get(int index) {
        return grades.get(index);
    }

    public int size() {
        return grades.size();
    }

}
