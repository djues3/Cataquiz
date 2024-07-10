package rs.edu.raf.cataquiz.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileDataSerializer
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileStoreModule {
    @Provides
    @Singleton
    fun provideAuthStore(
        @ApplicationContext context: Context,
    ): DataStore<Profile> = DataStoreFactory.create(
        produceFile = { context.dataStoreFile("profile.json") },
        serializer = ProfileDataSerializer(),
    )

}