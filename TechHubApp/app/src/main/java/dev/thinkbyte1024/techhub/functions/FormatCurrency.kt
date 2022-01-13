package dev.thinkbyte1024.techhub.functions

import java.text.NumberFormat
import java.util.*

fun FormatCurrency(price: Int): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")

    return format.format(price)
}