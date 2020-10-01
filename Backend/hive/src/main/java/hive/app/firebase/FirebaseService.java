package hive.app.firebase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import hive.app.utils.DateTime;

@Service
public class FirebaseService {
	
	private StorageOptions storageOptions;
	String bucketName = "https://console.firebase.google.com/project/hivephotodb/storage/hivephotodb.appspot.com/files";
	
	public String[] uploadFile(File file) throws IOException {
//		File file = convertMultiPartToFile(multipartFile);
		Path filePath = file.toPath();
//		String objectName = generateFileName(multipartFile);
		String objectName = "test";
	
		Storage storage = storageOptions.getService();
	
		BlobId id = BlobId.of(bucketName, objectName);
		BlobInfo info = BlobInfo.newBuilder(id).build();
		Blob blob = storage.create(info, Files.readAllBytes(filePath));
	
		System.out.print("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
		return new String[]{"fileUrl", objectName};
	}
}
