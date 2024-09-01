package abp.animeg7.anime.service;

import abp.animeg7.anime.dao.HandlerSax;
import abp.animeg7.anime.dao.AnimeRepository;
import abp.animeg7.anime.model.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AnimeService {

    private AnimeRepository animeRepository;

    @Autowired
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public ResponseEntity getAllAnime() {
        List<Anime> animes = animeRepository.getAnime();
        if(animes.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(animes);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: no hay datos a mostrar");
    }

    public ResponseEntity getAnimeById(int id) {

        Anime anime = animeRepository.findById(id);

        if (anime != null) {
            return ResponseEntity.status(HttpStatus.OK).body(anime);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime no encontrado");
        }
    }

    public ResponseEntity getAnimeByName(String name) {

        Anime anime = animeRepository.findByName(name);

        if (anime != null) {
            return ResponseEntity.status(HttpStatus.OK).body(anime);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime no encontrado");
        }
    }

    public ResponseEntity addAnime(Anime anime) {

        if (anime != null) {
            animeRepository.addAnime(anime);
            return ResponseEntity.status(HttpStatus.CREATED).body("Anime agregado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Anime no válido");
        }
    }

    public ResponseEntity updateAnime(int id, Anime anime) {

        Anime existingAnime = animeRepository.findById(id);

        if (existingAnime != null) {
            animeRepository.updateAnime(id, anime);
            return ResponseEntity.status(HttpStatus.OK).body("Anime actualizado correctamente");
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime no encontrado");
        }
    }

    public ResponseEntity deleteAnime(int id) {
        Anime existingAnime = animeRepository.findById(id);

        if (existingAnime != null) {
            animeRepository.deleteAnime(id);
            return ResponseEntity.status(HttpStatus.OK).body("Anime eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime no encontrado");
        }
    }

    public void dataLoad() {
        try {
            File file = new File("Files/Anime.xml");
            List<Anime> animes = parseAnimeXML(file);
            for (Anime anime : animes) {
                animeRepository.addAnime(anime);
                System.out.println(anime);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static List<Anime> parseAnimeXML(File file) throws ParserConfigurationException, SAXException, IOException {
        HandlerSax handler = new HandlerSax();
        return handler.getAnimes(file.getPath());
    }
}
