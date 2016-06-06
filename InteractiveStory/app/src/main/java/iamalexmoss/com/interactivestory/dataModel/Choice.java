package iamalexmoss.com.interactivestory.dataModel;

/**
 * Created by amoss on 6/3/2016.
 */
public class Choice {
    private String mText;
    private int mNextPage;

    //Constructor
    public Choice(String text, int nextPage) {
        mText = text;
        mNextPage = nextPage;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public void setNextPage(int nextPage) {
        this.mNextPage = nextPage;
    }
}
