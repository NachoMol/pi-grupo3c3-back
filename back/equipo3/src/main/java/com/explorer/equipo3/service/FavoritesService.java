package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.CategoryController;
import com.explorer.equipo3.controller.FavoritesController;
import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.FavoritesDTO;
import com.explorer.equipo3.repository.IFavoritesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService implements IFavoritesService{

    private static final Logger logger = LogManager.getLogger(FavoritesService.class);

    @Autowired
    private IFavoritesRepository favoritesRepository;

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public List<FavoritesDTO> findFavoritesByUserID(Long userId) {
        return favoritesRepository.findFavoritesByUserID(userId);
    }

    @Override
    @Transactional
    public Optional<Favorites> findFavoriteById(Long id) {
        return favoritesRepository.findById(id);
    }

    @Override
    @Transactional
    public Favorites saveFavorites(@Valid FavoritesDTO favoritesDTO) {
        logger.info("ingresamos al metodo de guardar favorite");
        Favorites favorites = new Favorites();
        Optional<Product> product = productService.getProductById(favoritesDTO.getProduct_id());
        logger.info("product encontrado");
        Optional<User> user = userService.getUserID(favoritesDTO.getUser_id());
        logger.info("user encontrado");
        favorites.setProduct(product.orElseThrow());
        favorites.setUser(user.orElseThrow());
        logger.info("favorite guardado");
        return favoritesRepository.save(favorites);
    }

    @Override
    @Transactional
    public void deleteFavoriteById(Long id) {
        favoritesRepository.deleteById(id);
    }

}
