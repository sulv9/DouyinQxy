package com.qxy.lib.account.ext

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * 加密SharedPreferences的封装
 * https://stackoverflow.com/questions/62498977/how-to-create-masterkey-after-masterkeys-deprecated-in-android
 */
// sp文件名
const val SECURE_USER_FILE_NAME = "dou_yin_secure_user_info"

/**
 * [KeyGenParameterSpec](https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec)
 */
private val keyGenParameterSpec = KeyGenParameterSpec.Builder(
    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
)
    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
    .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
    .build()

/**
 * [MasterKey](https://developer.android.com/reference/androidx/security/crypto/MasterKey)
 */
private fun getMasterKey(context: Context) =
    MasterKey.Builder(context).setKeyGenParameterSpec(keyGenParameterSpec).build()

/**
 * 获取[EncryptedSharedPreferences]
 */
val Context.secureSharedPref: SharedPreferences
    get() = EncryptedSharedPreferences.create(
        this,
        SECURE_USER_FILE_NAME,
        getMasterKey(this),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
