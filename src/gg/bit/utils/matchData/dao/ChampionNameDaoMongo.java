package gg.bit.utils.matchData.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import gg.bit.utils.matchData.vo.ChallengerVo;

public class ChampionNameDaoMongo {
	private String ip = "127.0.0.1";
//	private String port = "27017";
	private String database = "lol";
	private String collection = "champion_names";
	
	private MongoClient connection() {
		MongoClient mongoClient = 		
				MongoClients.create(
						MongoClientSettings.builder()
						.applyToClusterSettings(builder ->
						builder.hosts(Arrays.asList(new ServerAddress(ip)))).build());
		
		return mongoClient;
	}
	
	private MongoCollection<Document> getCollection(MongoClient mongoClient) 
			throws MongoClientException {
			
			MongoDatabase database = mongoClient.getDatabase(this.database);
			MongoCollection<Document> collection = database.getCollection(this.collection); 
					
			return collection;
		}
	
	public Map<String, Float> checkNameById(Map<Integer, Float> map) {
		
		// 몽고 디비 접속 클라이언트
		MongoClient mongoClient = null;
		// 클라이언트로 접속 후 collection 가져오기
		MongoCollection<Document> coll = null;
		
		// 변경될 데이터 가질 map 생성
		Map<String, Float> winRateMap = new HashMap<>();
		
		try {
			// 몽고 접속
			mongoClient = connection();
			coll = getCollection(mongoClient);
			
			// map 반복
			Set<Integer> keySet = map.keySet();
			Iterator<Integer> keyIterator = keySet.iterator();
			while (keyIterator.hasNext()) {
				Integer key = keyIterator.next();
				
				String name = 
						(String) coll.find(eq("key", key))
							.first().get("name");
				
				winRateMap.put(name, map.get(key));
			}
			
			
		} catch (MongoClientException e) {
			e.printStackTrace();
		} finally {
			try {
				mongoClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return winRateMap;
	}
}

