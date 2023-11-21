package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFavoritesRepository extends JpaRepository <Favorites, Long> {

    @Query("SELECT * FROM favorites WHERE user_id = :userId")
    List<Favorites> findFavoritesByUserID(Long userId);
}
