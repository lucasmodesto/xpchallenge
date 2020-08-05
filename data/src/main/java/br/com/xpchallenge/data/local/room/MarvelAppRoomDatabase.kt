package br.com.xpchallenge.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import br.com.xpchallenge.data.local.room.dao.ICharacterDAO

@Database(entities = [CharacterDBModel::class], version = 1)
abstract class MarvelAppRoomDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "MarvelAppDatabase"
    }

    abstract fun dao(): ICharacterDAO
}
