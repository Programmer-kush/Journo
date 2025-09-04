package Project.SpringBoot.JournalApp.controller;

import Project.SpringBoot.JournalApp.entity.JournalEntry;
import Project.SpringBoot.JournalApp.entity.User;
import Project.SpringBoot.JournalApp.service.JournalEntryService;
import Project.SpringBoot.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal") //ab iss class ke endpoint isse se shuru hoge
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUsername(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try{
            journalEntryService.saveEntry(myEntry , userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUsername(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId))   // FIX: compare entry id to myId
                .collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional <JournalEntry> entry = journalEntryService.findById(myId);

            if(entry.isPresent()){
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed=journalEntryService.deleteById(myId, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updatedJournalEntryById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUsername(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId))   // FIX: compare entry id to myId
                .collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional <JournalEntry> journalentry = journalEntryService.findById(myId);

            if(journalentry.isPresent()){
                JournalEntry old=journalentry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());

                journalEntryService.saveEntry(old);
                return new  ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
