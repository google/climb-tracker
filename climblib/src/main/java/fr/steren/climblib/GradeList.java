package fr.steren.climblib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

public class GradeList {

    private ArrayList<Grade> grades;

    public static final String SYSTEM_UUIA = "uuia";
    public static final String SYSTEM_FRENCH = "french";

    public static final String SYSTEM_DEFAULT = SYSTEM_UUIA;

    public GradeList(Context context, String gradeSystem) {
        grades = new ArrayList<Grade>();

        // load a resource file containing the grades definition
        Resources res = context.getResources();
        TypedArray gradeDefinition = res.obtainTypedArray(getGradeResourceFromSystem(gradeSystem));
        for( int i = 0; i < gradeDefinition.length(); i++) {
            grades.add(new Grade(gradeDefinition.getString(i)));
        }
    }

    public ArrayList<String> getGradeStringList() {
        ArrayList<String> result = new ArrayList<String>();
        for( Grade g : grades) {
            result.add(g.label);
        }
        return result;
    }

    private int getGradeResourceFromSystem(String system) {
        switch (system) {
            case SYSTEM_UUIA:
                return R.array.grades_uuia;
            case SYSTEM_FRENCH:
                return R.array.grades_french;
            default:
                return R.array.grades_uuia;
        }
    }

    public Grade get(int index) {
        return grades.get(index);
    }

    public int size() {
        return grades.size();
    }

}
