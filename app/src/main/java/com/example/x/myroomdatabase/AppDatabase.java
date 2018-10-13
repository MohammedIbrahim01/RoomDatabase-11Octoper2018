package com.example.x.myroomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Task.class, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "app-database";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if (sInstance == null) {

            synchronized (LOCK) {

                sInstance = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract TaskDao taskDao();
}
