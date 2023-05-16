package edu.lehigh.cse216.alb323.backend;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class DriveAPI {
    private static final String APPLICATION_NAME = "Backend";
    private static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    private Drive drive;

    public DriveAPI() {
        setupDrive();
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void setupDrive() {  
        try {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        } catch(GeneralSecurityException ie) {
            System.err.println(ie.getMessage());
        } catch(IOException ie) {
            System.err.println(ie.getMessage());
        }

    }

    public String uploadToDrive() {
        return null;
    }

    public String DownloadFile(String fileId, String fileType) {
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            drive.files().export(fileId, fileType)
            .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toString();
    }

    public Drive getDrive() {
        return drive;
    }
}