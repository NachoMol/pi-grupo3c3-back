package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.dto.FavoritesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoritesRepository extends JpaRepository <Favorites, Long> {

    @Query("SELECT new com.explorer.equipo3.model.dto.FavoritesDTO(f.id, f.user.id, f.product.id, f.created_at, f.updated_at) FROM Favorites f WHERE f.user.id = :userId")
    List<FavoritesDTO> findFavoritesByUserID(Long userId);
}
