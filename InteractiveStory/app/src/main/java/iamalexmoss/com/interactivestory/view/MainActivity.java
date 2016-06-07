package iamalexmoss.com.interactivestory.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import iamalexmoss.com.interactivestory.R;

public class MainActivity extends AppCompatActivity {

    //Variables
    private EditText mNameField;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameField = (EditText)findViewById(R.id.nameEditText);
        mStartButton = (Button)findViewById(R.id.startButton);

        //onClick of Start Button
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Get the text value from the mNameField
                String name = mNameField.getText().toString();
                startStory(name);
            }
        });
    }
    private void startStory(String name) {
        Intent intentStoryActivity = new Intent(this, StoryActivity.class);
        intentStoryActivity.putExtra(getString(R.string.key_name), name);
        startActivity(intentStoryActivity);
    }

    @Override
    protected void onResume() {
        //Will go back to launcher activity
        super.onResume();
        mNameField.setText("");
    }
}
