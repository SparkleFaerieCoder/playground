package iamalexmoss.com.interactivestory.model;

/**
 * Created by amoss on 6/3/2016.
 */
public class Choice {
    private String mText;
    private int nextPage;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
