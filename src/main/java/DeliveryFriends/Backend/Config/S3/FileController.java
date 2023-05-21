package DeliveryFriends.Backend.Config.S3;

import DeliveryFriends.Backend.Controller.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final AwsS3Uploader awsS3Uploader;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws BaseException {
        String fileName = awsS3Uploader.upload(multipartFile, "test");
        return fileName;
    }
}