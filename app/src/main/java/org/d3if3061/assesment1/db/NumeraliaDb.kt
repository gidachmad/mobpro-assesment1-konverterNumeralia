package org.d3if3061.assesment1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NumeraliaEntity::class], version = 1, exportSchema = false)
abstract class NumeraliaDb : RoomDatabase() {

    abstract val dao : NumeraliaDao

    companion object {
        @Volatile
        private var INSTANCE: NumeraliaDb? = null

        fun getInstance(context: Context): NumeraliaDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NumeraliaDb::class.java,
                        "numeralia.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}