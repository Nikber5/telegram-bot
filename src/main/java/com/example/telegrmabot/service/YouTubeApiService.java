package com.example.telegrmabot.service;

import java.util.ArrayList;
import java.util.List;
import com.example.telegrmabot.model.YouTubeItem;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class YouTubeApiService {
    private static final String GOOGLE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_SEARCH_TYPE = "video";

    @Value("${youtube.fields}")
    private String YOUTUBE_SEARCH_FIELDS;
    @Value("${youtube.apikey}")
    private String YOUTUBE_APIKEY_ENV;
    @Value("${youtube.maxvid}")
    private Long NUMBER_OF_VIDEOS_RETURNED;

    private final Logger logger = LoggerFactory.getLogger(YouTubeApiService.class);
    private final YouTube youtube;

    public YouTubeApiService(YouTube youtube) {
        this.youtube = youtube;
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
                    logger.info(item.toString());
                }
            } else {
                logger.info("No search results got from YouTube API by request: " + searchQuery);
            }
            return rvalue;
        } catch (Exception e) {
            throw new RuntimeException("Can't find videos by request: " + searchQuery, e);
        }
    }
}
