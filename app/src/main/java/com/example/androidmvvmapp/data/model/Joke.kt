package com.example.androidmvvmapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class that stores joke content
 */
@Entity
data class Joke(
    /**
     * Stores id of the joke
     */
    @PrimaryKey(autoGenerate = true)
    val jokeId: Int?,
    /**
     * Stores category of the joke
     */
    @SerializedName("category")
    @Expose
    val category: String,
    /**
     * Stores type of the joke
     */
    @SerializedName("type")
    @Expose
    val type: String,
    /**
     * Stores setup of the joke if it's type is not single
     */
    @SerializedName("setup")
    @Expose
    val setup: String = "",
    /**
     * Stores delivery of the joke if it's type is not single
     */
    @SerializedName("delivery")
    @Expose
    val delivery: String = "",
    /**
     * Stores flags of the current joke
     */
    @SerializedName("flags")
    @Expose
    val flags: Flags?,
    /**
     * Stores id of the joke object
     */
    @SerializedName("id")
    @Expose
    val id: Int,
    /**
     * Stores boolean value whether joke is safe or not
     */
    @SerializedName("safe")
    @Expose
    val safe: Boolean,
    /**
     * Stores language of the joke
     */
    @SerializedName("lang")
    @Expose
    val lang: String,
    /**
     * Stores content of the joke if it's type is single
     */
    @SerializedName("joke")
    @Expose
    val joke: String = ""
) {
    //Custom constructor for joke data class
    constructor(
        category: String,
        type: String,
        setup: String,
        delivery: String,
        flags: Flags?,
        id: Int,
        safe: Boolean, lang: String
    ) : this(
        null,
        category,
        type,
        setup,
        delivery,
        flags,
        id,
        safe, lang, ""
    )

    constructor(
        category: String,
        type: String,
        flags: Flags?,
        id: Int,
        safe: Boolean, lang: String, joke: String
    ) : this(
        null, category, type, "", "", flags, id, safe, lang, joke
    )
}