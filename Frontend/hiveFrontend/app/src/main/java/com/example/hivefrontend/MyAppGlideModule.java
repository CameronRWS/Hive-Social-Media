package com.example.hivefrontend;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.storage.StorageReference;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.io.InputStream;

/**
 * This class is for handeling photos. MyAppGlideModule allows us to use the Glide
 * Module, which is an image loader library for Android.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    /**
     * This function registers components of a glide singleton immediately after
     * initialization of a glide instance. This function takes an application context,
     * the glide singleton to be registered, and a registry to be used to register the
     * compontents.
     * @param context
     *        the application context
     * @param glide
     *        the glide singleton to be registered
     * @param registry
     *        the registry to be used to register components
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}
