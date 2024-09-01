package abp.animeg7.anime.dao;

import abp.animeg7.anime.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepository {
    private final String GET_VIDEOS = "call anime.getAllVideos()";
    private final String GET_VIDEO_BY_ANIME = "call anime.getVideoByAnimeId(?)";
    private final String GET_VIDEOS_BY_ID = "call anime.getVideosById(?)";
    private final String INSERT_VIDEO = "call anime.insertVideo(?, ?, ?, ?)";
    private final String UPDATE_VIDEO = "call anime.updateVideoById(?, ?, ?, ?)";
    private final String DELETE_VIDEO = "call anime.deleteVideoById(?,?)";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public VideoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Video findVideoById (int id) {
        try {
            return jdbcTemplate.queryForObject(GET_VIDEOS_BY_ID,
                    new Object[]{id},
                    new BeanPropertyRowMapper<>(Video.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Video> getVideos() {
        return jdbcTemplate.query(GET_VIDEOS, new BeanPropertyRowMapper<>(Video.class));
    }

    public List<Video> getVideosByAnime(int idAnime) {
        return jdbcTemplate.query(GET_VIDEO_BY_ANIME, new BeanPropertyRowMapper<>(Video.class), idAnime);
    }
    public void addVideos (Video video) {
        jdbcTemplate.update(INSERT_VIDEO,
                video.getIdanime(),
                video.getEpisode(),
                video.getUrl(),
                video.getImage()
        );
    }

    public void updateVideos (int idanime, int episode, Video video) {
        jdbcTemplate.update(UPDATE_VIDEO,
                idanime,
                episode,
                video.getUrl(),
                video.getImage()
        );
    }

    public void deleteVideos (int idanime, int episode) {
        jdbcTemplate.update(DELETE_VIDEO, idanime, episode);
    }
}
