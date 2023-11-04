package com.example.lab07b.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {StudentEntity.class}, version = 1,exportSchema = false)

public abstract class RoomDB extends RoomDatabase {
    public abstract StudentDao studentDao();
    private static volatile RoomDB INSTANCE;
    private static final int NUMBER_OF_THREAD = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static RoomDB getDataBase(final Context context){
        if (INSTANCE==null){
            synchronized (RoomDB.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class,"student_table")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                StudentDao dao = INSTANCE.studentDao();
                dao.deleteAll();

                dao.insert(new StudentEntity(0,"Student1","student1@gmail.com"));
                dao.insert(new StudentEntity(0,"Student2","student2@gmail.com"));

            });
        }
    };
}
