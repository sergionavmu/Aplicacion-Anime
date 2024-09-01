package abp.animeg7.anime.api;


import abp.animeg7.anime.model.Video;
import abp.animeg7.anime.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/dataloads")
    public ResponseEntity dataLoad() {
        videoService.dataLoad();
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity getVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{idAnime}")
    public ResponseEntity getVideoByAnime (@PathVariable int idAnime) {
        return videoService.getVideosByAnime(idAnime);
    }

    @PostMapping("/add")
    public ResponseEntity addVideo(@RequestBody Video video) {
        return videoService.addVideos(video);
    }

    @PutMapping("/anime/{idAnime}/episode/{episode}")
    public ResponseEntity updateVideo(@PathVariable int idAnime, @PathVariable int episode, @RequestBody Video video) {
        return videoService.updateVideo(idAnime, episode, video);
    }
    @DeleteMapping("/anime/{idanime}/episodio/{episode}")
    public ResponseEntity deleteVideo(@PathVariable int idanime, @PathVariable int episode) {
        return videoService.deleteVideo(idanime, episode);
    }

}