package Project.SpringBoot.JournalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data    //@Data isse sare lombok wale methods aa jayege
@Builder
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)   // application properties file mai configure karna padega automatically spring nhi karega
    @NonNull
    private String userName;
    @NonNull
    private String password;

    @DBRef // foreign key to JournalEntry
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;



}
