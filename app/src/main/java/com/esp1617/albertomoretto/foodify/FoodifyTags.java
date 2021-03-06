package com.esp1617.albertomoretto.foodify;

/**
 * Created by leonardo on 04/05/17.
 */
//Classe che contiene le stringhe TAG utilizzate per associare valori ad identificatori univoci.
public class FoodifyTags {

    //Identificatore per l'ID della notifica
    public static final String ORDER_NOTIFICATION = "com.esp1617.albertomoretto.foodify_notification_order";

    //Identificatori per cibi e per valore dell'ordine
    public static final String BILL_VALUE = "com.esp1617.albertomoretto.foodify_bill_value";
    public static final String SELECT_FOOD_CALLER = "com.esp1617.albertomoretto.foodify_select_food_caller";
    public static final String HAMBURGER = "com.esp1617.albertomoretto.foodify_hamburger";
    public static final String HOT_DOG = "com.esp1617.albertomoretto.foodify_hot_dog";
    public static final String FRIES = "com.esp1617.albertomoretto.foodify_fries";
    public static final String DRINK = "com.esp1617.albertomoretto.foodify_drink";
    public static final String DESSERT = "com.esp1617.albertomoretto.foodify_dessert";

    //Identificatori per Intent
    public static String CUSTOM_INTENT_PAYMENT = "com.esp1617.albertomoretto.custom_intent_payment";
    public static int BROADCAST_PAYMENT_REQUEST_CODE = 12345;

    //Identificatori per extra da applicare agli Intent
    public static String EXTRA_EXIT_APP = "com.esp1617.albertomoretto.extra_exit_app";
    public static String EXTRA_PRICE_SETTED = "com.esp1617.albertomoretto.price_setted";
    public static String EXTRA_ORDER_SETTED = "com.esp1617.albertomoretto.order_setted";
    public static String EXTRA_PRICE_ORDER = "com.esp1617.albertomoretto.price_order";
    public static String EXTRA_ITEMS_ORDER = "com.esp1617.albertomoretto.items_order";
    public static String EXTRA_SIZE_ORDER = "com.esp1617.albertomoretto.size_order";
    public static String EXTRA_NOTIFY_ID_ORDER = "com.esp1617.albertomoretto.notify_id_order";

    public static final int POPUP_CONFIRM_REQUEST = 1;
    public static final int ACCOUNT_CHARGING = 1;

    //Identificatori per debug/Log
    public static final String FOOD_ACTIVITY_TAG ="FoodActivity";
    public static String ACTION_START = "TimerStart";

    //Identificatori per gestione SharedPreferences
    public static final String SHARED_PREF_ORDER_READY = "com.esp1617.albertomoretto.foodify_shared_pref_order_ready";
    public static final String SHARED_BILL_TO_PAY = "com.esp1617.albertomoretto.foodify_shared_bill_to_pay";
    public static final String SHARED_ORDERS_LIST_READY = "com.esp1617.albertomoretto.foodify_shared_orders_list_ready";

}
