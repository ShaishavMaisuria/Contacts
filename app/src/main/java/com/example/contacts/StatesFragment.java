package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class StatesFragment extends Fragment {


    public StatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    ArrayList<String> states = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Filter By State");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_states, container, false);

        HashSet<String> statesMap = new HashSet<>();

        for (User user: Data.users) {
            statesMap.add(user.state);
        }

        states = new ArrayList<>();
        states.addAll(statesMap);
        Collections.sort(states);
        states.add(0, "All States");

        listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, states);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.statePicked(states.get(position));
            }
        });

        return view;
    }
    StatesFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StatesFragmentListener) context;
    }

    interface StatesFragmentListener{
        void statePicked(String state);
    }

}