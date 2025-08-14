package com.kiran.animeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiran.animeapp.data.local.dao.AnimeDao
import com.kiran.animeapp.data.local.dao.RemoteKeysDao
import com.kiran.animeapp.data.local.entity.AnimeEntity
import com.kiran.animeapp.data.local.entity.RemoteKeys
import com.kiran.animeapp.data.util.Converters

@Database(
    entities = [AnimeEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}