package com.example.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsersFragment extends Fragment {
    String TAG = "demo";
    String mState;
    String sortBy, sortDirection;


    public void setState(String state){
        mState = state;
    }

    public UsersFragment() {
        // Required empty public constructor
    }

    public void setSort(String sortBy, String sortDirection){
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    ListView listView;
    UsersAdapter adapter;
    ArrayList<User> usersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        getActivity().setTitle("Users");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        view.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showFilter();
            }
        });

        view.findViewById(R.id.buttonSort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showSort();
            }
        });

        listView = view.findViewById(R.id.listView);

        //Filtering part ..
        if(mState == null || mState.equals("All States")){
            usersList.clear();
            usersList.addAll(Data.users);
        } else{
            usersList.clear();
            for (User user: Data.users) {
                if(user.state.equals(mState)){
                    usersList.add(user);
                }
            }
        }

        if(sortBy != null && sortDirection != null){
            Collections.sort(usersList, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    int comparison = 0;

                    if(sortBy.equals("Age")){
                        comparison = user1.age - user2.age;
                    } else if(sortBy.equals("Name")){
                        comparison = user1.name.compareTo(user2.name);
                    } else if(sortBy.equals("State")){
                        comparison = user1.state.compareTo(user2.state);
                    }

                    if(sortDirection.equals("ASC")){
                        comparison = -1 * comparison;
                    }

                    return comparison;
                }
            });
        }

        adapter = new UsersAdapter(getActivity(), R.layout.user_list_item, usersList);
        listView.setAdapter(adapter);

        return view;
    }

    UsersFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UsersFragmentListener) context;
        Log.d(TAG, "onAttach: ");
    }

    interface UsersFragmentListener{
        void showFilter();
        void showSort();
    }

    class UsersAdapter extends ArrayAdapter<User>{

        public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
            }

            User user = getItem(position);
            ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewState = convertView.findViewById(R.id.textViewState);
            TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);
            TextView textViewAge = convertView.findViewById(R.id.textViewAge);


            if(user.gender.equals("Female")){
                imageViewIcon.setImageResource(R.drawable.avatar_female);
            } else{
                imageViewIcon.setImageResource(R.drawable.avatar_male);
            }

            textViewName.setText(user.name);
            textViewState.setText(user.state);
            textViewGroup.setText(user.group);
            textViewAge.setText(user.age + " Years Old");

            return convertView;
        }
    }

}