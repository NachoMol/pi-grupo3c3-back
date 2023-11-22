package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.FavoritesDTO;
import com.explorer.equipo3.repository.IFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService implements IFavoritesService{

    @Autowired
    private IFavoritesRepository favoritesRepository;

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @Override
    public List<FavoritesDTO> findFavoritesByUserID(Long userId) {
        return favoritesRepository.findFavoritesByUserID(userId);
    }

    @Override
    public Optional<Favorites> findFavoriteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Favorites saveFavorites(@Valid FavoritesDTO favoritesDTO) {
        Favorites favorites = new Favorites();
        Optional<Product> product = productService.getProductById(favoritesDTO.getProduct_id());
        Optional<User> user = userService.getUserID(favoritesDTO.getUser_id());
        favorites.setProduct(product.orElseThrow());
        favorites.setUser(user.orElseThrow());
        return favoritesRepository.save(favorites);
    }

    @Override
    public void deleteFavoriteById(Long id) {
        favoritesRepository.deleteById(id);
    }

}
