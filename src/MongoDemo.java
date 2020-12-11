import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.bson.Document;
import com.mongodb.DB;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDemo {
	// 접속
	// 인자는 db, collections 순서
	public static void main(String[] args) {
		MongoConnection("192.168.56.101", "lol", "challenger");
	}
	
	private static void MongoConnection(String ip, String mydb, String mycol) {
		// 지정 IP mongo db 접속
		// 인스턴스 생성
		
		// 가상 서버 접속 클라이언트
		MongoClient mongoClient = MongoClients.create(
				MongoClientSettings.builder()
				.applyToClusterSettings(builder ->
				builder.hosts(Arrays.asList(new ServerAddress(ip)))).build());
		
		// 로컬 서버 접속 클라이언트
//		MongoClient mongoClient = MongoClients.create();
		try {
			// 지정 db 접속
			// db 이름이 없을 경우 새롭게 생성하게 됨
			MongoDatabase database = mongoClient.getDatabase(mydb);
			// collection 접근
			// collention 이 없을 경우 새롭게 생성하게 됨
			MongoCollection<Document> collection = database.getCollection(mycol);
			System.out.println("연결 성공");
			
			FindIterable<Document> it =collection.find();
			
			for (Document doc : it) {
				System.out.println(doc.get("role"));
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
	}
}