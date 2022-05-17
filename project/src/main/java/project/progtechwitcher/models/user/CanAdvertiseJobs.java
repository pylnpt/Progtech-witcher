package project.progtechwitcher.models.user;

import project.progtechwitcher.Database.Database;

public class CanAdvertiseJobs extends RoleBase{
    private UserBase user;
    public CanAdvertiseJobs(UserBase user) {
        super(user);
    }

    public void AdvertiseJob(String title, String description, int reward, int requiredLevel) {
        Database.AdvertiseJob(this.user.getId(), title, description, reward, requiredLevel);
    }
    public void DeleteJob(int jobId) throws IllegalAccessException {
        int creatorId = 0;
        boolean someoneAssigned = false;
        for (int i = 0; i<Database.jobs.size(); i++)
        {
            if(Database.jobs.get(i).getId() == jobId)
            {
                creatorId=Database.jobs.get(i).getCreatedBy();
                someoneAssigned = Database.jobs.get(i).getAcceptedBy()!=0;
                break;
            }
        }
        if(creatorId == this.user.getId() || !someoneAssigned)
        {
            Database.DeleteJob(jobId);
        }
        else
        {
            throw new IllegalAccessException();
        }
    }
}
