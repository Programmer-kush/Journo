package Project.SpringBoot.JournalApp.entity;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "Journal_entries")
@Data        //@Data isse sare lombok wale methods aa jayege
public class JournalEntry {
    @Id   //primary key
    private ObjectId id; // datatype for object id in mongodb
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;



}
