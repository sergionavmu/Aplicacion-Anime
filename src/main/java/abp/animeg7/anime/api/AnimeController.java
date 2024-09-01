package abp.animeg7.anime.api;

import abp.animeg7.anime.model.Anime;
import abp.animeg7.anime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping("/helloworld")
    public ResponseEntity welcome() {
        return ResponseEntity.ok("hello world");
    }

    @GetMapping()
    public ResponseEntity getAnime() {
        return animeService.getAllAnime();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimeById(@PathVariable int id) {
        return animeService.getAnimeById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getAnimeByName(@PathVariable String name) {
        return animeService.getAnimeByName(name);
    }


    @PostMapping("/dataloads")
    public ResponseEntity dataLoad() {
        animeService.dataLoad();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity addAnime(@RequestBody Anime anime) {
        return animeService.addAnime(anime);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAnime(@PathVariable int id, @RequestBody Anime anime) {
        return animeService.updateAnime(id, anime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnime(@PathVariable int id) {
        return animeService.deleteAnime(id);
    }

}
