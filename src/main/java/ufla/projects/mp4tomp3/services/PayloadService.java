package ufla.projects.mp4tomp3.services;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.ObjectWriteResponse;
import io.minio.UploadObjectArgs;
import ufla.projects.mp4tomp3.exceptions.StorageException;
import ufla.projects.mp4tomp3.domain.Payload;
import ufla.projects.mp4tomp3.dto.PayloadDTO;
import ufla.projects.mp4tomp3.storage.StorageClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;

@ApplicationScoped
public class PayloadService {
    @Inject
    StorageClient storageClient;

    @Inject
    Logger logger;

    @ConfigProperty(name = "bucket.name")
    private String bucketName;

    private void makeBucket() throws Exception {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs
                .builder()
                .bucket(bucketName)
                .build();

        if (storageClient.getMinioClient().bucketExists(bucketExistsArgs)) {
            logger.info("Bucket " + bucketName + " already exist");
        }
        else {
            logger.info("Bucket " + bucketName + " not exist");
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            storageClient.getMinioClient().makeBucket(makeBucketArgs);
            logger.info("Bucket " + bucketName + " created");
        }
    }
    public PayloadDTO sendObjectToStorage(Payload payload, File file) throws Exception {

        ObjectWriteResponse response = null;
        this.makeBucket();
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(payload.fileName)
                    .filename(file.getAbsolutePath())
                    .contentType("application/pdf")
                    .build();
            response = storageClient.getMinioClient().uploadObject(uploadObjectArgs);
            System.out.println(response.versionId());
        } catch (Exception e) {
            throw new StorageException("Error upload file to storage. Please try again -- " + e.getMessage());
        }

        return PayloadDTO.builder()
                .bucketName(response.bucket())
                .fileNAme(payload.fileName)
                .build();
    }
}
