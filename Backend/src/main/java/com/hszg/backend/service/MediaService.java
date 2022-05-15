package com.hszg.backend.service;

import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.api.error.ResourceNotFoundException;
import com.hszg.backend.data.model.Media;
import com.hszg.backend.repos.MediaRepository;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.service.edit.MediaPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final ObjectRepository objectRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository, ObjectRepository objectRepository) {
        this.mediaRepository = mediaRepository;
        this.objectRepository = objectRepository;
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(Long id) {

        var media = mediaRepository.findById(id);
        if (media.isEmpty()) {
            throw new ResourceNotFoundException("Media with id" + id + " does not exist");
        }

        return media.get();

    }

    @Transactional
    public Media createMedia(Long objectId, Media media) {

        var object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ObjectNotFoundException("Object with id " + object + " does not exist");
        }

        media.setObject(object.get());
        return mediaRepository.save(media);

    }

    public List<Media> getAllMediaByObjectId(Long objectId) {

        var object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ObjectNotFoundException("Object with id " + object + " does not exist");
        }

        return mediaRepository.findMediaByObjectId(objectId);

    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }

    @Transactional
    public Media editMedia(Long id, MediaPropertiesEdit changes) {
        var media = getMediaById(id);
        changes.applyChanges(media);
        return mediaRepository.save(media);
    }

}
