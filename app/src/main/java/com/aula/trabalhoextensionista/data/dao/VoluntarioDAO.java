package com.aula.trabalhoextensionista.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.models.Voluntario;

import java.util.List;

@Dao
public interface VoluntarioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Voluntario voluntario);

    @Query("SELECT * FROM Voluntario")
    public List<Voluntario> getAllVoluntarios();

    @Query("SELECT * FROM Voluntario WHERE id = :id")
    public Voluntario getVoluntarioById(int id);

    @Update
    public void editarVoluntario(Voluntario voluntario);

    @Delete
    public void deleteVoluntario(Voluntario voluntario);
}
