package fr.tdaumas.android.tp2.dal.utils

import fr.tdaumas.android.tp2.dal.room.entities.NeighborEntity
import fr.tdaumas.android.tp2.models.Neighbor

fun NeighborEntity.toNeighbor() = Neighbor(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    address = address,
    phoneNumber = phoneNumber,
    aboutMe = aboutMe,
    favorite = favorite,
    webSite = webSite ?: ""
)

fun Neighbor.toEntity() = NeighborEntity(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    address = address,
    phoneNumber = phoneNumber,
    aboutMe = aboutMe,
    favorite = favorite,
    webSite = webSite
)