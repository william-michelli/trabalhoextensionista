package com.aula.trabalhoextensionista.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aula.trabalhoextensionista.data.dao.VoluntarioDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.dao.OngDAO;
import com.aula.trabalhoextensionista.data.models.Voluntario;

@Database(
        entities = {
                Ong.class,
                Voluntario.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OngDAO OngDAO(); //Metodo abstrato
    public abstract VoluntarioDAO voluntarioDAO();
    public static volatile AppDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;

    static AppDatabase getDatabase(final Context context) { //Singleton
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) { //Synchronized = somente uma thread acessa o objeto
               if (INSTANCE == null) {
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           AppDatabase.class, "app.db").build();
               }
            }
        }
        return INSTANCE;
    }
}
