package project.progtechwitcher.models.user;

public class Employer extends UserBase{
    public Employer(String username) {
        super(username, Role.EMPLOYER);
    }
}
