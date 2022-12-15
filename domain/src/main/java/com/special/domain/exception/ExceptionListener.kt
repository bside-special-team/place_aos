package com.special.domain.exception

interface ExceptionListener {
    fun updateException(e: Throwable)
}