package progtechbead.progtechapp.models.user;

import progtechbead.progtechapp.models.Jobs;

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
