package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.dto.FavoritesDTO;

import java.util.List;
import java.util.Optional;

public interface IFavoritesService {

    List<Favorites> findFavoritesByUserID(Long id);

    Optional<Favorites> findFavoriteById(Long id);

    Favorites saveFavorites(FavoritesDTO favoriteDTO);

    void deleteFavoriteById(Long id);

}
