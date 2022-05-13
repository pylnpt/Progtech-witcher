package project.progtechwitcher.models.user;
import project.progtechwitcher.models.Jobs;

import java.util.ArrayList;

public abstract class UserBase {

    private int id;
    private String username;//unique constraint-es
    private String password;
    private Role role;
    private int level;
    public ArrayList<Jobs> takenJobs;
    public ArrayList<Jobs> advertisedJobs;

    @Override
    public String toString() {
        return "UserBase{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", level=" + level +
                ", takenJobs=" + takenJobs +
                ", advertisedJobs=" + advertisedJobs +
                '}';
    }

    public UserBase(String username, Role role) {
        setUsername(username);
        setRole(role);
        switch (role)
        {
            case ADMIN:
            {
//                this.takenJobs = new ArrayList<Jobs>();
//                this.advertisedJobs = new ArrayList<Jobs>();
                setLevel(100);
                break;
            }
            case EMPLOYEE: {
                this.takenJobs = new ArrayList<Jobs>();
                break;
            }
            case EMPLOYER:
            {
                this.advertisedJobs = new ArrayList<Jobs>();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level > this.level)
        {
            this.level = level;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<Jobs> getTakenJobs() {
        return takenJobs;
    }

    public void AddNewJob(Jobs job)
    {
        this.takenJobs.add(job);
    }
    public void AddNewAdvertisement(Jobs job)
    {
        this.advertisedJobs.add(job);
    }
}
