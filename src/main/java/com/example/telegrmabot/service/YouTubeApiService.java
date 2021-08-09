package com.example.telegrmabot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.telegrmabot.model.YouTubeItem;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class YouTubeApiService {
    @Value("${youtube.fields}")
    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)";
    @Value("${youtube.apikey}")
    private String YOUTUBE_APIKEY_ENV;
    @Value("${youtube.maxvid}")
    private Long NUMBER_OF_VIDEOS_RETURNED;

    private static final String GOOGLE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_SEARCH_TYPE = "video";
    private static final String YOUTUBE_API_APPLICATION = "google-youtube-api-search";

    private static YouTube youtube;

    static {
        // This object is used to make YouTube Data API requests. The last
        // argument is required, but since we don't need anything
        // initialized when the HttpRequest is initialized, we override
        // the interface and provide a no-op function.
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                        // intentionally left empty
                    }
                }).setApplicationName(YOUTUBE_API_APPLICATION).build();
    }


    /**
     * Makes YouTube search into video library with given keywords.
     */
    public List<YouTubeItem> youTubeSearch(String searchQuery, long maxSearch) {
        List<YouTubeItem> rvalue = new ArrayList<>();
        try {
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list(List.of("id", "snippet"));
            search.setKey(YOUTUBE_APIKEY_ENV);
            search.setQ(searchQuery);
            search.setType(List.of(YOUTUBE_SEARCH_TYPE));
            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields(YOUTUBE_SEARCH_FIELDS);
            if (maxSearch < 1) {
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            } else {
                search.setMaxResults(maxSearch);
            }
            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null && searchResultList.size() > 0) {
                for (SearchResult r : searchResultList) {
                    YouTubeItem item = new YouTubeItem(
                            GOOGLE_YOUTUBE_URL + r.getId().getVideoId(),
                            r.getSnippet().getTitle(),
                            r.getSnippet().getThumbnails().getDefault().getUrl(),
                            r.getSnippet().getDescription());
                    rvalue.add(item);
                    System.out.println(item);
                }
            } else {
                System.out.println("No search results got from YouTube API");
            }
        } catch (
                GoogleJsonResponseException e) {
            System.out.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (
                IOException e) {
            System.out.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (
                Throwable t) {
            System.out.println("Severe errors!");
            t.printStackTrace();
        }
        return rvalue;
    }
}
