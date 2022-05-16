package project.progtechwitcher.models.user;

import project.progtechwitcher.Database.Database;

public class Admin extends UserBase{

    public Admin(String username) {
        super(username, Role.ADMIN);
    }

    public void DeleteUser(int userId) {
        Database.DeleteUser(userId);
    }
    public void DeleteJob(int jobId) throws IllegalAccessException {
        boolean someoneAssigned = false;
        for (int i = 0; i<Database.jobs.size(); i++)
        {
            if(Database.jobs.get(i).getId() == jobId)
            {
                someoneAssigned = Database.jobs.get(i).getAcceptedBy()!=0;
                break;
            }
        }
        if(!someoneAssigned)
        {
            Database.DeleteJob(jobId);
        }
        else
        {
            throw new IllegalAccessException();
        }
    }
}
