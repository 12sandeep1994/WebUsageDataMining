package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.constants.WebUsageConstantsIF;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.model.GoogleResult;

public class GoogleHelper { 

	
	public static final List<GoogleResult> getGoogleResults(String query) {

		List<GoogleResult> googleResults = new ArrayList<GoogleResult>();

		List<Result> resultList = getSearchResult(query);

		if (resultList != null && resultList.size() > 0) {
			for (Result result : resultList) {

				GoogleResult googleResult = new GoogleResult();

				String snippetFromGoogle = result.getSnippet();

				String url = result.getDisplayLink();

				String hiddenUrl = result.getLink();

				String title = result.getTitle();

				googleResult.setDesc(snippetFromGoogle);
				googleResult.setHiddenUrl(hiddenUrl);
				googleResult.setUrl(url);
				googleResult.setTitle(title);

				googleResults.add(googleResult);

			}

		}

		return googleResults;

	}

	public static List<Result> getSearchResult(String keyword) {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		Customsearch customsearch = new Customsearch(httpTransport,
				jsonFactory, null);

		List<Result> resultList = null;
		try {
			Customsearch.Cse.List list = customsearch.cse().list(keyword);
			list.setKey(WebUsageConstantsIF.GoogleKeys.API_KEY);
			list.setCx(WebUsageConstantsIF.GoogleKeys.SEARCH_ENGINE_ID);
			list.setNum(10L);
			list.setStart(1L);
			Search results = list.execute();
			resultList = results.getItems();

		} catch (Exception e) {

			System.out.println("Exception is  = " + e.getMessage());
			e.printStackTrace();

		}

		return resultList;

	}

}
