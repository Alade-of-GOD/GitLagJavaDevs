package com.studio.pogafresh.gitlagjavadevs;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by PoG~Afresh on 8/28/2017.
 */
public class NetworkAccess {
    //url to fetch data from
    final static String GITHUB_URL = "https://api.github.com/search/users?q=language:java+location:lagos";

    final static String PARAM_QUERY = "q";

    final static String PARAM_SORT = "sort";

    final static String sortBy = "stars";

    public static URL buildGitUrl(String jointQuery) {
        Uri builtUri = Uri.parse(GITHUB_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, jointQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                        //.appendQueryParameter(BuildConfig.TOKEN_API_KEY, null)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL githubUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) githubUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
