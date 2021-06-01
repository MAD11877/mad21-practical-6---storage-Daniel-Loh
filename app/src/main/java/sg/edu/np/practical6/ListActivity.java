package sg.edu.np.practical6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    //static ArrayList<User> myList = new ArrayList<>();
    private SQLiteDatabase myDatabase;
    static ArrayList<User> arrayList = new ArrayList<>();
    //DatabaseHelper databaseHelper;
    myDBHandler dbHandler = new myDBHandler(this, null, null, 1);

    public interface OnItemClickListener{
        void onItemClick(ImageView item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_list);

        ImageView image = findViewById(R.id.imageView);

        ArrayList<User> myList = new ArrayList<>();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Profile");
                builder.setMessage("How do you make an octopus laugh? \n\nYou give it ten-tickles.");
                builder.setCancelable(true);
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int image) {
                        dialog.cancel();
                        Intent view = new Intent(ListActivity.this, MainActivity.class);
                        Random ran = new Random();
                        int ranval = ran.nextInt(1000000000);
                        view.putExtra("random", ranval);
                        startActivity(view);
                    }
                });
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int image) {
                        dialog.cancel();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });*/

        //Create a List of 20 User objects in the ListActivity. Randomize the name, descriptions
        // and value of followed

        setContentView(R.layout.activity_list);
        //arrayList = new ArrayList<>();
        int count = dbHandler.getProfileCount();
        if(count == 20)
        {
            arrayList = new ArrayList<>();
            arrayList = dbHandler.getUsers();
        }
        else if (count < 20)
        {
            boolean follow;
            for (int i = 0; i < 20; i++) {
                Random ran = new Random();
                int ranuser = ran.nextInt();
                int randesc = ran.nextInt();
                int ranfollow = ran.nextInt();

                if (ranfollow % 2 == 1) {
                    follow = true;
                } else {
                    follow = false;
                }
                //User user = dbHandler.findUser(ranuser);
                /*User dbuserdata = new User();
                dbuserdata.setName("Name" + ranuser);
                dbuserdata.setId(i+1);
                dbuserdata.setDescription("Description " + randesc);
                dbuserdata.setFollowed(follow);
                dbHandler.addUser(dbuserdata);*/
                User dbuserdata = new User("Name" + ranuser, "Description " + randesc, ranuser, follow);
                dbHandler.addUser(dbuserdata);
        }

        }
        arrayList = dbHandler.getUsers();
        RecyclerView recyclerView = findViewById(R.id.recyclerviewer);
        CustomAdaptor cAdaptor = new CustomAdaptor(arrayList);
        LinearLayoutManager cLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(cLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdaptor);
    }
}
