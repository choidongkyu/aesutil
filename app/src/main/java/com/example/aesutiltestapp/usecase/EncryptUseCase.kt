package com.example.aesutiltestapp.usecase

import com.example.aesutiltestapp.util.AES256Util

class EncryptUseCase {
    private val aesUtil by lazy {
        AES256Util()
    }

    fun encrypt(filePath: String) {
        aesUtil.encryptFile(filePath)
    }
}