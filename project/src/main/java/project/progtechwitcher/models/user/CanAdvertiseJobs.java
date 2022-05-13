package project.progtechwitcher.models.user;

import project.progtechwitcher.models.Jobs;

public class CanAdvertiseJobs extends RoleBase{
    private UserBase user;
    public CanAdvertiseJobs(UserBase user) {
        super(user);
    }

    public void AdvertiseJob(Jobs job)
    {
        this.user.AddNewAdvertisement(job);
        job.setCreatedBy(user.getId());
    }
}
