package mainpackage;

public class Habit {

    private String mName;
    private int mReps;
    private int mDone;

    public Habit(String name, int reps, int done) {
        this.mName = name;
        this.mReps = reps;
        this.mDone = done;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getRepetitions() {
        return mReps;
    }

    public void setRepetitions(int repetitions) {
        this.mReps = repetitions;
    }

    public int getDone() {
        return mDone;
    }

    public void setDone(int done) {
        this.mDone = done;
    }
}
