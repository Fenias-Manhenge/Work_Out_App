package com.example.work_out.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeWorkOut")
data class DateTimeEntity(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int = 0,
    @ColumnInfo("Date")
    val date: String = "",
    @ColumnInfo("Time")
    val time: String = ""
)
