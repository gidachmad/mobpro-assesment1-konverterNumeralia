package org.d3if3061.assesment1.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumeraliaDao {
    @Insert
    fun insert(numeralia: NumeraliaEntity)

    @Query("SELECT * FROM numeralia ORDER BY id DESC ")
    fun getLastNumeralia(): LiveData<List<NumeraliaEntity>>


}