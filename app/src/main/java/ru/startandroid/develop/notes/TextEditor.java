package ru.startandroid.develop.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.Arrays;
import java.util.HashSet;

public class TextEditor extends AppCompatActivity {

    EditText editNote;
    int notesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        editNote = (EditText) findViewById(R.id.editNote);

        Intent intent = getIntent();
        notesId = intent.getIntExtra("notesId", -1);

        if(notesId == -1) {

            MainActivity.notes.add("");
            notesId = MainActivity.notes.size() - 1;
            MainActivity.adapterNotes.notifyDataSetChanged();

        } else {

            editNote.setText(MainActivity.notes.get(notesId));

        }

        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(notesId, s.toString());
                MainActivity.adapterNotes.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ru.startandroid.develop.notes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}