package ru.aol_panchenko.weatherapp.utils

import io.reactivex.disposables.Disposable

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
fun unsubscribe(subscription: Disposable?) {
    if (subscription != null && !subscription.isDisposed) {
        subscription.dispose()
    }
}

fun String.firstLettersToUpperCase() =
        this.split(" ").map { it[0].toUpperCase().plus(it.substring(1)) }.toString()
                .replace(Regex("[,\\[\\]]"), "").trim()