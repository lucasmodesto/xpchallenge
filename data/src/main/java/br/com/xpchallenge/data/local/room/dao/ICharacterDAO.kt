package br.com.xpchallenge.data.local.room.dao

import androidx.room.*
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

    @Delete
    fun delete(character: CharacterDBModel)

    @Query("DELETE FROM characters WHERE id = :id")
    fun deleteById(id: Int): Completable

//    @Query("UPDATE characters SET isFavorite=:isFavorite WHERE id = :id")
//    fun updateFavorite(id: Int, isFavorite: Boolean)

}