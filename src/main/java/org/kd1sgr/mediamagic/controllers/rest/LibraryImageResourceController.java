package org.kd1sgr.mediamagic.controllers.rest;

import org.kd1sgr.mediamagic.model.CameraImage;
import org.kd1sgr.mediamagic.model.ImageSize;
import org.kd1sgr.mediamagic.model.OrientationVO;
import org.kd1sgr.mediamagic.services.ImageSenderService;
import org.kd1sgr.mediamagic.services.ImageService;
import org.kd1sgr.mediamagic.services.MediaImporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/resource/library/image")
public class LibraryImageResourceController
{
    private final Logger logger = LoggerFactory.getLogger( LibraryImageResourceController.class );

    @Autowired
    ImageSenderService imageSenderService;

    @Autowired
    ImageService imageService;

    @Autowired
    MediaImporterService mediaImporterService;

    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public void doGet(@PathVariable("imageId") String imageId,
                      @RequestParam( value = "size", required = false ) String sizeOrNull,
                      @RequestParam( value = "orientation", required = false) String orientationOrNull,
                      HttpServletResponse response ) throws IOException
    {
        final ImageSize imageSize = ( sizeOrNull == null ) || ImageSize.isKnownValue( sizeOrNull ) ?
                        ImageSize.SIZE_DEFAULT : ImageSize.instanceOf( sizeOrNull );

        final OrientationVO orientationVO = ( orientationOrNull == null ) ||
                            OrientationVO.isKnownValue( orientationOrNull ) ?
                            OrientationVO.ROTATE_NONE : OrientationVO.valueOf( orientationOrNull );


        logger.info( "doGet called with path imageId= '" + imageId + "', size='" + imageSize + "', orientation='" + orientationVO + "'" );

        Optional<CameraImage> opt = imageService.getCameraImage( imageId );
        if ( opt.isPresent() )
        {
            imageSenderService.sendRawImageDataToOutputStream( opt.get(), response.getOutputStream());
        }
    }

}
