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

    public int getNextPage() {
        return mNextPage;
    }

}
