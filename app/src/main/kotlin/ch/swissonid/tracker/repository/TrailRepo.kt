package ch.swissonid.tracker.repository

import ch.swissonid.tracker.model.Trail


interface TrailRepo {
    fun save(trail: Trail)
    fun getById(id: Long):Trail
    fun getAll(): List<Trail>
    fun eraseEverything()
    fun delete(trail: Trail)
}