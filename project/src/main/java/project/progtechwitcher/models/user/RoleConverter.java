package project.progtechwitcher.models.user;

public class RoleConverter {
    private RoleConverter()
    { }
    public static final String RoleToString(Role role)
    {
        switch(role)
        {
            case ADMIN -> {
                return "admin";
            }
            case EMPLOYER -> {
                return "employer";
            }
            case EMPLOYEE -> {
                return "employee";
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

    public static final Role StringToRole(String role)
    {
        switch(role)
        {
            case "admin" -> {
                return Role.ADMIN;
            }
            case "employer" -> {
                return Role.EMPLOYER;
            }
            case "employee" -> {
                return Role.EMPLOYEE;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
