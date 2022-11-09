package com.special.domain.repositories

import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.tour.Tour
import com.special.domain.entities.tour.TourPoint

interface TourRepository {
    suspend fun startTour(coordinate: Coordinate)

    suspend fun completeTour(coordinate: Coordinate)

    suspend fun updateImage(point: TourPoint)

    suspend fun myTourList(): List<Tour>
}