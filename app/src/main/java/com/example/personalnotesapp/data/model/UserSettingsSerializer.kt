package com.example.personalnotesapp.data.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserSettingsSerializer : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: UserSettings,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = UserSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}

//object UserSettingsSerializer : Serializer<UserSettings> {
//    override val defaultValue: UserSettings
//        get() = UserSettingsKt.getDefaultInstance()
//
//    override suspend fun readFrom(input: InputStream): UserSettings {
//        try {
//            return UserSettingsKt.parseFrom(input)
//        } catch (e: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto", e)
//        }
//    }
//
//    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
//        t.writeTo(output)
//    }
//}