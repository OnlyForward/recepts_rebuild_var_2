package com.example.a123.recepts_rebuild_var_2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.recepts_rebuild_var_2.R;

public class IzbFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ReceptsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment,container,false);

        mAdapter = new ReceptsAdapter();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.reycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public class ReceptsAdapter extends RecyclerView.Adapter<ReceptsHolder>{


        @NonNull
        @Override
        public ReceptsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReceptsHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_fragment,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ReceptsHolder holder, int position) {

        }


        @Override
        public int getItemCount() {
            return 0;
        }
    }

    public class ReceptsHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView picture;
        private ImageView flag;


        public ReceptsHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.text1);
            picture = (ImageView)itemView.findViewById(R.id.image1);
            flag = (ImageView)itemView.findViewById(R.id.red_flag1);

            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void onBind(){

        }
    }
}
