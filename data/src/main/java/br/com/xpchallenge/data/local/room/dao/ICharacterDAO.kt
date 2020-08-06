package br.com.xpchallenge.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.xpchallenge.data.local.room.model.CharacterDBModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface ICharacterDAO {

    @Query("SELECT * from characters ORDER BY name ASC")
    fun getCharacters(): Single<List<CharacterDBModel>>

    @Query("SELECT * from characters ORDER BY name ASC")
    fun observeCharacters(): Observable<List<CharacterDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharacterDBModel): Completable

    @Query("DELETE FROM characters WHERE id = :id")
    fun deleteById(id: Int): Completable
}
