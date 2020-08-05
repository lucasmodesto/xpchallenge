package br.com.xpchallenge.data.extensions

import java.math.BigInteger
import java.security.MessageDigest

fun String.toMd5Hash(): String {
    return String.format(
        "%032x",
        BigInteger(
            1,
            MessageDigest.getInstance("MD5")
                .digest(this.toByteArray(Charsets.UTF_8))
        )
    )
}
