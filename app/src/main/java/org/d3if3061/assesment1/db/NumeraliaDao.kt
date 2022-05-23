package org.d3if3061.assesment1.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumeraliaDao {
    @Insert
    fun insert(numeralia: NumeraliaEntity)

    @Query("SELECT * FROM numeralia ORDER BY id DESC ")
    fun getNumeralia(): LiveData<List<NumeraliaEntity>>

    @Query("DELETE FROM numeralia")
    fun clearData()

    @Delete
    fun deleteHistori(numeralia: NumeraliaEntity)
}