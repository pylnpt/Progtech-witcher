package project.progtechwitcher.models;

public class Jobs extends Object {
    private int id;
    private String title;
    private String description;
    private int reward;
    private int requiredLevel;
    private boolean isSomeoneAssigned;
    private int acceptedBy = 0;//db_connection táblából
    private int createdBy = 0;
    private boolean isDone = false;

    public Jobs(String title, String description, int reward, int requiredLevel) {
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.requiredLevel = requiredLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        if(reward>0 && reward < 5)
        {
            this.reward = reward;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        if(requiredLevel < 100) {
            this.requiredLevel = requiredLevel;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public boolean isSomeoneAssigned() {
        return isSomeoneAssigned;
    }

    public int getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(int acceptedBy) throws Exception {
        if(this.acceptedBy == 0 && acceptedBy != 0) {
            this.acceptedBy = acceptedBy;
            this.isSomeoneAssigned = true;
        }
        else if(acceptedBy == 0 && this.acceptedBy !=0)
        {
            throw new Exception("Already taken");
        }
        else
        {
            this.isSomeoneAssigned = false;
        }
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean setDone) {
        this.isDone = setDone;
    }
    public void setSomeoneAssigned(boolean someoneAssigned) {
        isSomeoneAssigned = someoneAssigned;
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", requiredLevel=" + requiredLevel +
                ", isSomeoneAssigned=" + isSomeoneAssigned +
                ", acceptedBy=" + acceptedBy +
                ", createdBy=" + createdBy +
                ", isDone=" + isDone +
                '}';
    }
}
