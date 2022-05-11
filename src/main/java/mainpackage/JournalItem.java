package mainpackage;

public class JournalItem {
    private String mDate;
    private String mContent;


    public JournalItem(String date, String content) {
        this.mDate = date;
        this.mContent = content;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }
}
