package io.ak1.nytimes.model

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "bookmarks_table", indices = [Index(value = ["title"], unique = true)])
class Bookmarks : Results()