package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.repository.IFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FavoritesService implements IFavoritesService{

    @Autowired
    private IFavoritesRepository favoritesRepository;

    @Override
    public List<Favorites> findFavoritesByUserID(Long userId) {
        return favoritesRepository.findFavoritesByUserID(userId);
    }

    @Override
    public Optional<Favorites> findFavoriteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Favorites saveFavorites(Favorites favorite) {
        return favoritesRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteById(Long id) {

    }

}
