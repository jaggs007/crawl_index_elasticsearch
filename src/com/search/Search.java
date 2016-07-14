package com.search;

import java.io.IOException;
import java.util.Scanner;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class Search {
	private static String data;

	public static void searchDocument(Client client) throws IOException,
			InterruptedException {

		Scanner reader = new Scanner(System.in);
		System.out.println("Enter string to search");
		
		data=reader.next();

		SearchResponse response =client.prepareSearch("curl")
				.setTypes("new").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("motto",data)).execute()
				.actionGet();

// Printing the Search Results
SearchHit[] searchResult = response.getHits().hits();

for (SearchHit sh : searchResult) {
	System.out.println(sh.getSource().toString());

}
}

}
