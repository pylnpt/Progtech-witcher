package progtechbead.progtechapp.models.user;

import  progtechbead.progtechapp.models.user.UserBase;
import  progtechbead.progtechapp.models.user.RoleBase;
import progtechbead.progtechapp.models.Jobs;

public class CanTakeJobs extends progtechbead.progtechapp.models.user.RoleBase {
    private progtechbead.progtechapp.models.user.UserBase user;
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
            job.setTakenBy(user.getId());
            user.AddNewJob(job);
        }
    }

    public void JobDone(Jobs job)
    {
        job.setDone();
        user.setLevel(user.getLevel()+job.getReward());
    }
}

