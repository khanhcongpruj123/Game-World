package com.icongkhanh.gameworld.util

import com.icongkhanh.gameworld.domain.model.Game

fun Game.merge(other: Game): Game {
    if (other == null) return this.copy()
    var id = if (this.id == 0L) other.id else this.id
    var name = if (this.name.isEmpty()) other.name else this.name
    var description = if (this.description.isEmpty()) other.description else this.description
    var website = if (this.website.isEmpty()) other.website else this.website
    var genre = if (this.genre.isEmpty()) other.genre else this.genre
    var platforms = if (this.platforms.isEmpty()) other.platforms else this.platforms
    val stores = other.stores

    return this.copy(
        id = id,
        name = name,
        description = description,
        website = website,
        genre = genre,
        platforms = platforms,
        stores = stores
    )
}