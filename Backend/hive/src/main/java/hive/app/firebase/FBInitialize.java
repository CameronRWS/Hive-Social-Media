package hive.app.firebase;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@Service  
public class FBInitialize {  

	@PostConstruct  
	public void initialize() {  
		try {  
			FileInputStream serviceAccount =  new FileInputStream("./utils/serviceAccount.json");  
			FirebaseOptions options = new FirebaseOptions.Builder()  
//					.setCredentials(GoogleCredentials.fromStream(serviceAccount))  
					.setDatabaseUrl("https://chatapp-e6e15.firebaseio.com")  
					.build();  
			FirebaseApp.initializeApp(options);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
}  