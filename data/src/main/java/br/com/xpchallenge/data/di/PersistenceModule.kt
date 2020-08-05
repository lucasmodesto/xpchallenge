package br.com.xpchallenge.data.di

import android.content.Context
import androidx.room.Room
import br.com.xpchallenge.data.local.room.MarvelAppRoomDatabase
import br.com.xpchallenge.data.local.room.dao.ICharacterDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PersistenceModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MarvelAppRoomDatabase {
        return Room.databaseBuilder(
            context,
            MarvelAppRoomDatabase::class.java,
            MarvelAppRoomDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesCharacterDao(roomDatabase: MarvelAppRoomDatabase): ICharacterDAO {
        return roomDatabase.dao()
    }
}
