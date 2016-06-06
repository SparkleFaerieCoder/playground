package iamalexmoss.com.interactivestory.dataModel;

/**
 * Created by amoss on 6/3/2016.
 */
public class Page {
    private int mImageId;
    private String mText;
    private Choice mChoice1;
    private Choice mChoice2;
    private boolean mIsFinal = false;

    //Constructor
    public Page(int imageId, String text, Choice choice1, Choice choice2) {
        mImageId = imageId;
        mText = text;
        mChoice1 = choice1;
        mChoice2 = choice2;
    }

    public boolean isFinal() {
        return mIsFinal;
    }

    //Constructor
    public Page(int imageId, String text) {
        mImageId = imageId;
        mText = text;
        mChoice1 = null;
        mChoice2 = null;
        mIsFinal = true;

    }

    public int getImageId() {
        return mImageId;
    }


    public String getText() {
        return mText;
    }


    public Choice getChoice1() {
        return mChoice1;
    }


    public Choice getChoice2() {
        return mChoice2;
    }

}
