package com.beomsu317.use_case.common

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

fun getSha256WithSalt(value: String, length: Int = 32): String {
    val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(length)
    val encodedSalt = Hex.encodeHexString(salt)
    val hash = DigestUtils.sha256Hex("${encodedSalt}${value}")
    return "${encodedSalt}:${hash}"
}

fun validatePasswordHash(passwordHashWithSalt: String, password: String): Boolean {
    val (salt, passwordHash) = passwordHashWithSalt.split(':')
    val hash = DigestUtils.sha256Hex("${salt}${password}")
    return hash == passwordHash
}

fun checkPasswordConstraints(password: String): Boolean {
    return Regex("^.{8,32}$").containsMatchIn(password)
}
