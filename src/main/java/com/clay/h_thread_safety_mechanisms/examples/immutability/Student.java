package com.clay.h_thread_safety_mechanisms.examples.immutability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * To achieve immutability, several techniques are used:
 *
 * The final keyword is used to make the class Student final, preventing it from being subclassed.
 * The fields name, age, and courses are declared as private final, indicating that they cannot be modified once assigned.
 * The constructor takes the initial values of name, age, and courses as parameters and assigns them to the corresponding
 * fields. For the courses field, a new ArrayList is created using the provided list, ensuring that changes to the original
 * list do not affect the Student object.
 *
 * Getter methods (getName(), getAge(), getCourses()) are provided to allow access to the values of the fields.
 * The getCourses() method returns an unmodifiable list using Collections.unmodifiableList(), preventing external code
 * from modifying the list held by the Student object.
 *
 * By making the Student class immutable, we guarantee that once a Student object is created, its data remains unchanged.
 * This ensures thread safety, simplifies code reasoning, and prevents unintended modifications by external code.
 */
public final class Student {
    private final String name;
    private final int age;
    private final List<String> courses;

    public Student(String name, int age, List<String> courses) {
        this.name = name;
        this.age = age;
        this.courses = new ArrayList<>(courses);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
