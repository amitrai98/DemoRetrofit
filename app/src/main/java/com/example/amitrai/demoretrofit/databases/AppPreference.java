package com.example.amitrai.demoretrofit.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.amitrai.demoretrofit.models.Data;
import com.example.amitrai.demoretrofit.models.RememberMe;


public class AppPreference {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPrefUtils;
    private SharedPreferences.Editor mEditorUtils;
    private  Context _context;
    private int mPRIVATE_MODE = 0;
    static AppPreference sessionObj;
    private final String mPREF_NAME = "RETROFIT_DEMO";

    private final String API_KEY = "API_KEY";
    private final String NAME = "NAME";
    private final String EMAIL = "EMAIL";
    private final String CREATED_AT = "CREATED_AT";
    private final String mPREF_NAMEUTILS = "RETROFIT_DEMO_PREF_UTILS";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";
    private static final String KEY_USERID = "id";



//    private static final String KEY_USERALREADY = "userAlready";
//    private static final String KEY_ACESSTOKEN = "user_id";
//    private static final String KEY_USER_TYPE = "is_driver";
//    private static final String KEY_USER_AVTAR = "user_image";
//    private static final String KEY_USER_NAME = "user_name";
//    private static final String KEY_USER_EMAIL = "user_email";
//    private static final String KEY_FB_LOGIN = "fb_login";

//    private static final String KEY_CLIENT_TOKEN = "payment_token";
//    private static final String KEY_SAVE_CARD = "save_card";
//    private static final String KEY_HAVING_CURRENTTRIP="havingCurrentTRip";
//    private static final String KEY_GCM_REG_TOKEN = "gcm_reg_token";
//    private static final String KEY_IS_REGSITERED_GCM = "is_gcm_reg";
//    private static final String KEY_IS_GUEST = "is_guest";
//    private static final String KEY_FILLED_DATA = "filled_data";
//    private static final String KEY_BUS_ID = "bus_id";
//    private static  final String KEY_TRIP_ID="trip_id";
//    private static final String KEY_STOP_ID = "stop_id";
//    private static final String KEY_TRIP_STATUS = "trip_status";
//
//    private static final String KEY_SAVED_DATE_FOR_ONE_WEEK = "saved_date_one_week";
//
//
//    private static final String KEY_USER_MOBILE = "mobile";
//    private static final String KEY_LOAD_CURRENT_TRIP = "load_trip";
//    private static final String KEY_Is_CURRENT_TRIP_STARTED = "is_trip_started";
//    public static final String KEY_STOP_POSITION = "key_stop_pos";
//    public static final String KEY_IS_DRIVER_ON_ROUTE_SCREEN = "is_on_route_screen";
//    public static final String KEY_IS_DRIVER_ON_UPCOMINGROUTE_SCREEN = "is_on_upcoming_route_screen";
//
//    public static final String KEY_NOTIFYDATA = "notification_data";
//    private String KEY_CURRENT_TRIP_ROUTE_ID="tripRouteId";
//
//    public static final String KEY_PAUSE_RESUME = "pause_resume";
//    private static final String KEY_LATITUDE = "latitude";
//    private static final String KEY_LONGITUDE = "longitude";
//
//    private static final String KEY_DUMMY_BUS_DATA = "dummy_bus_data";
//    private static final String KEY_LOCATION_POINT1 = "location_point1";
//    private static final String KEY_LOCATION_POINT2 = "location_point2";
//    private String KEY_MOVE_TO_UPCOMING="move_to_upcoming";
//
//    private String KEY_REFERRAL_SUBJECT="referral_subject";
//    private String KEY_REFERRAL_URL="referral_Url";
//    private String KEY_REFERRAL_CODE="referral_code";
//    private String KEY_REFERRAL_CODE_TYPE="referral_code_type";
//    private String KEY_REFERRAL_CREDIT="referral_credit";
//
//
//
//    private String UINIQUEID="unique";







    public AppPreference(Context context) {
        this._context = context;
        mPref = _context.getSharedPreferences(mPREF_NAME, mPRIVATE_MODE);
        mEditor = mPref.edit();
        mPrefUtils = _context.getSharedPreferences(mPREF_NAMEUTILS, mPRIVATE_MODE);
        mEditorUtils=mPrefUtils.edit();
    }

    public static AppPreference newInstance(Context context) {
        if (sessionObj == null)
            sessionObj = new AppPreference(context);
        return sessionObj;
    }

    public void setUserID(String id) {

        mEditor.putString(EMAIL, id);
        mEditor.commit();
    }

    public String getUserID() {
        return mPref.getString(EMAIL, "");
    }

    public String getAPI_KEY() {
        return mPref.getString(API_KEY, "");
    }

    public void setUserLoginTrue(Data userLogin) {
        mEditor.putString(API_KEY, userLogin.getApiKey());
        mEditor.putString(NAME, userLogin.getName());
        mEditor.putString(EMAIL, userLogin.getEmail());
        mEditor.putString(CREATED_AT, userLogin.getCreatedAt());
        mEditor.commit();

    }

    public void setRememberme(RememberMe rememberMe){
        mEditor.putString(USERNAME, rememberMe.getUsername());
        mEditor.putString(PASSWORD, rememberMe.getPassword());
        mEditor.commit();
    }

    public RememberMe getRemberme(){
        return new RememberMe(mPref.getString(USERNAME, ""),
                mPref.getString(PASSWORD, ""));
    }

    public Data getLoggedInUser() {
        Data login = new Data();
        login.setApiKey(mPref.getString(API_KEY, ""));
        login.setCreatedAt(mPref.getString(CREATED_AT, ""));
        login.setEmail(mPref.getString(EMAIL, ""));
        login.setName(mPref.getString(NAME, ""));

        return login;
    }

    public void logout(){
        mEditor.putString(API_KEY, "");
        mEditor.putString(NAME, "");
        mEditor.putString(EMAIL, "");
        mEditor.putString(CREATED_AT, "");
        mEditor.commit();
    }

//    public void setpaymentToken(String clienttoken) {
//
//        mEditor.putString(KEY_CLIENT_TOKEN, clienttoken).commit();
//    }
//    public String getUserType() {
//        return mPref.getString(KEY_USER_TYPE, null);
//    }
//
//    public String getPaymentToken() {
//        return mPref.getString(KEY_CLIENT_TOKEN, "");
//    }
//
//    public String getUserEmail() {
//        return mPref.getString(KEY_USER_EMAIL, "");
//    }
//
//    public boolean isUserLogin() {
//        if (!mPref.getString(KEY_ACESSTOKEN, "").isEmpty()) {
//            return true;
//        }
//        return false;
//    }
//
//    public void setLoginFromFb(boolean loginFromFb) {
//        mEditor.putBoolean(KEY_FB_LOGIN, loginFromFb);
//        mEditor.commit();
//    }
//
//    public boolean isFbLogin() {
//        return mPref.getBoolean(KEY_FB_LOGIN, false);
//    }
//
//    public void clearSession() {
//        mEditor.clear().commit();
//    }
//
//    public void setUserAvtar(String id) {
//
//        mEditor.putString(KEY_USER_AVTAR, id);
//        mEditor.commit();
//    }
//
//    public void setUserFullName(String id) {
//
//        mEditor.putString(KEY_USER_NAME, id);
//        mEditor.commit();
//    }
//
//    /**
//     * boolean to save or not the card
//     * @param b
//     */
//    public void setSaveCard(boolean b) {
//        mEditor.putBoolean(KEY_SAVE_CARD, b).commit();
//    }
//
//    /**
//     * value of saved card
//     * @return
//     */
//    public boolean isSavedCard() {
//        return mPref.getBoolean(KEY_SAVE_CARD, false);
//    }
//
//
//    public void setGcmRegsitration(String gcm_token, boolean isregisitered) {
//        mEditor.putString(KEY_GCM_REG_TOKEN, gcm_token);
//        mEditor.putBoolean(KEY_IS_REGSITERED_GCM, isregisitered);
//        mEditor.commit();
//    }
//
//    public String getGcmregistrationId() {
//        return mPref.getString(KEY_GCM_REG_TOKEN, "");
//    }
//
//    public boolean isRegisteredtoGCM() {
//        return mPref.getBoolean(KEY_IS_REGSITERED_GCM, false);
//    }
//
//    public void setCurrentTripStatus(boolean b){
//        mEditor.putBoolean(KEY_HAVING_CURRENTTRIP,b).commit();
//    }
//
//    public boolean getCurrentTripStatus(){
//        return mPref.getBoolean(KEY_HAVING_CURRENTTRIP,false);
//    }
//
//    public void setTimeDifference(long timeDifference){
//        mEditor.putLong("timediff", timeDifference).commit();
//    }
//
//
//    public void setIsCurrenttripStarted(boolean b){
//        mEditor.putBoolean(KEY_Is_CURRENT_TRIP_STARTED, b).commit();
//    }
//    public boolean getIsCuurentTRipStarted(){
//        return  mPref.getBoolean(KEY_Is_CURRENT_TRIP_STARTED,false);
//    }
//
//
//   /* public void setLoadCurrenttrip(boolean b){
//        mEditor.putBoolean(KEY_LOAD_CURRENT_TRIP,b).commit();
//    }*/
//
//
//    /**
//     *
//     * @param pos current stop position
//     */
//    public void setTripStopPos(int pos){
//        mEditor.putInt(KEY_STOP_POSITION, pos).commit();
//    }
//
//    /**
//     *
//     * @return current stop postion of current trip driver side
//     */
//    public int getTripStopPos(){
//        return mPref.getInt(KEY_STOP_POSITION, -1);//
//    }
//
//
//   /* public boolean getIsLoadCurrentTrip(){
//        return  mPref.getBoolean(KEY_LOAD_CURRENT_TRIP, false);
//    }*/
//    public long getTimeDifference(){
//        return mPref.getLong("timediff", 0);
//    }
//
//    public void setpassedMillis(long pastmilis){
//        mEditor.putLong("pastMills",pastmilis).commit();
//    }
//
//    public long getPastMiliis(){
//        return mPref.getLong("pastMills", 0);
//    }
//
//
//
//    public void setGuestUser(boolean token) {
//        mEditor.putBoolean(KEY_IS_GUEST, token);
//        mEditor.commit();
//    }
//
//    public boolean isGuestUser() {
//        return mPref.getBoolean(KEY_IS_GUEST, false);
//    }
//
//    public void setFilledData(String data) {
//        mEditor.putString(KEY_FILLED_DATA, data);
//        mEditor.commit();
//    }
//
//    public String getFilledData() {
//        return mPref.getString(KEY_FILLED_DATA, null);
//    }
//
//    /**
//     *
//     * @param busId
//     */
//    public void setBusId(String busId){
//       mEditor.putString(KEY_BUS_ID,busId).commit();
//    }
//
//    public String getbusId(){
//       return mPref.getString(KEY_BUS_ID,"");
//    }
//
//
//    public void settripId(String tripId){
//        mEditor.putString(KEY_TRIP_ID,tripId).commit();
//    }
//
//    public String gettripId(){
//        return mPref.getString(KEY_TRIP_ID,"");
//    }
//
//
//
//    public void setStopId(String stopId){
//        mEditor.putString(KEY_STOP_ID,stopId).commit();
//    }
//
//    public String getStopId(){
//        return mPref.getString(KEY_STOP_ID,"");
//    }
//
//    public void setTripStatus(String status){
//        mEditor.putString(KEY_TRIP_STATUS,status).commit();
//    }
//
//    public  String getTripStatus(){
//        return mPref.getString(KEY_TRIP_STATUS,Constants.INTENTS.TRIP_STATUS_HIDE);
//    }
//
//
//    public void setLoginResponseString(String responseString){
//
//        mEditor.putString("response",responseString).commit();
//    }
//
//    public String getLoginResponseString(){
//        return  mPref.getString("response","");
//    }
//
//
//    public void setCountryString(String country){
//        mEditorUtils.putString("responseCountry", country).commit();
//    }
//
//    public String getCountryCResponse(){
//        return  mPrefUtils.getString("responseCountry", "");
//    }
//
//
//
//    public void setScreenPause(boolean b){
//
//        mEditor.putBoolean(KEY_IS_DRIVER_ON_ROUTE_SCREEN, b).commit();
//    }
//    public boolean getScreenPause(){
//
//       return mPref.getBoolean(KEY_IS_DRIVER_ON_ROUTE_SCREEN, false);
//    }
//
//
//    public void setDriverUpcomingRoutePause(boolean b){
//
//        mEditor.putBoolean(KEY_IS_DRIVER_ON_UPCOMINGROUTE_SCREEN,b).commit();
//    }
//    public boolean getDriverUpcomingRoutePause(){
//
//        return mPref.getBoolean(KEY_IS_DRIVER_ON_UPCOMINGROUTE_SCREEN,true);
//    }
//
//
//    public void refreshINResume(boolean b,String key){
//
//        mEditor.putBoolean(UINIQUEID+key,b).commit();
//    }
//
//    /**
//     *
//     * @param refreshtype  values 1/0
//     */
//    public void refreshINResume(int refreshtype){
//        mEditor.putInt(Constants.REFRESH_TYPE, refreshtype).commit();
//    }
//
//    /**
//     *
//     * @param key is uniquerouteId
//     * @return
//     */
//    public boolean shouldRefreshINResume(String key){
//        return mPref.getBoolean(UINIQUEID+key,false);
//    }
//
//    public int shouldRefreshINResume(){
//        return mPref.getInt(Constants.REFRESH_TYPE, 0);
//    }
//
//    /**
//     * accept 0 and 1  , 1 for pause 2 for resume
//     * @param stauts
//     */
//    public void setTripPauseResume(int stauts){
//        mEditor.putInt(KEY_PAUSE_RESUME,stauts).commit();
//    }
//
//    /**
//     *
//     * @return 1 for pause return 2 for resume ,0 nothing
//     */
//    public int getPauseResume(){
//        return mPref.getInt(KEY_PAUSE_RESUME,Constants.INTENTS.TRIP_STATUS_INTIAL);
//    }
//
//    /**
//     * Set the trip route id for driver route
//     * @param tripRouteId
//     */
//    public void setCurretTripRouteId(String tripRouteId){
//
//        mEditor.putString(KEY_CURRENT_TRIP_ROUTE_ID,tripRouteId).commit();
//    }
//
//    /**
//     * get Current trip routeID for driver
//     * @return
//     */
//    public String getCurrentTRipRouteId(){
//
//      return   mPref.getString(KEY_CURRENT_TRIP_ROUTE_ID,"");
//    }
//
//
//    public void setPreviousLatLng(String lat,String lng){
//        mEditor.putString(KEY_LATITUDE,lat);
//        mEditor.putString(KEY_LONGITUDE,lng);
//        mEditor.commit();
//    }
//    public double[] getPreviousLatLng(){
//        double[] latlng =new double[2];
//        try{
//            latlng[0]=Double.parseDouble(mPref.getString(KEY_LATITUDE,"0.0"));
//            latlng[1]=Double.parseDouble(mPref.getString(KEY_LONGITUDE,"0.0"));
//        }catch (Exception e){
//            latlng[0]=0.0;
//            latlng[1]=0.0;
//
//        }
//        return latlng;
//    }
//
//    public void setNotifyData(String data){
//        mEditor.putString(KEY_NOTIFYDATA,data).commit();
//
//    }
//    public String getNotifyData(){
//        return  mPref.getString(KEY_NOTIFYDATA,"");
//    }
//
//
//    /**
//     * set the json string in prefrence
//     * @param data json string of dummy bus
//     */
//    public void setDummyBusData(String data){
//        mEditorUtils.putString(KEY_DUMMY_BUS_DATA,data).commit();
//    }
//
//    /**
//     *
//     * @return the json string of dummybus
//     */
//    public String getDummyBusData(){
//
//       return mPrefUtils.getString(KEY_DUMMY_BUS_DATA,"");
//    }
//
//
//    public void setMovetoUpocming(boolean b){
//        mEditor.putBoolean(KEY_MOVE_TO_UPCOMING,b).commit();
//    }
//
//    public boolean getIsMoveTOupcoming(){
//        return mPref.getBoolean(KEY_MOVE_TO_UPCOMING,false);
//    }
//
//
//    public void setReferralDetails(Referral referralDetails){
//        mEditor.putString(KEY_REFERRAL_CODE,referralDetails.getReferralCode());
//        mEditor.putString(KEY_REFERRAL_CODE_TYPE,referralDetails.getCodeType());
//        mEditor.putString(KEY_REFERRAL_URL,referralDetails.getReferralUrl());
//        mEditor.putString(KEY_REFERRAL_CREDIT,referralDetails.getCreditAmount());
//        mEditor.commit();
//
//    }
//
//    public  Referral getReferralDetails(){
//        Referral referral=new Referral();
//        referral.setReferralUrl(mPref.getString(KEY_REFERRAL_URL,""));
//        referral.setReferralCode(mPref.getString(KEY_REFERRAL_CODE,""));
//        referral.setCodeType(mPref.getString(KEY_REFERRAL_CODE_TYPE,""));
//        referral.setCreditAmount(mPref.getString(KEY_REFERRAL_CREDIT,""));
//        return referral;
//    }
//
//    public void setKeySavedDateForOneWeek(String date){
//        mEditor.putString(KEY_SAVED_DATE_FOR_ONE_WEEK,date).commit();
//    }
//
//
//    public boolean compareWeekDifference(String todaysDateString){
//        DateUtils dateUtils=DateUtils.getInstance();
//        String dateAfter7days=dateUtils.getCalculatedDate(mPref.getString(KEY_SAVED_DATE_FOR_ONE_WEEK,""),DateUtils.DATE_FORMAT,7);
//        Date oneWeekAfterSavedDate=dateUtils.getDateFromString(dateAfter7days,DateUtils.DATE_FORMAT);
//        Date todaysDate=dateUtils.getDateFromString(todaysDateString,DateUtils.DATE_FORMAT);
//        if(todaysDate.after(oneWeekAfterSavedDate)){
//            return true;
//        }
//        return false;
//
//    }

}
