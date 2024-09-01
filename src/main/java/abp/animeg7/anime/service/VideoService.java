package abp.animeg7.anime.service;

import abp.animeg7.anime.dao.HandlerSax;
import abp.animeg7.anime.dao.VideoRepository;
import abp.animeg7.anime.model.Video;
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
public class VideoService {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    public VideoService (VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public ResponseEntity getAllVideos() {
        List<Video> videos = videoRepository.getVideos();
        if(videos.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(videos);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: no hay videos a mostrar");
    }
    public ResponseEntity getVideosByAnime(int idAnime) {

        List<Video> videos = videoRepository.getVideosByAnime(idAnime);

        if(videos.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(videos);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: no hay videos a mostrar");
    }

    public ResponseEntity addVideos(Video video) {

        if (video != null) {
            videoRepository.addVideos(video);
            return ResponseEntity.status(HttpStatus.CREATED).body("Video agregado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Video no válido");
        }
    }

    public ResponseEntity updateVideo(int idanime, int episode, Video video) {
            videoRepository.updateVideos(idanime, episode, video);
            return ResponseEntity.status(HttpStatus.OK).body("Video actualizado correctamente");
    }

    public ResponseEntity deleteVideo(int idanime, int episode) {
            videoRepository.deleteVideos(idanime, episode);
            return ResponseEntity.status(HttpStatus.OK).body("Video eliminado correctamente");
    }

    public void dataLoad() {
        try {
            File file = new File("Files/Anime.xml");
            List<Video> videos = parseAnimeXML(file);
            for (Video video : videos) {
                videoRepository.addVideos(video);
                System.out.println(video);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static List<Video> parseAnimeXML(File file) throws ParserConfigurationException, SAXException, IOException {
        HandlerSax handler = new HandlerSax();
        return handler.getVideos(file.getPath());
    }

}
