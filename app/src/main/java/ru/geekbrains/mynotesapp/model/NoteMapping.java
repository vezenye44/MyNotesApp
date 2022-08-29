package ru.geekbrains.mynotesapp.model;
import com.google.firebase.Timestamp;
import com.google.type.DateTime;
import ru.geekbrains.mynotesapp.model.Note;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class NoteMapping {
    public static class Fields{
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String TEXT = "text";
    }
    public static Note toNote(String id, Map<String, Object> doc) {
        Timestamp timestamp = (Timestamp) doc.get(Fields.DATE);
        assert timestamp != null;
        Date date = timestamp.toDate();
        Note answer = new Note((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.TEXT),
                date);
        answer.setId(id);
        return answer;
    }
    public static Map<String, Object> toDocument(Note note){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, note.getTitleOfNote());
        answer.put(Fields.TEXT, note.getTextOfNote());
        answer.put(Fields.DATE, note.getDate());
        return answer;
    }
}
