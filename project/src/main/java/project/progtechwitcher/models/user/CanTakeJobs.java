package project.progtechwitcher.models.user;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.models.Jobs;

public class CanTakeJobs extends RoleBase {
    private UserBase user;
    public CanTakeJobs(UserBase user) {
        super(user);
    }


    public void TakeJob(int JobId, int requiredLevel) throws Exception {
        if(requiredLevel > user.getLevel())
        {
            throw new Exception("");
        }
        else
        {
            Database.ApplyToJob(JobId, this.user.getId());
        }
    }

    public void JobDone(int jobId)
    {
        Database.SetJobDone(jobId, this.user.getId());
        Database.AddReward2User(jobId, this.user.getId());
    }
}

