package hive.app.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.storage.Storage.Channels;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import hive.app.utils.DateTime;

@Service  
public class FirebaseInitialize {  
	
	private StorageOptions storageOptions;
	private static DateTime date;
	String bucketName = "https://console.firebase.google.com/project/hivephotodb/storage/hivephotodb.appspot.com/files";
	
	@PostConstruct  
	public void initialize() {  
		try { 
			FileInputStream serviceAccount = new FileInputStream("C:\\Users\\julie\\Documents\\workspace-spring-tool-suite-4-4.7.1.RELEASE\\tc_3\\Backend\\hive\\src\\main\\java\\hive\\app\\utils\\serviceAccount.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setDatabaseUrl("https://hivephotodb.firebaseio.com")
					  .build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
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
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.close();
		return convertedFile;
	}
	
	private String generateFileName(MultipartFile multiPart) {
        return date.GetCurrentDateTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
	
}  