package com.example.expensemanagerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import javax.microedition.khronos.egl.EGLDisplay;

import Model.Data;


public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //floating button
    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;
    private Button btnCancel;

    //fab textview
    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean isOpen = false;

    private Animation FadeOpen, FadeClose;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    //recycler view
    private RecyclerView mRecyclerIncome;
    private RecyclerView mRecyclerExpense;
    private TextView totalIncomeResult, totalExpenseResult, txtTest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        Log.d("user",uid);
        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseDatabase").child(uid);

        mIncomeDatabase.keepSynced(true);
        mExpenseDatabase.keepSynced(true);

//        Log.d("BBB", mUser + "");
//        Log.d("CCC", uid);  //id email cua nguoi dung
//        Log.d("DDD", mIncomeDatabase + "");  //duong link database

        //connect float button to layout
        fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn = myview.findViewById(R.id.income_ft_btn);
        fab_expense_btn = myview.findViewById(R.id.expense_ft_btn);

        //connect floating text
        fab_income_txt = myview.findViewById(R.id.income_ft_text);
        fab_expense_txt = myview.findViewById(R.id.expense_ft_text);

        //textview result
        totalIncomeResult = myview.findViewById(R.id.income_set_result);
        totalExpenseResult = myview.findViewById(R.id.expense_set_result);
        txtTest = myview.findViewById(R.id.test);

        //recycler view
        mRecyclerIncome = myview.findViewById(R.id.recycler_income);
        mRecyclerExpense = myview.findViewById(R.id.recycler_expense);

        //set truoc layout o day de tranh loi "no layout manager attached, skipping layout"

        LinearLayoutManager incomelayoutmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager expenselayoutmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        mRecyclerExpense.setLayoutManager(incomelayoutmanager);
        mRecyclerExpense.setHasFixedSize(true);

        mRecyclerIncome.setHasFixedSize(true);
        mRecyclerIncome.setLayoutManager(expenselayoutmanager);

        //Animation connect
        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);


        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();
                if (isOpen) {
                    fab_income_btn.startAnimation(FadeClose);
                    fab_expense_btn.startAnimation(FadeClose);
                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);

                    fab_income_txt.startAnimation(FadeClose);
                    fab_expense_txt.startAnimation(FadeClose);
                    fab_income_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);

                    isOpen = false;
                } else {
                    fab_income_btn.startAnimation(FadeOpen);
                    fab_expense_btn.startAnimation(FadeOpen);
                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);

                    fab_income_txt.startAnimation(FadeOpen);
                    fab_expense_txt.startAnimation(FadeOpen);
                    fab_income_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);

                    isOpen = true;
                }
            }
        });

        //calculate total income
        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalIncome = 0;
                for (DataSnapshot mysnap : snapshot.getChildren()) {
                    Data data = mysnap.getValue(Data.class);
                    totalIncome += data.getAmount();

                    String stResult = String.valueOf(totalIncome);
                    totalIncomeResult.setText(stResult);
                    tinhSoDu();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //calculate expensse result

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalExpense = 0;
                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);
                    totalExpense += data.getAmount();
                    String totalsum2 = String.valueOf(totalExpense);
                    totalExpenseResult.setText(totalsum2 );
                    tinhSoDu();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return myview;
    }

    private void tinhSoDu(){
        txtTest.setText(String.valueOf(Integer.parseInt(totalIncomeResult.getText().toString()) - Integer.parseInt(totalExpenseResult.getText().toString())));
    }

    //Floating button animation

    private void floatButtonAnimation() {

        if (isOpen) {
            fab_income_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_txt.startAnimation(FadeClose);
            fab_expense_txt.startAnimation(FadeClose);
            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);

            isOpen = false;
        } else {
            fab_income_btn.startAnimation(FadeOpen);
            fab_expense_btn.startAnimation(FadeOpen);
            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_txt.startAnimation(FadeOpen);
            fab_expense_txt.startAnimation(FadeOpen);
            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);

            isOpen = true;
        }
    }

    private void addData() {
        //Fab button income

        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });

    }

    public void incomeDataInsert() {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myView);
        final AlertDialog dialog = mydialog.create();

        dialog.setCancelable(false); // bam vao vung ngoai dialog thi dialog k bi mat di

        final EditText edtAmount = myView.findViewById(R.id.amount_edt);
        final EditText edtType = myView.findViewById(R.id.type_edt);
        final EditText edtNote = myView.findViewById(R.id.note_edt);

        Button btnSave = myView.findViewById(R.id.btnSave);
        Button btnCancel = myView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = edtType.getText().toString().trim();
                String amount = edtAmount.getText().toString().trim();
                String note = edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(type)) {
                    edtType.setError("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    edtAmount.setError("Required Field...");
                    return;
                }

                int ouramountint = Integer.parseInt(amount);

                if (TextUtils.isEmpty(note)) {
                    edtNote.setError("Required Field...");
                    return;
                }

                String id = mIncomeDatabase.push().getKey();  //moi khoan chi tieu deu co 1 id rieng

                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ouramountint, type, note, id, mDate);  //Lop Data trong Model

                mIncomeDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data Added", Toast.LENGTH_SHORT).show();

                floatButtonAnimation();

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatButtonAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void expenseDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myView = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();

        dialog.setCancelable(false);

        final EditText amount = myView.findViewById(R.id.amount_edt);
        final EditText type = myView.findViewById(R.id.type_edt);
        final EditText note = myView.findViewById(R.id.note_edt);

        Button btnSave = myView.findViewById(R.id.btnSave);
        Button btnCancel = myView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmAmount = amount.getText().toString().trim();
                String tmType = type.getText().toString().trim();
                String tmNote = note.getText().toString().trim();

                if (TextUtils.isEmpty(tmAmount)) {
                    amount.setError("Requried Field...");
                    return;
                }

                int inamount = Integer.parseInt(tmAmount);

                if (TextUtils.isEmpty(tmType)) {
                    type.setError("Requried Field...");
                    return;
                }
                if (TextUtils.isEmpty(tmNote)) {
                    note.setError("Required Field...");
                    return;
                }

                //insert expense data to database
                String id = mExpenseDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(inamount, tmType, tmNote, id, mDate);
                mExpenseDatabase.child(id).setValue(data);


                Toast.makeText(getActivity(), "Data added", Toast.LENGTH_SHORT).show();
                floatButtonAnimation();
                dialog.dismiss();

                //recycler
                LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                layoutManagerIncome.setStackFromEnd(true);
                layoutManagerIncome.setReverseLayout(true);
                mRecyclerIncome.setHasFixedSize(true);
                mRecyclerIncome.setLayoutManager(layoutManagerIncome);

                LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                layoutManagerExpense.setReverseLayout(true);
                layoutManagerExpense.setStackFromEnd(true);
                mRecyclerExpense.setHasFixedSize(true);
                mRecyclerExpense.setLayoutManager(layoutManagerExpense);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatButtonAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        //retrieve data to income recyclerview
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mIncomeDatabase, Data.class)
                .setLifecycleOwner(this)
                .build();

        FirebaseRecyclerAdapter<Data, IncomeViewHolder> incomeAdapter = new FirebaseRecyclerAdapter<Data, IncomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull IncomeViewHolder holder, int position, @NonNull Data model) {
                holder.setIncomeType(model.getType());
                holder.setIncomeAmount(model.getAmount() +" USD");
                holder.setIncomeDate(model.getDate());
                Log.d("NNN",model.getType());
                Log.d("MMM",model.getAmount()+"");

            }

            @NonNull
            @Override
            public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new IncomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dasboard_income, parent, false));
            }
        };

        mRecyclerIncome.setAdapter(incomeAdapter);

        //retrieve data to expense recyclerview
        FirebaseRecyclerOptions<Data> options2 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mExpenseDatabase, Data.class)
                .setLifecycleOwner(this)
                .build();

        FirebaseRecyclerAdapter<Data, ExpenseViewHolder> expenseAdapter = new FirebaseRecyclerAdapter<Data, ExpenseViewHolder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position, @NonNull Data model) {
                holder.setExpenseType(model.getType());
                holder.setExpenseAmount(model.getAmount()+" USD");
                holder.setExpenseDate(model.getDate());
            }

            @NonNull
            @Override
            public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ExpenseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_expense, parent, false));
            }
        };

        mRecyclerExpense.setAdapter(expenseAdapter);
    }

    //for income data
    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
        View mIncomView;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mIncomView = itemView;
        }

        public void setIncomeType(String type) {
            TextView mType = mIncomView.findViewById(R.id.type_income_dashboard);
            mType.setText(type);
        }

        public void setIncomeAmount(String amount) {
            TextView mAmount = mIncomView.findViewById(R.id.amount_income_dashboard);
            String strAmount = String.valueOf(amount);
            mAmount.setText(strAmount);
        }

        public void setIncomeDate(String date) {
            TextView mDate = mIncomView.findViewById(R.id.date_income_dashboard);
            mDate.setText(date);
        }
    }

    //for expense data
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        View mExpenseView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            mExpenseView = itemView;
        }

        public void setExpenseType(String type) {
            TextView mtype = mExpenseView.findViewById(R.id.type_expense_dashboard);
            mtype.setText(type);
        }

        public void setExpenseAmount(String  amount) {
            TextView mAmount = mExpenseView.findViewById(R.id.amount_expense_dashboard);
            String strAmount = String.valueOf(amount);
            mAmount.setText(strAmount);
        }

        public void setExpenseDate(String date) {
            TextView mDate = mExpenseView.findViewById(R.id.date_expense_dashboard);
            mDate.setText(date);
        }
    }
}
