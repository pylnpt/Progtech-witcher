package project.progtechwitcher.models.user;
import project.progtechwitcher.models.Jobs;

public class CanTakeJobs extends RoleBase {
    private UserBase user;
    public CanTakeJobs(UserBase user) {
        super(user);
    }
    public void TakeJob(Jobs job) throws Exception {
        if(job.getRequiredLevel() > user.getLevel())
        {
            throw new Exception("");
        }
        else
        {
            job.setAcceptedBy(user.getId());
            user.AddNewJob(job);
        }
    }

    public void JobDone(Jobs job)
    {
        job.setDone(true);
        user.setLevel(user.getLevel()+job.getReward());
    }
}

