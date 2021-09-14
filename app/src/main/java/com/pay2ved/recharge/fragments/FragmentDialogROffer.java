package com.pay2ved.recharge.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.PlansAdapter;
import com.pay2ved.recharge.adapter.PlansROfferAdapter;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
import com.pay2ved.recharge.service.callmodel.CallROffer;
import com.pay2ved.recharge.service.query.DBQuery;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 * ( http://linktr.ee/wackycodes )
 */

public class FragmentDialogROffer extends DialogFragment implements Listener.OnItemSelectedListener, Listener.OnROfferListener {

    private Listener.OnItemSelectedListener parentListener;

    private String s_Username;
    private String s_Password;
    private String s_provider;
    private String s_Number;
    private boolean isMobileOffer;

    public FragmentDialogROffer(Listener.OnItemSelectedListener parentListener, String s_Username, String s_Password, String s_provider, String s_Number, boolean isMobileOffer) {
        this.parentListener = parentListener;
        this.s_Username = s_Username;
        this.s_Password = s_Password;
        this.s_provider = s_provider;
        this.s_Number = s_Number;
        this.isMobileOffer = isMobileOffer;
    }

    private List<ShowFormGetSet> offerList = new ArrayList<>();

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ImageView backBtn;
    private TextView textNoDataFound;
    private TextView textViewTitle;

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_r_offer, container, false);

        progressBar = view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.recyclerViewROffer );
        backBtn = view.findViewById(R.id.img_back );
        textNoDataFound = view.findViewById(R.id.textNoDataFound );
        textViewTitle = view.findViewById(R.id.txt_title );

        if (isMobileOffer){
            // Mobile R-OFFER...
        }else {
            textViewTitle.setText("DTH ROFFER");
        }

        offerList.clear();

        // Load Data...
        DBQuery.queryToROffer( this, s_Username, s_Password, s_Number, s_provider, !isMobileOffer );

        // Back Button Action
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDialogROffer.this.dismiss();
            }
        });

        return view;

    }

    @Override
    public void onItemSelected(CallDTHPlans.Amount amount, String planName) {
        FragmentDialogROffer.this.dismiss();
        parentListener.onItemSelected( amount, planName );
    }

    @Override
    public void onROfferLoad(@Nullable CallROffer rOffer) {
        progressBar.setVisibility(View.GONE);
        textNoDataFound.setVisibility(View.GONE);

        if (rOffer != null){
            if (rOffer.getError() == 0){
                recyclerView.setVisibility(View.VISIBLE);
            }

            if (rOffer.getData() != null){
                PlansROfferAdapter adapter = new PlansROfferAdapter( this, getContext(), null, rOffer.getData(), false );
                recyclerView.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
            if ( rOffer.getRoffer() != null){
                PlansROfferAdapter adapter = new PlansROfferAdapter( this, getContext(), rOffer.getRoffer(), null, true );
                recyclerView.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }

            // If Any Error Response...
            if (rOffer.getError() != 0){
                textNoDataFound.setVisibility(View.VISIBLE);
                textNoDataFound.setText(rOffer.getMessage());
            }
        }else{
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    /*

// START : R OFFER For Mobile ---------------
    public class Roffer{
    public String rs;
    public String desc;
}

public class Root{
    public int error;
    public String message;
    public String tel;
    public String operator;
    public List<Roffer> roffer;
}

// END : R OFFER For Mobile ---------------

// START : R OFFER For DTH ---------------
        {
            "error": 0,
            "number": "3035921078",
            "operator": "AirtelDTH",
            "message": "Plans fetch successfully",
            "data": [
                {
                    "amount": 3983,
                    "description": "Recharge with Rs 3983 for Value Sports 12 Months Pack & save Rs364."
                },
            ]
        }
     */



}