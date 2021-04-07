package com.example.contacts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExternalSortAdapter extends RecyclerView.Adapter<ExternalSortAdapter.ViewSortHolder> {

    ArrayList<String> sortLabels;
    Context mContext;
    ExternalSortAdapterListener mListener;

    public ExternalSortAdapter(Context context, ExternalSortAdapterListener mListener, ArrayList<String> labels){
        this.mContext = context;
        sortLabels = labels;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ExternalSortAdapter.ViewSortHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sort_list_item, parent, false);
        ViewSortHolder vh = new ViewSortHolder(view, this.mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalSortAdapter.ViewSortHolder holder, int position) {
        String label = sortLabels.get(position);
        holder.setupRowItem(label);
    }

    @Override
    public int getItemCount() {
        return sortLabels.size();
    }

    static public class ViewSortHolder extends RecyclerView.ViewHolder {
        private TextView textViewSortLabel;
        String mLabel;
        ExternalSortAdapterListener mListener;

        public ViewSortHolder(View view, ExternalSortAdapterListener mListener) {
            super(view);
            this.mListener = mListener;
            textViewSortLabel = view.findViewById(R.id.textViewSortLabel);

            view.findViewById(R.id.imageViewDesc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "onClick: DESC " + mLabel );
                    mListener.itemSortSelected(mLabel, "DESC");
                }
            });

            view.findViewById(R.id.imageViewAsc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "onClick: ASC " + mLabel);
                    mListener.itemSortSelected(mLabel, "ASC");
                }
            });
        }

        public void setupRowItem(String label){
            textViewSortLabel.setText(label);
            this.mLabel = label;
        }
    }

    interface ExternalSortAdapterListener {
        void itemSortSelected(String sortBy, String sortDirection);
    }
}
