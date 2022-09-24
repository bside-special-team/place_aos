package com.special.data

import com.special.data.repoimpl.UserRepoImpl
import com.special.domain.repositories.UserRepository
import com.special.mock.UserMockDataImpl
import com.special.remote.impls.UserRemoteDataImpl

class BindModule {

    fun getUserRepo(isMock: Boolean): UserRepository {
        return if (isMock) {
            UserRepoImpl(UserMockDataImpl())
        } else {
            UserRepoImpl(UserRemoteDataImpl())
        }
    }
}