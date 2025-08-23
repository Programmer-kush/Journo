package Project.SpringBoot.JournalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}


//    PlatformTransactionManager → Spring’s standard interface for handling transactions.
//
//    MongoTransactionManager → A specific implementation for MongoDB, which allows you to use multi-document transactions.
//
//    MongoDatabaseFactory → Provides a connection to the MongoDB database.

    //This means Spring will use MongoTransactionManager whenever you annotate methods with @Transactional.


    //Normally, MongoDB writes are atomic only at the document level.
    //But since MongoDB 4.0+, you can have ACID transactions across multiple documents and collections — provided you’re using a replica set or sharded cluster.

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
