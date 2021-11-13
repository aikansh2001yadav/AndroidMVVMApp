package com.example.androidmvvmapp.data.db

import androidx.room.TypeConverter
import com.example.androidmvvmapp.data.model.Flags
import org.json.JSONObject

/**
 * Custom Converter class for Flags object
 */
class FlagsTypeConverter {
    @TypeConverter
    fun fromFlags(flags: Flags): String {
        return JSONObject().apply {
            put("nsfw", flags.nsfw)
            put("religious", flags.religious)
            put("political", flags.political)
            put("racist", flags.racist)
            put("sexist", flags.sexist)
            put("explicit", flags.explicit)
        }.toString()
    }

    @TypeConverter
    fun toFlags(flags: String): Flags {
        val json = JSONObject(flags)
        return Flags(
            json.getBoolean("nsfw"),
            json.getBoolean("religious"),
            json.getBoolean("political"),
            json.getBoolean("racist"),
            json.getBoolean("sexist"),
            json.getBoolean("explicit")
        )
    }
}