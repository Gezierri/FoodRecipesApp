package com.example.foodrecipes.data.repository

import com.example.foodrecipes.data.local.LocalDataStore
import com.example.foodrecipes.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataStore: LocalDataStore
) {

    val remote = remoteDataSource
    val local = localDataStore
}