package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoritesRepository extends JpaRepository <Favorites, Long> {

    @Query("SELECT f FROM Favorites f WHERE user_id = :userId")
    List<Favorites> findFavoritesByUserID(Long userId);
}
