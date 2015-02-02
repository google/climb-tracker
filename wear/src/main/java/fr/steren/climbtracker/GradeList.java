package fr.steren.climbtracker;

import java.util.ArrayList;

public class GradeList {

    private ArrayList<Grade> grades;

    GradeList() {
        grades = new ArrayList<Grade>();
        grades.add(new Grade("4"));
        grades.add(new Grade("4+"));
        grades.add(new Grade("5-"));
        grades.add(new Grade("5"));
        grades.add(new Grade("5+"));
        grades.add(new Grade("6-"));
        grades.add(new Grade("6"));
        grades.add(new Grade("6+"));
        grades.add(new Grade("7-"));
    }

    Grade get(int index) {
        return grades.get(index);
    }

    int size() {
        return grades.size();
    }

}
