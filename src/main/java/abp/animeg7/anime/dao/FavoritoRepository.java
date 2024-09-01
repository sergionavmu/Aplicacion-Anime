package abp.animeg7.anime.dao;

import abp.animeg7.anime.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoritoRepository {

    private final String GET_FAVORITO = "call anime.getAllFavoritos()";
    private final String GET_FAVORITO_BY_ID = "call anime.getFavoritoById(?)";
    private final String INSERT_FAVORITO = "call anime.insertFavorito(?,?)";
    private final String DELETE_FAVORITO = "call anime.deleteFavoritoById(?, ?)";
    private final String GET_FAVORITO_ANIME = "call anime.getFavoritoByUserByAnime(?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoritoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFavorito(Favorito favorito) {
        jdbcTemplate.update(INSERT_FAVORITO, favorito.getIduser(), favorito.getIdanime());

    }
    public Favorito findFavorito(int userId, int animeId) {
        List<Favorito> favoritos = jdbcTemplate.query(GET_FAVORITO_ANIME,
                new Object[]{userId, animeId},
                new BeanPropertyRowMapper<>(Favorito.class));
        return favoritos.isEmpty() ? null : favoritos.get(0);
    }
    public List<Favorito> getFavorito(){
        return jdbcTemplate.query(GET_FAVORITO, new BeanPropertyRowMapper<>(Favorito.class));
    }

    public List<Favorito> getFavoritoById(int userId) {
        return jdbcTemplate.query(GET_FAVORITO_BY_ID,
                new Object[]{userId},
                new BeanPropertyRowMapper<>(Favorito.class));
    }

    public boolean deleteFavorito(int userId, int animeId) {
        int rowsAffected = jdbcTemplate.update(DELETE_FAVORITO, userId, animeId);
        return rowsAffected > 0;
    }
}
