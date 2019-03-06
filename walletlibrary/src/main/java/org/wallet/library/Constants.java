package org.wallet.library;

public class Constants {

    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    public interface REQUEST_TYPE_VALUE {
        String TYPE_LOGIN = "TYPE_LOGIN";
        String TYPE_PAY = "TYPE_PAY";
    }

    public static final String RESPONSE_STATE = "RESPONSE_STATE";
    public interface RESPONSE_STATE_VALUE {
        int RESPONSE_ALLOW = 200;
        int RESPONSE_DENY = 400;
    }

    public interface INTENT_EXTRA_KEY {
        String META_EXTRA = "META_EXTRA";
        String PACKAGE_NAME = "PACKAGE_NAME_EXTRA";
        String ACTIVITY_CLASS = "ACTIVITY_CLASS_EXTRA";

    }

    public interface BROAD_ACTION {
        String CLIENT_TO_SERVER = "org.elastos.client.request";
        String SERVER_TO_CLIENT = "org.elastos.server.response";
    }

}
