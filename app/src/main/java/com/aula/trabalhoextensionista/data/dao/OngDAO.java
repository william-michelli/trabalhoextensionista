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
public interface OngDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert (Ong ong);

    @Query("SELECT * FROM Ong")
    public List<Ong> getAllOngs();

    @Query("SELECT * FROM Ong WHERE id = :id")
    public Ong getOngById(int id);

    @Update
    public void editarOng(Ong ong);

    @Delete
    public void deleteOng(Ong ong);
}
