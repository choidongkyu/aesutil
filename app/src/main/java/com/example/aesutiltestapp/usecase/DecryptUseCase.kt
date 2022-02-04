package com.example.aesutiltestapp.usecase

import com.example.aesutiltestapp.util.AES256Util

class DecryptUseCase {
    private val aesUtil by lazy {
        AES256Util()
    }

    fun decrypt(filePath: String) {
        aesUtil.decryptFile(filePath)
    }
}