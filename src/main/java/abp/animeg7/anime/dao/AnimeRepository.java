package abp.animeg7.anime.dao;

import abp.animeg7.anime.model.Anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AnimeRepository {

    private final String GET_ANIMES = "call anime.getAllAnimes()";
    private final String GET_ANIME_BY_ID = "call anime.getAnimeById(?)";
    private final String GET_ANIME_BY_NAME = "call anime.getAnimeByName(?)";
    private final String INSERT_ANIME = "call anime.insertAnime(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_ANIME = "call anime.updateAnimeById(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_ANIME = "call anime.deleteAnimeById(?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Anime findById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_ANIME_BY_ID,
                    new Object[]{id},
                    new BeanPropertyRowMapper<>(Anime.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Anime findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(GET_ANIME_BY_NAME,
                    new Object[]{name},
                    new BeanPropertyRowMapper<>(Anime.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Anime> getAnime(){
        return jdbcTemplate.query(GET_ANIMES, new BeanPropertyRowMapper<>(Anime.class));
    }

    public void addAnime(Anime anime) {
        jdbcTemplate.update(INSERT_ANIME,
                anime.getName(),
                anime.getDescription(),
                anime.getType(),
                anime.getYear(),
                anime.getImage(),
                anime.getOriginalName(),
                anime.getRating(),
                anime.getDemography(),
                anime.getGenre(),
                anime.getImage2(),
                anime.isActive()
        );
    }

    public void updateAnime (int id, Anime anime) {
        jdbcTemplate.update(UPDATE_ANIME,
                id,
                anime.getName(),
                anime.getDescription(),
                anime.getType(),
                anime.getYear(),
                anime.getImage(),
                anime.getOriginalName(),
                anime.getRating(),
                anime.getDemography(),
                anime.getGenre(),
                anime.getImage2(),
                anime.isActive()
        );
    }

    public void deleteAnime (int id) {
        jdbcTemplate.update(DELETE_ANIME, id);
    }

}
