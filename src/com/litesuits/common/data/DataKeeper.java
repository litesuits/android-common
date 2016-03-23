/*
 * Copyright (C) 2013 litesuits.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.litesuits.common.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.litesuits.android.log.Log;
import com.litesuits.common.data.cipher.Cipher;
import com.litesuits.common.utils.ByteUtil;
import com.litesuits.common.utils.HexUtil;

/**
 * @author MaTianyu
 * @date 14-7-10
 */
public class DataKeeper {
    private SharedPreferences sp;
    public static final  String KEY_PK_HOME = "msg_pk_home";
    public static final  String KEY_PK_NEW  = "msg_pk_new";
    private static final String TAG         = DataKeeper.class.getSimpleName();

    public DataKeeper(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * *************** get ******************
     */

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public Object get(String key) {
        return get(key, (Cipher) null);
    }

    public Object get(String key, Cipher cipher) {
        try {
            String hex = get(key, (String) null);
            if (hex == null) return null;
            byte[] bytes = HexUtil.decodeHex(hex.toCharArray());
            if (cipher != null) bytes = cipher.decrypt(bytes);
            Object obj = ByteUtil.byteToObject(bytes);
            Log.i(TAG, key + " get: " + obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *************** put ******************
     */
    public void put(String key, Object ser) {
        put(key, ser, null);
    }

    public void put(String key, Object ser, Cipher cipher) {
        try {
            Log.i(TAG, key + " put: " + ser);
            if (ser == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = ByteUtil.objectToByte(ser);
                if (cipher != null) bytes = cipher.encrypt(bytes);
                put(key, HexUtil.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

}
