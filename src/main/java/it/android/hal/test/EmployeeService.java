package it.android.hal.test;

import it.android.hal.service.RestService;

public class EmployeeService extends RestService<Employee, Employee> {

    public EmployeeService() {
        super("employees");
    }
}
