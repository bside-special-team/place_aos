package com.special.data.repoimpl

import com.special.domain.datasources.UserRemoteDataSource
import com.special.domain.repositories.UserRepository

class UserRepoImpl constructor(private val userRemote: UserRemoteDataSource): UserRepository {

}