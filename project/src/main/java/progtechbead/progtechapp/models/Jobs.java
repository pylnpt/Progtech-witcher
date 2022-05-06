package progtechbead.progtechapp.models;

public class Jobs {
    private int id;
    private String title;
    private String description;
    private int reward;
    private int requiredLevel;
    private boolean isSomeoneAssigned;
    private int takenBy = 0;//ez, vagy egy User obj, de abban meg van egy Jobs lista
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

    public int getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(int takenBy) throws Exception {
        if(this.takenBy == 0 && takenBy != 0) {
            this.takenBy = takenBy;
            this.isSomeoneAssigned = true;
        }
        else if(takenBy == 0 && this.takenBy!=0)
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

    public void setDone() {
        if(this.isSomeoneAssigned == true
                && this.isDone == false)
            isDone = true;
    }
}
