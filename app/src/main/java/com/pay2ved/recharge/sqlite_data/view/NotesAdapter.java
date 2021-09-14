package com.pay2ved.recharge.sqlite_data.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.sqlite_data.model.Note;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context context;
    private List<Note> notesList;
    Integer selected_position = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public CheckBox checkbox;
        public RelativeLayout rlt_header;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            checkbox = view.findViewById(R.id.checkbox);
            rlt_header = view.findViewById(R.id.rlt_header);
        }
    }


    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Note note = notesList.get(position);

        holder.note.setText(note.getNote());

        if (position == selected_position) {
            holder.checkbox.setChecked(true);
        } else holder.checkbox.setChecked(false);

        holder.checkbox.setOnClickListener(onStateChangedListener(holder.checkbox, position));

    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selected_position = position;

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.Position, String.valueOf(position));
                    editor.commit();

                    // Toast.makeText(context, " "+String.valueOf(position), Toast.LENGTH_SHORT).show();
                } else {
                    selected_position = -1;

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.Position, "");
                    editor.commit();

                }
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
