package ru.aol_panchenko.weatherapp.utils

import io.reactivex.disposables.Disposable
import ru.aol_panchenko.weatherapp.UNKNOWN_TERRITORY

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
fun unsubscribe(subscription: Disposable?) {
    if (subscription != null && !subscription.isDisposed) {
        subscription.dispose()
    }
}

fun String.firstLettersToUpperCase(): String {
    if (this.isNotEmpty()) {
        return this.split(" ").map { it[0].toUpperCase().plus(it.substring(1)) }.toString()
                .replace(Regex("[,\\[\\]]"), "").trim()
    }
    return UNKNOWN_TERRITORY
}
