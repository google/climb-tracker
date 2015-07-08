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

import java.util.Date;

public class Climb {
    private Date date;
    private String grade;
    private String system;

    private String key;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Climb() {
    }

    public Climb(Date date, String grade, String system) {
        this.date = date;
        this.grade = grade;
        this.system = system;
    }

    public Climb(String key, Date date, String grade, String system) {
        this.key = key;
        this.date = date;
        this.grade = grade;
        this.system = system;
    }

    public Date getDate() {
        return date;
    }

    public String getGrade() {
        return grade;
    }

    public String getSystem() {
        return system;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return grade;
    }
}
