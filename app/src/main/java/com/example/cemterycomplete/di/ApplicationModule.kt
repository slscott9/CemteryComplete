package com.example.cemterycomplete.di

import android.content.Context
import com.example.cemterycomplete.data.CemeteryRepositoryImpl
import com.example.cemterycomplete.data.local.CemeteryDao
import com.example.cemterycomplete.data.local.CemeteryDataSource
import com.example.cemterycomplete.data.local.CemeteryLocalDataSourceImpl
import com.example.cemterycomplete.data.local.CemeteryRoomDatabase
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSource
import com.example.cemterycomplete.data.remote.CemeteryRemoteDataSourceImpl
import com.example.cemterycomplete.network.CemeteryServiceApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

//We can return interfaces for CemeteryRemoteDataSourceImpl and CemeteryLocalDataSourceImpl since they both define those interfaces
//This allows the repoimpl to take the interfaces as dependencies.
//We can now make fake data sources that extend the interfaces and build the test repo with them.

@InstallIn(ApplicationComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideCemeteryDao(db: CemeteryRoomDatabase) = db.cemDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context)
            = CemeteryRoomDatabase.getDatabase(appContext)


    //dependencies are remoteInterface (which is CemeteryRemoteImpl) and localInterface (which is defined as localImpl)
    //returns the repo impl
    @Singleton
    @Provides
    fun provideCemeteryRepoImpl(cemeteryLocalDataSourceImpl: CemeteryDataSource, cemeteryRemoteDataSource: CemeteryRemoteDataSource): CemeteryRepositoryImpl {
        return CemeteryRepositoryImpl(cemeteryLocalDataSourceImpl, cemeteryRemoteDataSource)
    }
    /*
        Returns a Remote interface which is defined as remote implementation which defines the interface methods
     */
    @Singleton
    @Provides
    fun provideCemeteryRemoteDataSourceImpl( cemeteryServiceApi: CemeteryServiceApi,
                                         moshi : Moshi
    ): CemeteryRemoteDataSource {

        return CemeteryRemoteDataSourceImpl(cemeteryServiceApi, moshi)
    }

    /*
        Returns a CemeteryDataSource interface which is defined by the CemeteryLocalDataSourceImpl of the interface
     */
    @Singleton
    @Provides
    fun provideLocalDataSourceImpl(cemeteryDao: CemeteryDao) : CemeteryDataSource {
        return CemeteryLocalDataSourceImpl(cemeteryDao)
    }



    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideCemeteryService(retrofit: Retrofit): CemeteryServiceApi = retrofit.create(CemeteryServiceApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://dts.scott.net")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    @Provides
    fun provideOkHttpclient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

}