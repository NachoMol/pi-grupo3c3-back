package com.explorer.equipo3.controller;


import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.dto.FavoritesDTO;
import com.explorer.equipo3.service.IFavoritesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private static final Logger logger = LogManager.getLogger(FavoritesController.class);

    @Autowired
    private IFavoritesService favoritesService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FavoritesDTO>> getFavoritesByUserId(@PathVariable Long id){
        logger.info("ingresamos al metodo de buscar favorite por Id");
        return ResponseEntity.ok(favoritesService.findFavoritesByUserID(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> addFavorite(@RequestBody FavoritesDTO favoritesDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritesService.saveFavorites(favoritesDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long id){
        logger.info("ingresamos al metodo de eliminar favorite");
        logger.info("id: " + id);
        Optional favoriteOptional = favoritesService.findFavoriteById(id);
        logger.info("favoriteOptional: + " + favoriteOptional);
        if(favoriteOptional.isPresent()){
            logger.info("favorite encontrado");
            System.out.println("Ingreso al IF");
            favoritesService.deleteFavoriteById(id);
            logger.info("favorite eliminado");
            return ResponseEntity.noContent().build();
        }
        logger.info("el favorite no fue encontrado");
        return ResponseEntity.notFound().build();
    }
}
