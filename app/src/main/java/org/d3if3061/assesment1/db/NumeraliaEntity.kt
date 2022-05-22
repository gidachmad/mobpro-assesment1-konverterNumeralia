package org.d3if3061.assesment1.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeralia")
data class NumeraliaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var indeks1: Int,
    var indeks2: Int,
    var indeks3: Int,
    var indeks4: Int,
    var indeks5: Int,
    var indeks6: Int,
    var indeks7: Int
)
