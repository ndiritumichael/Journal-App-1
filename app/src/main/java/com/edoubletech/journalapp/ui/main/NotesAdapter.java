/*
 * Copyright (C) 2018 Eton Otieno Oboch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edoubletech.journalapp.ui.main;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edoubletech.journalapp.R;
import com.edoubletech.journalapp.data.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NotesViewHolder> {

    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }
    };

    public NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note currentNote = getItem(position);

        String title = currentNote.getTitle();
        holder.titleTextView.setText(title);
        holder.nameTextView.setText(title.charAt(0));

        Date date = currentNote.getDate();
        GradientDrawable drawable = (GradientDrawable) holder.nameTextView.getBackground();
        int dayOfTheWeek = date.getDay();
        int backgroundColor = getBackgroundColor(dayOfTheWeek, holder.itemView.getContext());
        drawable.setColor(backgroundColor);

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        holder.dateTextView.setText(formattedDate);


        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String formattedTime = timeFormat.format(date);
        holder.timeTextView.setText(formattedTime);
        holder.itemView.setTag(currentNote);
    }

    private int getBackgroundColor(int dayOfWeek, Context context) {
        int backgroundColor;
        switch (dayOfWeek) {
            case 0:
                backgroundColor = R.color.sunday;
                break;
            case 1:
                backgroundColor = R.color.monday;
                break;
            case 2:
                backgroundColor = R.color.tuesday;
                break;
            case 3:
                backgroundColor = R.color.wednesday;
                break;
            case 4:
                backgroundColor = R.color.thursday;
                break;
            case 5:
                backgroundColor = R.color.friday;
                break;
            case 6:
                backgroundColor = R.color.saturday;
                break;
            default:
                backgroundColor = R.color.colorAccent;
                break;
        }
        return ContextCompat.getColor(context, backgroundColor);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dateTextView, titleTextView, timeTextView, nameTextView;

        NotesViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            titleTextView = itemView.findViewById(R.id.noteTitleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            nameTextView = itemView.findViewById(R.id.nameCircleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Note note = (Note) itemView.getTag();
            Bundle bundle = new Bundle();
            bundle.putInt("NOTES_ID", note.getId());
            Navigation.findNavController(itemView).navigate(R.id.mainToAddAction, bundle);
        }
    }
}
