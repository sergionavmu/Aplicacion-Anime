package abp.animeg7.anime.service;

import abp.animeg7.anime.dao.FavoritoRepository;
import abp.animeg7.anime.dao.HandlerSax;
import abp.animeg7.anime.model.Anime;
import abp.animeg7.anime.model.Favorito;
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
public class FavoritoService {

    private FavoritoRepository favoritoRepository;

    @Autowired
    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    public ResponseEntity getAllFavorito() {
        List<Favorito> favoritos = favoritoRepository.getFavorito();
        if(favoritos.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(favoritos);
        else
            return ResponseEntity.status(HttpStatus.OK).body("ERROR: no hay datos a mostrar");
    }

    public ResponseEntity addFavorito(Favorito favorito) {
        // Comprueba si el favorito ya existe
        Favorito existingFavorito = favoritoRepository.findFavorito(favorito.getIduser(), favorito.getIdanime());
        if (existingFavorito != null) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"favorite does not exists\"}");
        } else {
            favoritoRepository.addFavorito(favorito);
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Favorito\"}");
        }
    }



    public ResponseEntity getFavoritoById(int userId) {
        List<Favorito> favoritos = favoritoRepository.getFavoritoById(userId);
        if (!favoritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(favoritos);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Favorito no encontrado con id \"}" + userId);
        }
    }


    public ResponseEntity deleteFavorito(int userId, int animeId) {
        if (favoritoRepository.deleteFavorito(userId, animeId)) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Favorito eliminado\"}");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Favorito no encontrado\"}");
        }
    }


    public void dataLoad() {
        try {
            File file = new File("Files/Anime.xml");
            List<Favorito> favoritos = parseAnimeXML(file);
            for (Favorito favorito : favoritos) {
                favoritoRepository.addFavorito(favorito);
                System.out.println(favorito);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static List<Favorito> parseAnimeXML(File file) throws ParserConfigurationException, SAXException, IOException {
        HandlerSax handler = new HandlerSax();
        return handler.getFavoritos(file.getPath());
    }
}
