package webcrawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.search.Search;

public class WebCrawler {
	public static void main(String[] args) throws IOException, InterruptedException {
		Document doc=null;
		String part1=null;
		String part2=null;
		String motto = "";
		try{
		doc = (Document) Jsoup.connect("http://en.wikipedia.org/wiki/United_States").get();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

        Element table = doc.select("TABLE[class = infobox geography vcard").first();
        Elements rows = table.select("tr");

        for (Element td: rows) {
        	
            boolean validate = td.toString().contains("Motto");
            if(validate){
            	System.out.println(td.text());
            	motto=td.text();
                String[] parts=motto.split("1");
            	part1=parts[0];
            	part2= part1.replaceAll("[^\\w\\s]","");
            	//System.out.println(part1);
            	System.out.println(part2);
            }
        }
		/*Elements body=doc.select("div.hatnote");
		for(Element link: body)
			System.out.println(link);*/

			/*Element content = doc.getElementById("content");
			Elements links = content.getElementsByTag("table");
			for (Element link : links) {
			  String linkHref = link.attr("");
			  String linkText = link.text();
			  System.out.println(linkText);
		}*/        
                
        Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build();
		TransportClient tc = new TransportClient(settings);
        Client client = tc.addTransportAddress(new InetSocketTransportAddress(
				"localhost", 9300));        
        
     //  IndexResponse resoo1 = client.prepareIndex("curl", "new")
       // 		.setSource(putJsonDocument(part2)).execute().actionGet();
        
        Search.searchDocument(client);
	}
		public static Map<String, Object> putJsonDocument(String part2) {
		Map<String, Object> jsonDocument = new HashMap<String, Object>();		
		jsonDocument.put("motto",part2);		
		return jsonDocument;
	}
}
