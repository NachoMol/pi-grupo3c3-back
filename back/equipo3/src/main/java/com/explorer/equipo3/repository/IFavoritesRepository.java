package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFavoritesRepository extends JpaRepository <Long, Favorites> {
}
