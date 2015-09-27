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
package fr.steren.climblib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

public class GradeList {

    private ArrayList<Grade> grades;

    public static final String SYSTEM_YDS = "yds";
    public static final String SYSTEM_UUIA = "uuia";
    public static final String SYSTEM_FRENCH = "french";
    public static final String SYSTEM_SAXON = "saxon";

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
            case SYSTEM_YDS:
                return R.array.grades_yds;
            case SYSTEM_UUIA:
                return R.array.grades_uuia;
            case SYSTEM_FRENCH:
                return R.array.grades_french;
            case SYSTEM_SAXON:
                return R.array.grades_saxon;
            default:
                return R.array.grades_yds;
        }
    }

    public Grade get(int index) {
        return grades.get(index);
    }

    public int size() {
        return grades.size();
    }

}
