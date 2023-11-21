package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Favorites;

import java.util.List;
import java.util.Optional;

public interface IFavoritesService {

    List<Favorites> findFavoritesByUserID(Long id);

    Optional<Favorites> findFavoriteById(Long id);

    Favorites saveFavorites(Favorites favorite);

    void deleteFavoriteById(Long id);

}
