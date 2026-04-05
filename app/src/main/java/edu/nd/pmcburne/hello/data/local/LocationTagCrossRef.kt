package edu.nd.pmcburne.hello.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["locationId", "tag"])
data class LocationTagCrossRef(
    val locationId: Int,
    val tag: String
)