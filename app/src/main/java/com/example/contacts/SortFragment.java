package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SortFragment extends Fragment implements ExternalSortAdapter.ExternalSortAdapterListener {

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    //SortAdapter adapter;
    ExternalSortAdapter adapter;
    ArrayList<String> labels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Sort");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        labels = new ArrayList<>();
        labels.add("Age");
        labels.add("Name");
        labels.add("State");


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //adapter = new SortAdapter(labels);

        adapter = new ExternalSortAdapter(getActivity(), this, labels);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void itemSortSelected(String sortBy, String sortDirection) {
        mListener.sortSelected(sortBy, sortDirection);
    }

    public class SortAdapter extends RecyclerView.Adapter<ViewSortHolder> {
        ArrayList<String> sortLabels;
        public SortAdapter(ArrayList<String> labels){
            sortLabels = labels;
        }

        @NonNull
        @Override
        public ViewSortHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.sort_list_item, parent, false);
            ViewSortHolder vh = new ViewSortHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewSortHolder holder, int position) {
            String label = sortLabels.get(position);
            holder.setupRowItem(label);
        }

        @Override
        public int getItemCount() {
            return sortLabels.size();
        }
    }

    public class ViewSortHolder extends RecyclerView.ViewHolder {
        private TextView textViewSortLabel;
        String mLabel;


        public ViewSortHolder(View view) {
            super(view);

            textViewSortLabel = view.findViewById(R.id.textViewSortLabel);

            view.findViewById(R.id.imageViewDesc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "onClick: DESC " + mLabel );
                    mListener.sortSelected(mLabel, "DESC");
                }
            });

            view.findViewById(R.id.imageViewAsc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "onClick: ASC " + mLabel);
                    mListener.sortSelected(mLabel, "ASC");
                }
            });
        }

        public void setupRowItem(String label){
            textViewSortLabel.setText(label);
            this.mLabel = label;
        }
    }

    SortFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SortFragmentListener) context;
    }

    interface SortFragmentListener{
        void sortSelected(String sortBy, String sortDirection);
    }
}