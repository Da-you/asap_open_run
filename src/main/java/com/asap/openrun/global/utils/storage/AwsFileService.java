package com.asap.openrun.global.utils.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class AwsFileService implements FileService {

  private final AmazonS3Client amazonS3Client;
  @Value("${cloud.aws.s3.bucket}")
  private String originBucket;
  @Value("${cloud.aws.s3.resize-bucket}")
  private String resizedBucket;


  public String uploadOrigin(MultipartFile file) {
    return upload(file, originBucket);
  }

  public String uploadResized(MultipartFile file) {
    return upload(file, resizedBucket);
  }


  @Override
  public String upload(MultipartFile file, String bucket) {
    String fileName = file.getOriginalFilename();
    String convertName = FileNameUtils.fileNameConvert(fileName);
    try {
      String mimeType = new Tika().detect(file.getInputStream());
      ObjectMetadata metadata = new ObjectMetadata();

      FileNameUtils.checkImageMimeType(mimeType);
      metadata.setContentType(mimeType);
      amazonS3Client.putObject(
          new PutObjectRequest(bucket, convertName, file.getInputStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return amazonS3Client.getUrl(bucket, convertName).toString();
  }

  @Override
  public void delete(String destLocation, String key) {
    amazonS3Client.deleteObject(destLocation, key);

  }

  public void deleteOrigin(String key) {
    delete(originBucket, key);
  }

  public void deleteResized(String key) {
    delete(resizedBucket, key);
  }

}
