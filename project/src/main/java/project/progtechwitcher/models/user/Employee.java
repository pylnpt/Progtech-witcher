package project.progtechwitcher.models.user;

public class Employee extends UserBase{
    public Employee(String username) {
        super(username, Role.EMPLOYEE);
    }
}
