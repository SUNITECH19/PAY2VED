package com.pay2ved.recharge.sms_home_activity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.sqlite_data.model.DatabaseHelper;
import com.pay2ved.recharge.sqlite_data.model.Note;
import com.pay2ved.recharge.sqlite_data.utils.MyDividerItemDecoration;
import com.pay2ved.recharge.sqlite_data.utils.RecyclerTouchListener;
import com.pay2ved.recharge.sqlite_data.view.NotesAdapter;

import java.util.ArrayList;
import java.util.List;


public class Gateway_SettingActivity extends AppCompatActivity {

    Button add, update, delete;
    String s_Position = "";
    private NotesAdapter mAdapter;
    List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway__setting);

        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        recyclerView = findViewById(R.id.recycler_view);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteDialog(false, null, -1);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_Position = AppsContants.sharedpreferences.getString(AppsContants.Position, "");

                if (s_Position.equals("")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(Gateway_SettingActivity.this).create();
                    alertDialog.setMessage("Please Select Item.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    int p = Integer.parseInt(s_Position);
                    showNoteDialog(true, notesList.get(p), p);

                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_Position = AppsContants.sharedpreferences.getString(AppsContants.Position, "");

                if (s_Position.equals("")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(Gateway_SettingActivity.this).create();
                    alertDialog.setMessage("Please Select Item.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Gateway_SettingActivity.this);
                    alert.setTitle("Confirm Delete...");
                    alert.setMessage("Are you sure?");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            int p = Integer.parseInt(s_Position);
                            deleteNote(p);
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();


                }
            }
        });


        db = new DatabaseHelper(this);
        notesList.addAll(db.getAllNotes());

        mAdapter = new NotesAdapter(this, notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

              /*  AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Position, String.valueOf(position));
                editor.commit();
*/
               // Toast.makeText(Gateway_SettingActivity.this, "Selected Item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    private void createNote(String note) {
        long id = db.insertNote(note);
        Note n = db.getNote(id);

        if (n != null) {
            notesList.add(0, n);
            mAdapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }
    }

    private void updateNote(String note, int position) {
        Note n = notesList.get(position);
        n.setNote(note);

        db.updateNote(n);
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyNotes();
    }

    private void deleteNote(int position) {
        db.deleteNote(notesList.get(position));

        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Update", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }


    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.activity_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Gateway_SettingActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.edt_mobile);
        TextView dialogTitle = view.findViewById(R.id.text_tittle);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.new_title) : getString(R.string.edit_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Update" : "Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(Gateway_SettingActivity.this, "Please Enter Gateway Number", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && note != null) {
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    createNote(inputNote.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyNotes() {
        if (db.getNotesCount() > 0) {
            // noNotesView.setVisibility(View.GONE);
        } else {
            // noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
