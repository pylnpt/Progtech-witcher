package project.progtechwitcher.models.user;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.models.Jobs;

public class CanTakeJobs extends RoleBase {
    private UserBase user;
    public CanTakeJobs(UserBase user) {
        super(user);
        this.user=user;
    }


    public void TakeJob(int JobId, int requiredLevel) throws Exception {
        if(requiredLevel > this.user.getLevel())
        {
            throw new Exception("Cannot take job");
        }
        else
        {
            Database.ApplyToJob(JobId, this.user.getId());
        }
    }

    public void JobDone(int jobId, int evolved)
    {
        Database.SetJobDone(jobId, this.user.getId());
        Database.AddReward2User(evolved, this.user.getId());
    }

}

