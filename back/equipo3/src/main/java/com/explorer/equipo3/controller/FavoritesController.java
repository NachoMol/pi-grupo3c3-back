package com.explorer.equipo3.controller;


import com.explorer.equipo3.model.Favorites;
import com.explorer.equipo3.model.dto.FavoritesDTO;
import com.explorer.equipo3.service.IFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    private IFavoritesService favoritesService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FavoritesDTO>> getFavoritesByUserId(@PathVariable Long id){
        return ResponseEntity.ok(favoritesService.findFavoritesByUserID(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> addFavorite(@RequestBody FavoritesDTO favoritesDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritesService.saveFavorites(favoritesDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long id){
        System.out.println("id: " + id);
        Optional favoriteOptional = favoritesService.findFavoriteById(id);
        System.out.println("favoriteOptional: + " + favoriteOptional);
        if(favoriteOptional.isPresent()){
            System.out.println("Ingreso al IF");
            favoritesService.deleteFavoriteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
