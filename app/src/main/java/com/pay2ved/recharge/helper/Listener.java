package com.pay2ved.recharge.helper;

import androidx.annotation.Nullable;

import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.model.ModelSearchableItem;
import com.pay2ved.recharge.service.callmodel.CallBalance;
import com.pay2ved.recharge.service.callmodel.CallCommission;
import com.pay2ved.recharge.service.callmodel.CallDTHInfo;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
import com.pay2ved.recharge.service.callmodel.CallHomeMessage;
import com.pay2ved.recharge.service.callmodel.CallObject;
import com.pay2ved.recharge.service.callmodel.CallPlans;
import com.pay2ved.recharge.service.callmodel.CallROffer;
import com.pay2ved.recharge.service.callmodel.CallRecharge;
import com.pay2ved.recharge.service.callmodel.CallRechargeReport;
import com.pay2ved.recharge.service.serverquery.PayBillQuery;

public interface Listener {

    //----------------------------------------------------------------------------------------------

    // Complaint ...
    interface OnObjectQueryListener{
        void onResponse(@Nullable CallObject callObject);
    }

    // Home Message Info
    interface OnHomeInfoListener{
        void onLoadHomeInfo( @Nullable CallHomeMessage homeMessage );
    }

    // Load Balance ...
    interface OnLoadBalanceListener{
        void onLoadBalance(@Nullable CallBalance callBalance );
    }

    // Commission Response...
    interface OnCommissionListener{
        void onLoadCommission(@Nullable CallCommission callCommission);
    }

    // Recharge Report...
    interface OnRechargeReportListener{
        void onLoadRechargeReport(@Nullable CallRechargeReport callRechargeReport);
    }

    // Plan List...
    interface OnPlansListener{
        void onLoadPlanList(@Nullable CallPlans callPlans);
    }

    // Recharge Info...
    interface OnRechargeListener{
        void onRechargeInfo(@Nullable CallRecharge callRecharge);
    }

    // DTH Info...
    interface OnDTHListener{
        void onDTHInfoLoad(@Nullable CallDTHInfo callDTHInfo);
    }

    // On DTH Plan List Load...
    interface OnDTHPlansListener{
        void onDTHPlansLoad(@Nullable CallDTHPlans callDTHPlans);
    }

    // On Load R Offer.....
    interface OnROfferListener{
        void onROfferLoad(@Nullable CallROffer rOffer);
    }

    //----------------------------------------------------------------------------------------------

    interface OnItemSelectedListener{
        void onItemSelected(CallDTHPlans.Amount amount, String planName );
    }

    interface OnPaymentListener{
        void onPaymentResponse(PayBillQuery.BillResponse billResponse );
    }

    interface OnItemSelectListener{
        void onSelectItem( ModelSearchableItem item );
    }

    // Net Connection...
    interface ConnectionListener{
        // Call : runOnUiThread(() -> {...}
        void onConnectChange(boolean isConnected);
    }

}
