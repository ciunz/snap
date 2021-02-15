package sen.com.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sen.com.abstraction.strategies.JSONParserStrategy
import sen.com.abstraction.strategies.ImageLoaderStrategy
import sen.com.core.PrefKey
import sen.com.core.preferences.SnapPreference
import sen.com.core.utils.imageLoader.GlideImageLoaderStrategy
import sen.com.core.utils.jsonParser.GSONParserStrategy
import javax.inject.Singleton

/**
 * Created by korneliussendy on 29/09/20,
 * at 01.15.
 * Snap
 */
@Module
@InstallIn(SingletonComponent::class)
object AppBaseModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PrefKey.MAIN_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSnapPreference(@ApplicationContext context: Context): SnapPreference =
        SnapPreference.instance.apply {
            init((context.getSharedPreferences(PrefKey.MAIN_KEY, Context.MODE_PRIVATE)))
        }

    @Provides
    fun provideJSONStrategy(): JSONParserStrategy = GSONParserStrategy

    @Provides
    fun provideImageLoaderStrategy(): ImageLoaderStrategy = GlideImageLoaderStrategy

}