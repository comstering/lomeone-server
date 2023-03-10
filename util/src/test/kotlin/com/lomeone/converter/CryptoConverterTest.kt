package com.lomeone.converter

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CryptoConverterTest : FreeSpec({
    val cryptoConverter = CryptoConverter()

    "convertToDatabaseColumn" - {
        "암호화하여 저장한다" {
            val attribute = "test"
            val dbData = cryptoConverter.convertToDatabaseColumn(attribute)
            dbData shouldNotBe "test"
        }
    }
    "convertToEntityAttribute" - {
        "복호화하여 가져온다" {
            val attribute = "test"
            val dbData = cryptoConverter.convertToDatabaseColumn(attribute)
            val decryptedAttribute = cryptoConverter.convertToEntityAttribute(dbData)
            decryptedAttribute shouldBe "test"
        }
    }
})
