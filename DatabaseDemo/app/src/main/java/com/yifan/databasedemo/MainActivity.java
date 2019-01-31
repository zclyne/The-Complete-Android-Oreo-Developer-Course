package com.yifan.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        // create a table
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))"); // 3 is the number of digits of age
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)"); // 3 is the number of digits of age

        // add items into the table
//        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 28)");
//        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Yifan', 20)");
//        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Shuang', 8)");
//        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 43)");
//        myDatabase.execSQL("DELETE FROM users WHERE name = 'Yifan'"); // delete every item with name = "Yifan"
        myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Nick', 28)");
        myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Yifan', 20)");
        myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Shuang', 8)");
        myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Nick', 43)");
        // SQLite automatically increment id for every item we add into newUsers, and we don't have to set it manually
        // id starts from 1

        // get things out of the table
        // query is pulling sth out of the database
        Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers", null);
//        Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name = 'Nick' AND AGE = 43", null);
//        Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name LIKE 'N%'", null); // get everyone whose name starts with letter 'N'
//        Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name LIKE '%a%' LIMIT 1", null); // get the 1st answer who has an 'a' in his/her name
        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");
        int idIndex = c.getColumnIndex("id");
        c.moveToFirst(); // move the cursor to the starting position, which is "Nick" in users
        // the following is wrong
//        while (c != null) {
//            Log.i("name", c.getString(nameIndex));
//            Log.i("age", c.getString(ageIndex));
//            c.moveToNext();
//        }
        do {
            Log.i("name", c.getString(nameIndex));
            // both of the following lines work well
//            Log.i("age", c.getString(ageIndex));
            Log.i("age", String.valueOf(c.getInt(ageIndex)));
            Log.i("id", String.valueOf(c.getInt(idIndex)));
        } while (c.moveToNext());
    }
}
