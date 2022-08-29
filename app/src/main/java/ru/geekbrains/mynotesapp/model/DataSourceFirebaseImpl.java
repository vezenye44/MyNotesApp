package ru.geekbrains.mynotesapp.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSourceFirebaseImpl implements DataSource {

    private static final String NOTES_COLLECTION = "Notes";
    private static final String TAG = "[DataSourceFirebaseImpl]";

    private final FirebaseFirestore store = FirebaseFirestore.getInstance();
    private final CollectionReference collection = store.collection(NOTES_COLLECTION);
    private List<Note> notes = new ArrayList<Note>();

    @Override
    public DataSource init(CardsSourceResponse cardsSourceResponse) {
        collection.orderBy(NoteMapping.Fields.DATE,
                        Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notes = new ArrayList<Note>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteMapping.toNote(id, doc);
                                notes.add(note);
                            }
                            Log.d(TAG, "success " + notes.size() + " qnt");
                            cardsSourceResponse.initialized(DataSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public boolean isEmpty() {
        return notes.isEmpty();
    }

    @Override
    public int indexOf(Note note) {
        return notes.indexOf(note);
    }

    @Override
    public void addData(Note note) {
        collection.add(NoteMapping.toDocument(note)).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        note.setId(documentReference.getId());
                    }
                });

    }

    @Override
    public void updateData(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));

    }

    @Override
    public Note getData(int position) {
        return notes.get(position);
    }

    @Override
    public int getDataSize() {
        if (notes != null)
            return notes.size();
        return 0;
    }

    @Override
    public void deleteData(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }
}
