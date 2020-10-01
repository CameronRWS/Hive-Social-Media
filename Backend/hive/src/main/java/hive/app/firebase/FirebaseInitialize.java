package hive.app.firebase;

import java.io.FileInputStream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

@Service  
public class FirebaseInitialize {  
	
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
}  