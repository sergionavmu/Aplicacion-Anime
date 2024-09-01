package abp.animeg7.anime.api;

import abp.animeg7.anime.model.Favorito;
import abp.animeg7.anime.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/add")
    public ResponseEntity addFavorito(@RequestBody Favorito favorito) {
        return favoritoService.addFavorito(favorito);
    }

    @PostMapping("/dataloads")
    public ResponseEntity dataLoad() {
        favoritoService.dataLoad();
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity getAnime() {
        return favoritoService.getAllFavorito();
    }

    @GetMapping("/{userId}")
    public ResponseEntity getFavoritoById(@PathVariable int userId) {
        return favoritoService.getFavoritoById(userId);
    }

    @DeleteMapping("/{userId}/{animeId}")
    public ResponseEntity deleteFavorito(@PathVariable int userId, @PathVariable int animeId) {
        return favoritoService.deleteFavorito(userId, animeId);
    }
}
