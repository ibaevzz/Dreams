package com.example.dreams

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dream(@PrimaryKey var quoteLink:String = "", var quoteText: String = "", var quoteAuthor: String = "")