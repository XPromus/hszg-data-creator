package com.hszg.backend.api;

import com.hszg.backend.data.model.Media;
import com.hszg.backend.service.MediaService;
import com.hszg.backend.service.edit.MediaPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/get/all")
    public List<Media> getAllMedia() {
        return mediaService.getAllMedia();
    }

    @GetMapping("/get/id/{id}")
    public Media getMediaById(@PathVariable Long id) {
        return mediaService.getMediaById(id);
    }

    @PostMapping("/create/{objectId}")
    public Media createMedia(@PathVariable Long objectId, @RequestBody Media media) {
        return mediaService.createMedia(objectId, media);
    }

    @PostMapping("/edit/{id}")
    public Media editMedia(@PathVariable Long id, @RequestBody MediaPropertiesEdit changes) {
        return mediaService.editMedia(id, changes);
    }

    @GetMapping("/get/object/{objectId}")
    public List<Media> getAllMediaByObjectId(@PathVariable Long objectId) {
        return mediaService.getAllMediaByObjectId(objectId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
    }

}
