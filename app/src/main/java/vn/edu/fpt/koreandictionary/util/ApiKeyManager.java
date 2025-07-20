package vn.edu.fpt.koreandictionary.util;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKeyManager {
    private static final String TAG = "ApiKeyManager";
    private static String cachedApiKey = null;
    
    public static String getApiKey(Context context) {
        if (cachedApiKey != null) {
            return cachedApiKey;
        }
        
        // Try to get from BuildConfig first (if available)
        try {
            // Use reflection to access BuildConfig if it exists
            Class<?> buildConfigClass = Class.forName(context.getPackageName() + ".BuildConfig");
            java.lang.reflect.Field apiKeyField = buildConfigClass.getField("KR_DICT_API_KEY");
            cachedApiKey = (String) apiKeyField.get(null);
            if (cachedApiKey != null && !cachedApiKey.isEmpty()) {
                return cachedApiKey;
            }
        } catch (Exception e) {
            Log.d(TAG, "BuildConfig not available, trying local.properties");
        }
        
        // Fallback to reading from local.properties
        try {
            File localPropertiesFile = new File(context.getApplicationInfo().sourceDir).getParentFile().getParentFile();
            File propertiesFile = new File(localPropertiesFile, "local.properties");
            
            if (propertiesFile.exists()) {
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream(propertiesFile);
                properties.load(fis);
                fis.close();
                
                cachedApiKey = properties.getProperty("KR_DICT_API_KEY", "");
                if (!cachedApiKey.isEmpty()) {
                    return cachedApiKey;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to read API key from local.properties", e);
        }
        
        // If all else fails, try reading from assets (for development)
        try {
            Properties properties = new Properties();
            properties.load(context.getAssets().open("local.properties"));
            cachedApiKey = properties.getProperty("KR_DICT_API_KEY", "");
        } catch (Exception e) {
            Log.e(TAG, "Failed to read API key from assets", e);
        }
        
        return cachedApiKey != null ? cachedApiKey : "";
    }
    
    public static void clearCache() {
        cachedApiKey = null;
    }
} 