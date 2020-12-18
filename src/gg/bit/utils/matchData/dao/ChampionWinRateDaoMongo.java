package gg.bit.utils.matchData.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;

import gg.bit.utils.matchData.vo.ChampionWinOrLoseVo;

public class ChampionWinRateDaoMongo implements ChampionWinRateDao {
	
	private String ip = "127.0.0.1";
//	private String port = "27017";
	private String database = "lol";
	
	private MongoClient connection() {
		MongoClient mongoClient = 		
				MongoClients.create(
						MongoClientSettings.builder()
						.applyToClusterSettings
						(builder ->
						builder.hosts(Arrays.asList(new ServerAddress(ip)))).build());
		
		return mongoClient;
	}
	
	private MongoCollection<Document> getCollection(MongoClient mongoClient, String collectionName) 
		throws MongoClientException {
		
		MongoDatabase database = mongoClient.getDatabase(this.database);
		MongoCollection<Document> collection = database.getCollection(collectionName); 
				
		return collection;
	}
	
	public List<ChampionWinOrLoseVo> getList() {
		// vo 객체 담을 list 생성
		List<ChampionWinOrLoseVo> list = new ArrayList<>();
		
		// 몽고 디비 접속 클라이언트
		MongoClient mongoClient = null;
		// 클라이언트로 접속 후 collection 가져오기
		// match_data 
		MongoCollection<Document> matchCollection = null;
		// id 매치시킬 name 컬렉션
		MongoCollection<Document> championNameCollection = null;
		
		try {
			mongoClient = connection();
			matchCollection = getCollection(mongoClient, "match_data");
			championNameCollection = getCollection(mongoClient, "champion_names");
			
			// 컬렉션에서 find 한 결과
			FindIterable<Document> matchIterator = matchCollection.find();
			
			int num = 0;
			for (Document doc: matchIterator) {
				// vo 객체 생성

				List<Document> participantsList = doc.getList("participants", Document.class);
				
				for (Document doc1 : participantsList) {
					
					ChampionWinOrLoseVo vo = new ChampionWinOrLoseVo();
					
					Integer championId = doc1.getInteger("championId");
					vo.setChampionId(championId);
					
//					String name = (String) championNameCollection.find(eq("key", championId)).first().get("name");
//					vo.setChampionName(name);
					
					System.out.println((double) doc.get("gameId"));
					System.out.println((int) doc1.get("participantId"));
					if (doc1.get("stats") != null) {
						String winOrLose = (String) (doc1.get("stats", Document.class).get("win"));
						vo.setWinOrLose(winOrLose);
						
					} else {
						vo.setWinOrLose("False");
					}
					
					// 리스트에 등록
					list.add(vo);
				}
				num++;
				if (num == 1000) break;
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
		
		return list;
	}
}
