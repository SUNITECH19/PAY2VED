package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.fragments.DialogFragmentSearchableSelectTap;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ModelSearchableItem;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.serverquery.PayBillQuery;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 * ( http://linktr.ee/wackycodes )
 */

public class ActivityBillPayments extends AppCompatActivity implements Listener.OnItemSelectListener, Listener.OnPaymentListener, DatePickerDialog.OnDateSetListener {

    public static final String REQUEST_BILL_CODE = "BILL_CODE";
    public static final int BILL_CODE_ELECTRICITY = 1;
    public static final int BILL_CODE_WATER = 2;
    public static final int BILL_CODE_GAS = 3;
    public static final int BILL_CODE_LAND_LINE = 4;
    public static final int BILL_CODE_INSURANCE = 5;

    private String userName;
    private String userPassword;
    private ResponseModel responseModel;
    private String operatorName;

    private ProgressDialog pDialog;
    private EditText editTextCustomerNumber;
    private EditText editTextAmount, editTextMobile, editTextSTDCode;
    private TextView textRemark, textTitle, textViewDOB;

    private ImageView btnBack;
    private Button btnViewBill, btnPayBill;
    // For Select ...
    private RelativeLayout layoutSelect;
    private ImageView spinImage;
    private TextView textViewSelected;


    private DatePickerDialog datePickerDialog;

    // Check Details...
    private LinearLayout layoutCustomerDetails;
    private LinearLayout layoutCustomerDetails2;
    private TextView textCustomerName, textBillNumber, textBillDate, textDueDate, textBillAmount, textDueAmount;
    private TextView textCustomerDetail;

    private ScrollView scrollView;

    // ------------- Spinner Data -----------
    String[] namelists;
    String[] codelist;
    String[] iconlist;
    String[] remarklist;
    private List<ModelSearchableItem> spinnerList = new ArrayList<>();

    // Temp.. Date Piker
    int startYear;
    int startMonth;
    int startDay;

    // -------------------- Request Code..
    private int requestCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);

        // Get Request Code...
        requestCode = getIntent().getIntExtra( REQUEST_BILL_CODE, -1 );

        scrollView = findViewById(R.id.scrollView);

        textTitle = findViewById(R.id.textTitle);
        textRemark = findViewById(R.id.txt_remark);

        editTextCustomerNumber = findViewById(R.id.edt_customer_number);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextSTDCode = findViewById(R.id.editTextSTDCode);

        btnPayBill = findViewById(R.id.btn_pay_bill);
        btnViewBill =  findViewById(R.id.btn_View_Bill);
        btnBack =  findViewById(R.id.img_back);

        layoutCustomerDetails = findViewById(R.id.layoutCustomerDetails);
        layoutCustomerDetails2 = findViewById(R.id.layoutCustomerDetails2);
        textCustomerDetail = findViewById(R.id.textCustomerDetail);
        textCustomerName = findViewById(R.id.textCustomerName);
        textBillNumber = findViewById(R.id.textBillNumber);
        textBillDate = findViewById(R.id.textBillDate);
        textDueDate = findViewById(R.id.textDueDate);
        textBillAmount = findViewById(R.id.textBillAmount);
        textDueAmount = findViewById(R.id.textDueAmount);
        textViewDOB = findViewById(R.id.textViewDOB);

        layoutSelect = findViewById(R.id.layoutSelectable);
        spinImage = findViewById(R.id.spinnerImages);
        textViewSelected = findViewById(R.id.spinnerTextView);

        editTextCustomerNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textRemark.setText( "Number Digit : " + s.length() );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initView();

        onButtonAction();
    }

    private void initView(){
        userName = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        userPassword = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

//        Log.e("LOG_IN", "userName: "+ userName + " userPassword: "+ userPassword);

        String codeStr = "";
        String nameStr = "";
        String remarkStr = "";
        String iconsStr = "";

        switch (requestCode){
            case BILL_CODE_ELECTRICITY:
                textTitle.setText( "Electricity");
                editTextCustomerNumber.setHint("Consumer Number/IVRS Number");
                //
                codeStr = AppsContants.sharedpreferences.getString(AppsContants.Elec_code, "");
                nameStr = AppsContants.sharedpreferences.getString(AppsContants.Elec_name, "");
                remarkStr = AppsContants.sharedpreferences.getString(AppsContants.Elec_remark, "");
                iconsStr = AppsContants.sharedpreferences.getString(AppsContants.Elec_icon, "");

                break;
            case BILL_CODE_WATER:
                textTitle.setText( "Water");

                codeStr = AppsContants.sharedpreferences.getString(AppsContants.Water_code, "");
                nameStr = AppsContants.sharedpreferences.getString(AppsContants.Water_name, "");
                remarkStr = AppsContants.sharedpreferences.getString(AppsContants.Water_remark, "");
                iconsStr = AppsContants.sharedpreferences.getString(AppsContants.Water_icon, "");

                break;
            case BILL_CODE_GAS:
                textTitle.setText( "Gas");
                codeStr = AppsContants.sharedpreferences.getString(AppsContants.Gas_code, "");
                nameStr = AppsContants.sharedpreferences.getString(AppsContants.Gas_name, "");
                remarkStr = AppsContants.sharedpreferences.getString(AppsContants.Gas_remark, "");
                iconsStr = AppsContants.sharedpreferences.getString(AppsContants.Gas_icon, "");

                break;
            case BILL_CODE_LAND_LINE:
                textTitle.setText( "Landline");
//                editTextSTDCode.setVisibility(View.VISIBLE);
                editTextSTDCode.setHint("STD Code");

                codeStr = AppsContants.sharedpreferences.getString(AppsContants.Land_code, "");
                nameStr = AppsContants.sharedpreferences.getString(AppsContants.Land_name, "");
                remarkStr = AppsContants.sharedpreferences.getString(AppsContants.Land_remark, "");
                iconsStr = AppsContants.sharedpreferences.getString(AppsContants.Land_icon, "");

                break;
            case BILL_CODE_INSURANCE:
                textTitle.setText( "Insurance");
                editTextMobile.setVisibility( View.VISIBLE );
//                textViewDOB.setVisibility( View.VISIBLE);

                codeStr = AppsContants.sharedpreferences.getString(AppsContants.Ins_code, "");
                nameStr = AppsContants.sharedpreferences.getString(AppsContants.Ins_name, "");
                remarkStr = AppsContants.sharedpreferences.getString(AppsContants.Ins_remark, "");
                iconsStr = AppsContants.sharedpreferences.getString(AppsContants.Ins_icon, "");

                break;
//            case BILL_CODE_POST_PAID:
//                textTitle.setText( "PostPaid");
//
//                codelist = AppsContants.sharedpreferences.getString(AppsContants.Post_code, "").split(",");
//                namelists = AppsContants.sharedpreferences.getString(AppsContants.Post_name, "").split(",");
//                //  remarklist = AppsContants.sharedpreferences.getString(AppsContants.Post_remark, "").split(",");
//                iconlist = AppsContants.sharedpreferences.getString(AppsContants.Post_icon, "").split(",");
//
//                break;
        }

//        System.out.println("CODES: " + codeStr );
//        System.out.println("Remarks: " + remarkStr );
//        System.out.println("Names: " + nameStr );

        codelist = codeStr.split(",");
        namelists = nameStr.split(",");
        remarklist = remarkStr.split(",");
        iconlist = iconsStr.split(",");

        Log.e("LIST_SIZE", "codelist :" + codelist.length + " namelists : " + namelists.length + " remarklist : " + remarklist.length );

        /**
         nameStr = "";
        codeStr = "";
        for( int x = 0; x < namelists.length; x++ ){
            nameStr = nameStr + x +" : "+  namelists[x] + "\n";
        }
        for( int x = 0; x < codelist.length; x++ ){
            codeStr = codeStr + x +" : "+  codelist[x] + "\n";
        }
        System.out.println(
                "Name List : \n" + nameStr +
                "\n Code List : \n" + codeStr
        );
         */

        for (int i = 1; i < namelists.length;i++){
            ModelSearchableItem item = new ModelSearchableItem();
            item.setTitle( namelists[i] );
            item.setCode(  codelist.length > i ? codelist[i]:"" );
            item.setRemark(  remarklist.length > i ? remarklist[i]:"" );
            item.setImage( iconlist.length > i ? iconlist[i]:"" );
            spinnerList.add( item );
        }

        // DatePiker Dialog...
        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(ActivityBillPayments.this, this, startYear, startMonth, startDay);

    }

    private void onButtonAction(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Dialog...
                showDialogToSelect();
            }
        });

        textViewDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDOB.setError(null);
                // Show Dialog...
                datePickerDialog.show();
            }
        });

        // View Bill Action....
        btnViewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatorName == null || operatorName.equals("") || operatorName.equals("null")){
                    textViewSelected.setError( "Please Select Operator!");
                    return;
                }
                if (TextUtils.isEmpty( editTextCustomerNumber.getText().toString() )){
                    editTextCustomerNumber.setError("Required field.!");
                    return;
                }
                // Hide Keyboard..
                hideKeyboard();

                onCheckBillAction();
            }
        });

        // Pay Bill...
        btnPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide Keyboard..
                hideKeyboard();
                // Query To Process...
                onPayBillAction();
            }
        });

    }

    private void onCheckBillAction(  ){
        switch (requestCode){
            case BILL_CODE_ELECTRICITY:
            case BILL_CODE_WATER:
            case BILL_CODE_GAS:
                queryToCheckBill(
                        new Httpjsontask( operatorName, editTextCustomerNumber.getText().toString() )
                );
                break;
            case BILL_CODE_LAND_LINE:
                // LandLine, GAS, : For BSNL STD CODE
                if (editTextSTDCode.getVisibility() == View.VISIBLE){
                    if (TextUtils.isEmpty(editTextSTDCode.getText().toString())){
                        editTextSTDCode.setError("Required!");
                        editTextSTDCode.requestFocus();
                        return;
                    }
                }
                queryToCheckBill(
                        new Httpjsontask( operatorName, editTextCustomerNumber.getText().toString(), editTextSTDCode.getText().toString() )
                );
                break;
            case BILL_CODE_INSURANCE:
                if (TextUtils.isEmpty(editTextMobile.getText().toString())){
                    editTextMobile.setError("Required!");
                    editTextMobile.requestFocus();
                    return;
                }
                if (textViewDOB.getVisibility() == View.VISIBLE){
                    if (TextUtils.isEmpty(textViewDOB.getText().toString())){
                        textViewDOB.setError("Required!");
                        return;
                    }
                }

                queryToCheckBill(
                        new Httpjsontask( operatorName, editTextCustomerNumber.getText().toString(),
                                editTextMobile.getText().toString(),
                                textViewDOB.getText().toString() )
                );
                break;
        }
    }

    private void onPayBillAction(){
        if (TextUtils.isEmpty(editTextAmount.getText().toString())){
            editTextAmount.setError("Required Field!");
            editTextAmount.requestFocus();
            return;
        }

        showDialog();
        // Query To Process...
        PayBillQuery billQuery;
        switch (requestCode){
            case BILL_CODE_ELECTRICITY:
            case BILL_CODE_WATER:
                billQuery = new PayBillQuery(
                        userName, userPassword, operatorName,  editTextCustomerNumber.getText().toString(), editTextAmount.getText().toString(),
                        responseModel.customer_name, responseModel.bill_number, responseModel.bill_date, responseModel.due_date, responseModel.due_amount,
                        "", "", ""
                );
                billQuery.setListener( ActivityBillPayments.this );
                billQuery.execute();
                break;
            case BILL_CODE_GAS:
            case BILL_CODE_LAND_LINE:
                billQuery = new PayBillQuery(
                        userName, userPassword, operatorName,  editTextCustomerNumber.getText().toString(), editTextAmount.getText().toString(),
                        responseModel.customer_name, responseModel.bill_number, responseModel.bill_date, responseModel.due_date, responseModel.due_amount,
                        "", "", editTextSTDCode.getText().toString()
                );
                billQuery.setListener( ActivityBillPayments.this );
                billQuery.execute();
                break;
            case BILL_CODE_INSURANCE:
                billQuery = new PayBillQuery(
                        userName, userPassword, operatorName,  editTextCustomerNumber.getText().toString(), editTextAmount.getText().toString(),
                        responseModel.customer_name, responseModel.bill_number, responseModel.bill_date, responseModel.due_date, responseModel.due_amount,
                        editTextMobile.getText().toString(), textViewDOB.getText().toString(), ""
                );

                billQuery.setListener( ActivityBillPayments.this );
                billQuery.execute();
                break;
        }
    }

    // Show Dialog For Customer Info...
    public void showDialogToSelect( ) {
        textViewSelected.setError( null );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("SHOW_DIALOG");
        if (prev != null) {
            ft.remove(prev);
        }
        //  Assign Required Info...
        DialogFragmentSearchableSelectTap fragment = new DialogFragmentSearchableSelectTap( spinnerList, this );
        fragment.show( fragmentManager, "SHOW_DIALOG");
    }

    private void showDialog(){
        if (pDialog == null ){
            pDialog = new ProgressDialog( this );
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissDialog(){
        if (pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getApplicationWindowToken() != null){
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
        }
    }

    private void setData(){
        // Change UI...
        editTextCustomerNumber.setEnabled( false );
        editTextCustomerNumber.setClickable( false );
        editTextCustomerNumber.setFocusable( false );
        editTextCustomerNumber.clearFocus();

        layoutSelect.setClickable( false );
        layoutSelect.setFocusable( false );

        btnViewBill.setVisibility(View.GONE);
        btnPayBill.setVisibility( View.VISIBLE );
        editTextAmount.setVisibility( View.VISIBLE );


        layoutCustomerDetails.setVisibility(View.VISIBLE);

        textCustomerName.setText( responseModel.customer_name );
        textBillNumber.setText( responseModel.bill_number );
        textBillDate.setText( responseModel.bill_date );
        textDueDate.setText( responseModel.due_date );
        textBillAmount.setText( "₹" + responseModel.bill_amount );
        textDueAmount.setText( "₹" + responseModel.due_amount );

        if (responseModel.partial_payment.equals("n")){
            editTextAmount.setClickable( false );
            editTextAmount.setFocusable( false );
            editTextAmount.clearFocus();
        }else{
            editTextAmount.setClickable( true );
            editTextAmount.setFocusable( true );
        }

        editTextAmount.setText( responseModel.bill_amount );

        // -- Special Case...
        if (requestCode == BILL_CODE_INSURANCE){
            textViewDOB.setClickable( false );
            textViewDOB.setFocusable( false );
        }
    }

    private void allowToNextStep(){
        btnViewBill.setVisibility(View.GONE);
        btnPayBill.setVisibility( View.VISIBLE );
        editTextAmount.setVisibility( View.VISIBLE );

        // Set Window If Response Code = 2...
        layoutCustomerDetails2.setVisibility(View.VISIBLE);
        textCustomerDetail.setText( responseModel.msg );

        editTextAmount.requestFocus();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        startYear = year;
        startMonth = month;
        startDay = dayOfMonth;

        //=========================================
        int Day = startDay;
        int Month = startMonth + 1;

        String s_dayOfMonth = Day + "";
        String s_month = Month + "";

        if (Day < 10) {
            s_dayOfMonth = "0" + Day;
        }

        if (Month < 10) {
            s_month = "0" + Month;
        }

        textViewDOB.setText( startYear + "-" + (s_month) + "-" + s_dayOfMonth);
    }
    @Override
    public void onSelectItem(ModelSearchableItem item) {
        if (item != null){
            operatorName = item.getCode();
            Log.e("ActivityBillPayment", "OP : " + item.getCode() + " Remark : " + item.getRemark() );
            textViewSelected.setText( item.getTitle() );

            try {
                Picasso.get().load(item.getImage()).placeholder(R.drawable.default_icon).into(spinImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (item.getRemark() != null && !item.getRemark().equals("") && !item.getRemark().equals("null")){
                textRemark.setText( item.getRemark() );
            }

            switch (requestCode){
                case BILL_CODE_INSURANCE:
                    if (isMatchOperator( operatorName )){
                        textViewDOB.setVisibility( View.VISIBLE );
                    }else {
                        textViewDOB.setVisibility( View.GONE );
                    }
                    break;
                case BILL_CODE_LAND_LINE:
                    // Show Only When LANDLINE Bill Payment && BSNL LandLine...
                    if( operatorName.equals("BSNLPH")){
                        editTextSTDCode.setVisibility(View.VISIBLE);
                    }else {
                        editTextSTDCode.setVisibility(View.GONE);
                    }
                    break;
            }
        }

    }

    @Override
    public void onPaymentResponse(PayBillQuery.BillResponse billResponse) {
        dismissDialog();
        if (billResponse.status == 0){
            // Success..
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBillPayments.this);
            builder.setTitle( billResponse.msg + "\n ");

            builder.setMessage("Number/ID :  " + billResponse.number + "\n " + "Operator :  " + billResponse.operator + "\n "
                    + "Amount :  " + billResponse.amount + "\n "
                    + "TXT NO. :  " + billResponse.txn_no + "\n "
                    + "Ref. Number :  " + billResponse.operator_ref_no + "\n "
            );

            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    if (billResponse.status == 0){
                        // Success..
                        dialog.dismiss();
                        ActivityBillPayments.this.finish();
                    }else{
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            // Error...
            Snackbar.make( scrollView.getRootView(), billResponse.msg, Snackbar.LENGTH_LONG).show();
        }
    }

    //======================================== start_http==========================================/
    private void queryToCheckBill( Httpjsontask viewBillQuery){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBillPayments.this);
        builder.setTitle("Do you want to sure confirm !" + "\n ");
        builder.setMessage( "Mobile No. :  " + viewBillQuery.accountNum  + "\n " + "Operator :  " + viewBillQuery.operatorName );
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDialog();
                viewBillQuery.execute();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class Httpjsontask extends AsyncTask<String, Void, String> {

        private String operatorName;
        private String accountNum;

        private String mobileNum;
        private String dob;
        private String stdCode;

        public Httpjsontask(String operatorName, String accountNum ) {
            this.operatorName = operatorName;
            this.accountNum = accountNum;
        }

        public Httpjsontask(String operatorName, String accountNum, String stdCode ) {
            this.operatorName = operatorName;
            this.accountNum = accountNum;
            this.stdCode = stdCode;
        }

        public Httpjsontask(String operatorName, String accountNum, String mobileNum, String dob) {
            this.operatorName = operatorName;
            this.accountNum = accountNum;
            this.mobileNum = mobileNum;
            this.dob = dob;
        }

        private String apiLink;

        private int resCode = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
            responseModel = new ResponseModel();

            switch (requestCode){
                case BILL_CODE_ELECTRICITY:
                    apiLink = HttpUrlPath.urlPath + HttpUrlPath.action_info_electricity;
                    break;
                case BILL_CODE_WATER:
                    apiLink = HttpUrlPath.urlPath + HttpUrlPath.action_info_water;
                    break;
                case BILL_CODE_GAS:
                    apiLink = HttpUrlPath.urlPath + HttpUrlPath.action_info_gas;
                    break;
                case BILL_CODE_LAND_LINE:
                    apiLink = HttpUrlPath.urlPath + HttpUrlPath.action_info_landLine;
                    break;
                case BILL_CODE_INSURANCE:
                    apiLink = HttpUrlPath.urlPath + HttpUrlPath.action_info_insurance;
                    break;
            }

        }

        protected String doInBackground(String... arg0) {
            String errorMsg = null;

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost( apiLink );

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", userName));
                nameValuePair.add(new BasicNameValuePair("password", userPassword));
                nameValuePair.add(new BasicNameValuePair("operator", operatorName));
                nameValuePair.add(new BasicNameValuePair("number", accountNum));

                switch (requestCode){
                    case BILL_CODE_INSURANCE:
                        // Insurance..
                        nameValuePair.add(new BasicNameValuePair("mobile", mobileNum ));
                        nameValuePair.add(new BasicNameValuePair("dob", dob));
                        break;
//                    case BILL_CODE_GAS:
                    case BILL_CODE_LAND_LINE:
                        // LandLine, GAS, : For BSNL STD CODE
                        nameValuePair.add(new BasicNameValuePair("stdcode", stdCode ));
                        break;
                    default:
                        break;
                }

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);

                JSONObject js = new JSONObject(object);

                resCode = js.getInt( "status");
                responseModel.msg = js.getString( "msg" );
                responseModel.operator = js.getString( "operator" );

                if (resCode == 0 && js.has("info")){
                    // Success...
                    JSONObject infoData = js.getJSONObject( "info" );
                    responseModel.customer_name = infoData.getString("customer_name");
                    responseModel.bill_number = infoData.getString("bill_number");
                    responseModel.bill_date = infoData.getString("bill_date");
                    responseModel.due_date = infoData.getString("due_date");
                    responseModel.bill_amount = infoData.getString("bill_amount");
                    responseModel.due_amount = infoData.getString("due_amount");
                    responseModel.partial_payment = infoData.getString("partial_payment");

                    responseModel.customer_mobile = infoData.has("customer_mobile") ? infoData.getString("customer_mobile") : "";

                }else{
                    // Failed...

                }


            } catch (Exception e) {
                Log.d("ActivityElectricity", "Error : " + e.getMessage());
                errorMsg = e.getMessage();
                e.printStackTrace();
            }
            return errorMsg;
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            dismissDialog();

            if (resCode != 0){
                // Show Error...
                if (resCode == 2){ /// Allow User To Input Amount And  Proceed To Payment...
                    allowToNextStep();
                }else {
                    Snackbar.make( scrollView.getRootView(), responseModel.msg + ( result1 !=null? " Error : " +result1 :  "" ), Snackbar.LENGTH_SHORT ).show();
                }
            }else{
                // Set UI Data..
                setData();
            }

        }

    }

    //============================================ end ======================================//

    public static class ResponseModel{

//        public int status;
        public String msg;
        public String number;
        public String operator;
//        public Info info;

        public String customer_name;

        public String customer_mobile; // Insurance, LandLine, postpaid

        public String bill_number;
        public String bill_date;
        public String due_date;
        public String bill_amount;
        public String due_amount;
        public String partial_payment;
    }

    private boolean isMatchOperator(String query){
        String[] insuranceOpCode = {"AVLI", "BALI", "AXLI", "CHOL", "ETLI", "EXLI", "FGIL", "HDFC", "ICICIPRU", "IDBI", "MAXL", "RNLI", "SBIL", "SLIC", "SUDI", "TATAAIA" };

        List<String> insuranceOpCodes = Arrays.asList(insuranceOpCode);
        if (insuranceOpCodes.contains( query )){
            return true;
        }else
            return false;
    }


}
