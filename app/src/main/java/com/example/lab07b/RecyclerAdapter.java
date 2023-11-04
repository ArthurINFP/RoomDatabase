package com.example.lab07b;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab07b.db.StudentEntity;

public class RecyclerAdapter extends ListAdapter<StudentEntity, RecyclerAdapter.ViewHolder> {

    private ObservedStudent mListener;
    private Context context;

    public RecyclerAdapter(@NonNull DiffUtil.ItemCallback<StudentEntity> diffCallback, ObservedStudent mListener, Context context) {
        super(diffCallback);
        this.mListener = mListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.update(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView tvName, tvEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void update(StudentEntity item) {
            tvName.setText(item.getName());
            tvEmail.setText(item.getEmail());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem op1 = contextMenu.add(Menu.NONE, 1, Menu.NONE, "Edit");
            MenuItem op2 = contextMenu.add(Menu.NONE, 2, Menu.NONE, "Delete");
            op1.setOnMenuItemClickListener(this);
            op2.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id) {
                case 1:
                    mListener.observedStudent(getItem(getAdapterPosition()), MainActivity.ACTION_EDIT);
                    return true;
                case 2:
                    new AlertDialog.Builder(context)
                            .setTitle("Warning")
                            .setMessage("Do you want to delete student?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", (dialogInterface, i) -> mListener.observedStudent(getItem(getAdapterPosition()), MainActivity.ACTION_DELETE))
                            .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                            .show();
                    return true;

                default:
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    return false;
            }
        }
    }

    public static class StudentDiff extends DiffUtil.ItemCallback<StudentEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull StudentEntity oldItem, @NonNull StudentEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StudentEntity oldItem, @NonNull StudentEntity newItem) {
            return (oldItem.getName().equals(newItem.getName()) || oldItem.getEmail().equals(newItem.getEmail()));
        }
    }
}
