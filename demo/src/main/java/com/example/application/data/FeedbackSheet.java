package com.example.application.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.oauth2.ServiceAccountCredentials;

/* Class to store feedack into a Google Sheet.
 *
 * This needs a Google Service Account to be set up first and give 'editor'
 * permissions to that account in the specified sheet.
 *
 *
 */
public class FeedbackSheet {

    private static final String APPLICATION_NAME = "NPS Feedback App";

    private static final List<String> AUTH_SCOPES = List.of(SheetsScopes.SPREADSHEETS);

    private static final String RANGE = "Sheet1";

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private String credentialsFilePath;
    private ServiceAccountCredentials loadedCredentials;
    private Sheets loadedService;

    private String sheetId;

    private FeedbackSheet(String sheetId, String credentialsFilePath) {
        this.sheetId = sheetId;
        this.credentialsFilePath = credentialsFilePath;
    }

    public static FeedbackSheet getSheet(String sheetId, String credentialsFilePath) {
        return new FeedbackSheet(sheetId, credentialsFilePath);
    }

    /**
     * Append a user score to the spreadsheet.
     * <p>
     * Adds a new row in the first sheet in a Google Spreadsheet
     * with timestamp userId and given NPS score.
     *
     * <code>2023-06-26 11:09:44,1687640813,	10</code>
     *
     * @param anonymousUserId Unique but anonymous I
     * @param score           NPS score
     */
    public void append(String product, String anonymousUserId, int score) {
        executor.submit(() -> {
            try {
                List<List<Object>> values = Arrays.asList(
                        Arrays.asList(LocalDateTime.now().toString(), product, anonymousUserId, score));

                ValueRange newEntry = new ValueRange().setValues(values);
                Sheets service = getSheetsService();
                service.spreadsheets().values()
                        .append(this.sheetId, RANGE, newEntry)
                        .setValueInputOption("USER_ENTERED")
                        .setAccessToken(getCredentials().getAccessToken().getTokenValue())
                        .execute();
            } catch (Exception e) {
                throw new RuntimeException("Failed to append Google Sheet", e);
            }
        });
    }

    /**
     * Initialize and get Google Sheets service.
     *
     * @return Sheets service to perform operations.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
        if (this.loadedService == null) {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            this.loadedService = new Sheets.Builder(httpTransport, JSON_FACTORY, request -> {
            })
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
        return this.loadedService;
    }

    /**
     * Load and create new ServiceAccountCredential instance.
     * <p>
     * Once successfully loaded, calling this will return the same credential
     * instance.
     *
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    protected ServiceAccountCredentials getCredentials() throws IOException {

        if (this.loadedCredentials != null) {
            if (this.loadedCredentials.getAccessToken().getExpirationTime().before(new Date())) {
                this.loadedCredentials.refresh();
            }
            return this.loadedCredentials;
        }

        try (InputStream in = new FileInputStream(credentialsFilePath)) {
            this.loadedCredentials = (ServiceAccountCredentials) ServiceAccountCredentials
                    .fromStream(new FileInputStream(credentialsFilePath))
                    .createScoped(AUTH_SCOPES);
            this.loadedCredentials.refresh();
            return this.loadedCredentials;
        }

    }

}
