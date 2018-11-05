package it.android.hal.test;

import it.android.hal.resource.ResourceHelper;
import it.android.hal.task.RestServiceAsync;
import it.android.hal.task.TaskBuilder;

public class TestAA {
    public static void main(String[] args) {

        ResourceHelper.build(this, new CustomConfig(), null);

        Employee employee = new Employee();
        EmployeeService employeeService = new EmployeeService();

        RestServiceAsync<Employee, Void, Void> restServiceAsync = TaskBuilder.build().create(employeeService);
        restServiceAsync.observe(this, aVoid -> {

        });
        restServiceAsync.execute(employee);
    }
}
