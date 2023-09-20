package com.firebase.config;



import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Configuration
public class FirebaseConfiguration {

	@Configuration
	public class FirebaseConfig {

		@PostConstruct
		public void init() {
			try {
				FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-adminsdk.json");

				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.getApplicationDefault())
						.setServiceAccountId("serviceAccount")
						.build();

				FirebaseApp.initializeApp(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
