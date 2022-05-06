package progtechbead.progtechapp.models.user;

public class RoleBase extends UserBase {
    UserBase user;

    public RoleBase(UserBase user) {
        super(user.getUsername(), user.getRole());
        this.user = user;
    }
}
