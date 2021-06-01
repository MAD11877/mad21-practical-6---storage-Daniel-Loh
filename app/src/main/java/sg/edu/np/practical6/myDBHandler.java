package sg.edu.np.practical6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class myDBHandler extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "UserDB.db";
    public static String USER = "User";
    public static String COLUMN_NAME = "Name";
    public static String COLUMN_DESC = "Description";
    public static String COLUMN_ID = "ID";
    public static String COLUMN_FOLLOWED = "Followed";

    //List<User> usersList = new ArrayList<>();
    //ArrayList<User> userArrayList = new ArrayList<>();
    static ArrayList<User> userDBList = new ArrayList<>();

    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + USER + "(" + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT," + COLUMN_ID + " TEXT," + COLUMN_FOLLOWED + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        onCreate(db);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_FOLLOWED, user.isFollowed());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(USER, null, values); //Write into DB
        db.close();
    }

    public ArrayList<User> getUsers()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        User queryData = new User();

        String query = "SELECT * FROM " + USER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                userDBList.add(new User(cursor.getString(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3) == 1));
            } while (cursor.moveToNext());
        }
        db.close();
        return (userDBList);
    }

    public void updateUser(User userUpdate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(userUpdate.followed == false) {
            cv.put(COLUMN_FOLLOWED, 0);
        } else {
            cv.put(COLUMN_FOLLOWED, 1);
        }
        db.update(USER, cv, "ID=?", new String[]{String.valueOf(userUpdate.getId())});
        db.close();
    }

    public int getProfileCount() {
        String countQuery = "SELECT  * FROM " + USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
