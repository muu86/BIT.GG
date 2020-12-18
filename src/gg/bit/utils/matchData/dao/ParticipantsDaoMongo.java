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

import gg.bit.utils.matchData.vo.ParticipantsVo;

public class ParticipantsDaoMongo implements ParticipantsDao {
	
	private String ip = "127.0.0.1";
//	private String port = "27017";
	private String database = "lol";
	private String collection = "match_data";
	int a = 0;
	int teamId =0;
	String Blue;
	String Red;
	
	
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
	
	public List<ParticipantsVo> getList() {
		// vo 객체 담을 list 생성
		List<ParticipantsVo> list = new ArrayList<>();
		
		// 몽고 디비 접속 클라이언트
		MongoClient mongoClient = null;
		// 클라이언트로 접속 후 collection 가져오기
		MongoCollection<Document> coll = null;
		
		try {
			mongoClient = connection();
			coll = getCollection(mongoClient);
			
			// 컬렉션에서 find 한 결과
			FindIterable<Document> it = coll.find();
			
			for (Document doc: it) {
				// vo 객체 생성
				for (int i = 0; i < 10; i++) {
					
				
				ParticipantsVo vo = new ParticipantsVo();
				
//				vo.setIndex(doc.getLong(""));
					
				vo.setTeamId(doc.getList("participants",Document.class).get(i).getInteger("teamId"));
				
				
				vo.setChampionId(doc.getList("participants",Document.class).get(i).getInteger("championId"));
			
				
				vo.setKill(doc.getList("participants",Document.class).get(i).get("stats", Document.class).getInteger("kills"));
				
				
				vo.setDeath(doc.getList("participants",Document.class).get(i).get("stats", Document.class).getInteger("deaths"));
				
				
				vo.setAssist(doc.getList("participants",Document.class).get(i).get("stats", Document.class).getInteger("assists"));
						
				
				vo.setDeal(doc.getList("participants",Document.class).get(i).get("stats", Document.class).getInteger("totalDamageDealtToChampions"));
				
				list.add(vo);
				}
				a++;
				if(a==1) break;				
				
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
