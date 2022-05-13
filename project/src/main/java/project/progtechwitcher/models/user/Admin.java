package project.progtechwitcher.models.user;

public class Admin extends UserBase{

    public Admin(String username) {
        super(username, Role.ADMIN);
    }

    public void DeleteUser(UserBase user)
    {
        if(user.getRole() != Role.ADMIN)
        {
            //delete user
        }
    }

    public void ModifyRoleOfUser(UserBase user, Role role)
    {
        if(user.getRole()!=role)
        {
            user.setRole(role);
        }
    }
}
