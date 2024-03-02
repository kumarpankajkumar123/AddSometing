package com.example.notesapp.Adaptor;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.AddNoted;
import com.example.notesapp.R;
import com.example.notesapp.UserData.Notes;
import com.example.notesapp.Util.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class myAdaptor extends FirestoreRecyclerAdapter<Notes,myAdaptor.myViewHolderClass> {
    Context context;


    public myAdaptor(@NonNull FirestoreRecyclerOptions<Notes> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolderClass holder, int position, @NonNull Notes model) {
       holder.title.setText(model.getTitle());
       holder.content.setText(model.getDescription());
       holder.time.setText(Utils.Timestamp(model.getTimestamp()));

       holder.itemView.setOnClickListener(view -> {

           Intent intent= new Intent(context, AddNoted.class);
           intent.putExtra("title",model.getTitle());
           intent.putExtra("des",model.getDescription());
           String docId = this.getSnapshots().getSnapshot(position).getId();
           intent.putExtra("docId",docId);
           context.startActivity(intent);

       });
    }

    @NonNull
    @Override
    public myViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designrecyclerview,parent,false);
        return new myViewHolderClass(view);
    }

    public class myViewHolderClass extends RecyclerView.ViewHolder {
        TextView title,content,time;
        public myViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.designTitle);
            content = itemView.findViewById(R.id.designDes);
            time = itemView.findViewById(R.id.designTimestamp);
        }
    }
}
