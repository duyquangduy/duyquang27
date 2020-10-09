package com.example.expensemanagerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

import Model.Data;

public class IncomeFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public IncomeFragment() {
//        // Required empty public constructor
//    }
//    // TODO: Rename and change types and number of parameters
//    public static IncomeFragment newInstance(String param1, String param2) {
//        IncomeFragment fragment = new IncomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;


    //Recyclerview
    private RecyclerView recyclerView;

    private TextView incomeSumResult;

    //update edittext
    private EditText edtAmount,edtType,edtNote;

    private Button btnupdate, btndelete;

    //data item value
    private String  type, note;
    private int amount;
    private String post_key;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_income, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();
        //Log.d("AA",uid+"");
        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        //Log.d("BB",mIncomeDatabase+"");
        recyclerView = myView.findViewById(R.id.recycler_id_income);
        incomeSumResult = myView.findViewById(R.id.income_txt_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //Tính tổng các khoản thu
        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int incomeSum = 0;
                for (DataSnapshot mySnapshot : snapshot.getChildren()) {
                    Data data = mySnapshot.getValue(Data.class);
                    incomeSum += data.getAmount();

                    String strIncomeSum = String.valueOf(incomeSum);

                    incomeSumResult.setText(strIncomeSum+".00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //khoi tao firebaserecycleroption thi moi co firebaserecyclerAdapter
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mIncomeDatabase, Data.class)
                .setLifecycleOwner(this)
                .build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, final Data model) {
                holder.setAmount(model.getAmount());
                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_key = getRef(position).getKey();

                        type = model.getType();
                        note = model.getNote();
                        amount = model.getAmount();

                        updateDataItem();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        private void setType(String type) {
            TextView mType = view.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }

        private void setNote(String note) {
            TextView mNote = view.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }

        private void setDate(String date) {
            TextView mDate = view.findViewById(R.id.date_txt_income);
            mDate.setText(date);
        }

        private void setAmount(int amount) {
            TextView mAmount = view.findViewById(R.id.amount_txt_income);
            String stAmount = String.valueOf(amount);
            mAmount.setText(stAmount);
        }
    }

    private void updateDataItem(){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myview = inflater.inflate(R.layout.update_data_item,null);
        mydialog.setView(myview);

        edtAmount=myview.findViewById(R.id.udamount_edt);
        edtType=myview.findViewById(R.id.udtype_edt);
        edtNote = myview.findViewById(R.id.udnote_edt);

        //set data to edit text
        edtType.setText(type);
        edtType.setSelection(type.length());

        edtNote.setText(note);
        edtNote.setSelection(note.length());

        edtAmount.setText(String.valueOf(amount));
        edtAmount.setSelection(String.valueOf(amount).length());

        btndelete = myview.findViewById(R.id.btn_delete);
        btnupdate = myview.findViewById(R.id.btnupdate);

        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = edtType.getText().toString().trim();
                note = edtNote.getText().toString().trim();

                String mdAmout = String.valueOf(amount);
                mdAmout = edtAmount.getText().toString().trim();

                int myAmount = Integer.parseInt(mdAmout);

                String mDate = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(myAmount,type,note,post_key,mDate);

                mIncomeDatabase.child(post_key).setValue(data);

                dialog.dismiss();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIncomeDatabase.child(post_key).removeValue();

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
