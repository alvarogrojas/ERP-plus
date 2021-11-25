package com.ndl.erp.fe.core;

import java.util.HashMap;

public class OptionSet {

    public static final String INPUT_FILE = "input";

    public static final String OUTPUT_FILE = "output";

    public static final String PREFIX_FILE = "signed";
    public static final String PASSWORD = "password";
    public static final String CERTIFICATE = "certificate";
    public static final String NAMESPACE = "namespace";
    public static final String BASE_NAME = "baseName";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PROCESS_ID = "processId";

    private HashMap<String,String> data = new HashMap<String, String>();

    public OptionSet() {
    }

    public String valueOf(String key) {
        return data.get(key);
    }

    public void setValue(String key, String value) {
        data.put(key,value);
    }
}
