package com.lomeone.util.converter

import com.lomeone.util.security.crypto.AESCrypto
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class AESCryptoConverter : AttributeConverter<String,String> {

    private val crypto = AESCrypto("1234567890abcdef")

    override fun convertToDatabaseColumn(attribute: String) = this.crypto.encrypt(attribute)

    override fun convertToEntityAttribute(dbData: String) = this.crypto.decrypt(dbData)
}