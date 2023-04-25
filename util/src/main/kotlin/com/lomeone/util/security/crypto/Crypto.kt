package com.lomeone.util.security.crypto

interface Crypto {
    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String): String
}