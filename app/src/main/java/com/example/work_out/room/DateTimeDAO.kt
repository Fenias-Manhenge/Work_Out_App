package com.example.work_out.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface DateTimeDAO {
    @Insert
    suspend fun insert(dateTimeEntity: DateTimeEntity)
}