package Project.SpringBoot.JournalApp.service;

import Project.SpringBoot.JournalApp.entity.JournalEntry;
import Project.SpringBoot.JournalApp.entity.User;
import Project.SpringBoot.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository; //dependency injection

    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try{
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());

            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the journal entry.",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
      journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed=false;
        try{
            User user = userService.findByUsername(userName);
             removed=user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("an error occurred while deleting the journal entry.",e);
        }
        return removed;

    }


}
