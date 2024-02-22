package com.example.work_out.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [DateTimeEntity::class], version = 1)
abstract class DateTimeDataBase: RoomDatabase() {

    abstract fun dateTimeDao(): DateTimeDAO

    companion object{
        @Volatile
        private var INSTANCE: DateTimeDataBase? = null

        fun getInstance(context: Context): DateTimeDataBase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DateTimeDataBase::class.java,
                        "TimeWorkOut"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}