package com.pethabittracker.gora.presentation.models

sealed class Lce <out T> {

    object Loading : Lce<Nothing>()

    data class Content<T>(val value: T) : Lce<T>()

    data class Error<T>(val throwable: Throwable) : Lce<Nothing>()

}
