package com.example.luma.data;

import java.lang.reflect.Field;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public final class FontsOverride {

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(
                    context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class
                    .getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.e("Can not set custom font " + customFontFileNameInAssets
                    + " instead of " + defaultFontNameToOverride, null);
        }
    }
}
