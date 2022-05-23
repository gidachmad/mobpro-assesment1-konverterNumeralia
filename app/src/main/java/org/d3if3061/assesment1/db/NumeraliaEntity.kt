package org.d3if3061.assesment1.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeralia")
data class NumeraliaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var bilangan: Int,
    var numeralia: String
)
