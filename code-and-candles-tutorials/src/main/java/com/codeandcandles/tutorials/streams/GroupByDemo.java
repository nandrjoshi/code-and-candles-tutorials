package com.codeandcandles.tutorials.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates grouping a list of objects using Collectors.groupingBy.
 * Final output is a Map<String, List<Employee>> grouped by department.
 */
public class GroupByDemo {

    static class Employee {
        String name;
        String department;

        Employee(String name, String department) {
            this.name = name;
            this.department = department;
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Nina", "HR"),
                new Employee("Raj", "Engineering"),
                new Employee("Tina", "HR"),
                new Employee("Mike", "Engineering")
        );

        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.department));

        byDept.forEach((dept, empList) -> {
            System.out.println(dept + ": " +
                    empList.stream().map(e -> e.name).collect(Collectors.joining(", "))
            );
        });
    }
}
