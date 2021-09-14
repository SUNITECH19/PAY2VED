package com.pay2ved.recharge.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.searchable.AdaptorListSearchable;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ModelSearchableItem;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentSearchableSelectTap extends DialogFragment implements Listener.OnItemSelectListener {

    public DialogFragmentSearchableSelectTap(List<ModelSearchableItem> spinnerList, Listener.OnItemSelectListener itemSelectListener) {
        // Required empty public constructor
        this.spinnerList.addAll( spinnerList );
        this.itemSelectListener = itemSelectListener;
    }

    private int requestCode;

    private Listener.OnItemSelectListener itemSelectListener;
    private List<ModelSearchableItem> spinnerList = new ArrayList<>();

    // Adaptor...
    private AdaptorListSearchable adapter;

    private EditText editTextSearch;
    private ListView listView;
    private ImageView imageViewBackBtn;
    private TextView textViewNoDataTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);

        textViewNoDataTxt = view.findViewById(R.id.text_view_no_found_text);
        imageViewBackBtn = view.findViewById(R.id.image_view_back_btn);
        editTextSearch = view.findViewById(R.id.edit_text_search);
        listView = view.findViewById(R.id.list_view);
        onSearch();

        if (getDialog()!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }

        if (spinnerList == null || spinnerList.size() == 0){
            textViewNoDataTxt.setVisibility( View.VISIBLE );
        }

        adapter = new AdaptorListSearchable( getContext(), spinnerList, this );
        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

//        searchView.setOnQueryTextListener(this);
        imageViewBackBtn.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        return view;
    }

    private void onSearch(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter( s.toString() );
            }
        });
    }

    @Override
    public void onSelectItem(ModelSearchableItem item) {
        if (itemSelectListener!=null){
            itemSelectListener.onSelectItem( item );
        }
        getDialog().dismiss();
    }


}

