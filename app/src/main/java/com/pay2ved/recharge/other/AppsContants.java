package com.pay2ved.recharge.other;

import android.content.Context;
import android.content.SharedPreferences;

public class AppsContants {


    public static SharedPreferences sharedpreferences;
    private static String myprefrencesKey;
    public static final String MyPREFERENCES = myprefrencesKey;

    public static SharedPreferences getSharedPreferences( Context context ){
        return AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static final String USERID = "USERIDKey";
    public static final String Mobile = "MobileKey";
    public static final String Uid = "UidKey";
    public static final String Type = "TypeKey";
    public static final String Password = "PasswordKey";
    public static final String Fullname = "FullnameKey";
    public static final String Username = "UsernameKey";
    public static final String CompanyName = "CompanyNameKey";
    public static final String Email = "EmailKey";
    public static final String Address = "AddressKey";
    public static final String City = "CityKey";
    public static final String State = "StateKey";
    public static final String Balance = "BalanceKey";
    public static final String Phone = "PhoneKey";
    public static final String Name = "NameKey";
    public static final String Account = "AccountKey";
    public static final String Ifsc = "IfscKey";
    public static final String VERIFICATION_REF_CODE = "VERIFICATION_REF_CODEKey";
    public static final String amount = "amountKey";
    public static final String mobile = "mobileKey";
    public static final String code = "codeKey";
    public static final String Position = "PositionKey";
    public static final String GateWay_No = "GateWay_NoKey";
    public static final String Spinner_list = "Spinner_listKey";
    public static final String ID = "IDKey";
    public static final String TITTLE = "TITTLEKey";
    public static final String REF_NO = "REF_NOKey";
    public static final String DATE_FROM = "DATE_FROMKey";
    public static final String DATE_TO = "DATE_TOKey";
    public static final String AMOUNT = "AMOUNTKey";
    public static final String DATE = "DATEKey";
    public static final String NUMBER = "NUMBERKey";
    public static final String STATUS = "STATUSKey";
    public static final String REMARK = "REMARKKey";
    public static final String Action = "ActionKey";
    public static final String Title = "TitleKey";
    public static final String Date = "DateKey";
    public static final String Description = "DescriptionKey";

    public static final String Mob_name = "Mob_nameKey";
    public static final String Mob_code = "Mob_codeKey";
    public static final String Mob_remark = "Mob_remarkKey";
    public static final String Mob_hint1 = "Mob_hint1Key";
    public static final String Mob_hint2 = "Mob_hint2Key";
    public static final String Mob_hint3 = "Mob_hint3Key";
    public static final String Mob_icon = "Mob_iconKey";

    public static final String Dth_name = "Dth_nameKey";
    public static final String Dth_code = "Dth_codeKey";
    public static final String Dth_remark = "Dth_remarkKey";
    public static final String Dth_hint1 = "Dth_hint1Key";
    public static final String Dth_hint2 = "Dth_hint2Key";
    public static final String Dth_hint3 = "Dth_hint3Key";
    public static final String Dth_icon = "Dth_iconKey";

    public static final String Data_name = "Data_nameKey";
    public static final String Data_code = "Data_codeKey";
    public static final String Data_remark = "Data_remarkKey";
    public static final String Data_hint1 = "Data_hint1Key";
    public static final String Data_hint2 = "Data_hint2Key";
    public static final String Data_hint3 = "Data_hint3Key";
    public static final String Data_icon = "Data_iconKey";

    public static final String Post_name = "Post_nameKey";
    public static final String Post_code = "Post_codeKey";
    public static final String Post_remark = "Post_remarkKey";
    public static final String Post_hint1 = "Post_hint1Key";
    public static final String Post_hint2 = "Post_hint2Key";
    public static final String Post_hint3 = "Post_hint3Key";
    public static final String Post_icon = "Post_iconKey";

    public static final String Land_name = "Land_nameKey";
    public static final String Land_code = "Land_codeKey";
    public static final String Land_remark = "Land_remarkKey";
    public static final String Land_hint1 = "Land_hint1Key";
    public static final String Land_hint2 = "Land_hint2Key";
    public static final String Land_hint3 = "Land_hint3Key";
    public static final String Land_icon = "Land_iconKey";

    public static final String Elec_name = "Elec_nameKey";
    public static final String Elec_code = "Elec_codeKey";
    public static final String Elec_remark = "Elec_remarkKey";
    public static final String Elec_hint1 = "Elec_hint1Key";
    public static final String Elec_hint2 = "Elec_hint2Key";
    public static final String Elec_hint3 = "Elec_hint3Key";
    public static final String Elec_icon = "Elec_iconKey";

    public static final String Money_name = "Money_nameKey";
    public static final String Money_code = "Money_codeKey";
    public static final String Money_remark = "Money_remarkKey";
    public static final String Money_hint1 = "Money_hint1Key";
    public static final String Money_hint2 = "Money_hint2Key";
    public static final String Money_hint3 = "Money_hint3Key";
    public static final String Money_icon = "Money_iconKey";

    public static final String Ins_name = "Ins_nameKey";
    public static final String Ins_code = "Ins_codeKey";
    public static final String Ins_remark = "Ins_remarkKey";
    public static final String Ins_hint1 = "Ins_hint1Key";
    public static final String Ins_hint2 = "Ins_hint2Key";
    public static final String Ins_hint3 = "Ins_hint3Key";
    public static final String Ins_icon = "Ins_iconKey";

    public static final String Gas_name = "Gas_nameKey";
    public static final String Gas_code = "Gas_codeKey";
    public static final String Gas_remark = "Gas_remarkKey";
    public static final String Gas_hint1 = "Gas_hint1Key";
    public static final String Gas_hint2 = "Gas_hint2Key";
    public static final String Gas_hint3 = "Gas_hint3Key";
    public static final String Gas_icon = "Gas_iconKey";

    public static final String Water_name = "Water_nameKey";
    public static final String Water_code = "Water_codeKey";
    public static final String Water_remark = "Water_remarkKey";
    public static final String Water_hint1 = "Water_hint1Key";
    public static final String Water_hint2 = "Water_hint2Key";
    public static final String Water_hint3 = "Water_hint3Key";
    public static final String Water_icon = "Water_iconKey";

    public static final String r_Status = "r_StatusKey";
    public static final String r_Detail = "r_DetailKey";
    public static final String r_Balance = "r_BalanceKey";
    public static final String position_id = "position_idKey";
    public static final String Operator_code = "Operator_codeKey";
    public static final String Circle_code = "Circle_codeKey";

    public static final String Pager_Position = "Pager_PositionKey";
    public static final String Customer_Number = "Customer_NumberKey";
    public static final String Customer_Name = "Customer_NameKey";
    public static final String Bill_DueDate = "Bill_DueDateKey";
    public static final String Bill_Date = "Bill_DateKey";
    public static final String Bill_Number = "Bill_NumberKey";
    public static final String Valid_Bill = "Valid_BillKey";
    public static final String Partial_Pay = "Partial_PayKey";
    public static final String Bill_Unit = "Bill_UnitKey";
    public static final String Bill_cycle = "Bill_cycleKey";
    public static final String Amount = "AmountKey";
    public static final String Sub_division = "Sub_divisionKey";
    public static final String U_id = "U_idKey";
    public static final String Tab = "TabKey";
    public static final String TID_Number = "TID_NumberKey";
    public static final String Landline_Number = "Landline_NumberKey";
    public static final String Service_Type = "Service_TypeKey";
    public static final String Service_No = "Service_NoKey";

    public static final String Money_Valid = "Money_ValidKey";
    public static final String Money_Tran = "Money_TranKey";
    public static final String u_id = "u_idKey";
    public static final String Sender_mobile = "Sender_mobileKey";
}
