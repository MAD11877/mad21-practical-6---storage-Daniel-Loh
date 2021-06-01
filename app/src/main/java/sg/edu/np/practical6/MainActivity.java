package sg.edu.np.practical6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Main Activity";
    myDBHandler dbHandler = new myDBHandler(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "On Create!");

        // Identifying Widgets
        Button button = findViewById(R.id.button);  // Identify button (Follow)
        Button button2 = findViewById(R.id.button2);  // Identify button2 (Message)
        TextView textView = findViewById(R.id.textView); // Identify TextView (Name)
        TextView textView2 = findViewById(R.id.textView2); // Identify TextView (Description)

        int a = getIntent().getIntExtra("a",0);

        //User user = ListActivity.myList.get(a);
        User user = ListActivity.arrayList.get(a);

        // Initialize name and description texts from the User object
        textView.setText(user.getName());
        textView2.setText(user.getDescription());

        //  initialize Button Text based on Followed Bool
        if (user.followed == true){
            button.setText("Unfollow");
        }
        else {
            button.setText("Follow");
        }

        // Change the button text after clicking and update the bool
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.followed == true) {
                    button.setText("Follow");
                    user.setFollowed(false);
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                    dbHandler.updateUser(user);
                }
                else {
                    button.setText("Unfollow");
                    user.setFollowed(true);
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                    dbHandler.updateUser(user);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "On Start!");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "On Resume!");
    }

    @Override
    protected void  onPause(){
        super.onPause();
        Log.v(TAG, "On Pause!");

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "On Destroy");
    }
}