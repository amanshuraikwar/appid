package io.github.amanshuraikwar.appid.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.amanshuraikwar.appid.db.AppIdDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideAppIdDb(
        @ApplicationContext context: Context,
    ): AppIdDb {
        return AppIdDb(
            AndroidSqliteDriver(
                AppIdDb.Schema,
                context,
                "appid.db",
                // to improve insert performance: https://stackoverflow.com/a/65426659
                callback = object : AndroidSqliteDriver.Callback(AppIdDb.Schema) {
                    override fun onConfigure(db: SupportSQLiteDatabase) {
                        super.onConfigure(db)
                        setPragma(db, "JOURNAL_MODE = WAL")
                        setPragma(db, "SYNCHRONOUS = 2")
                    }

                    private fun setPragma( db: SupportSQLiteDatabase, pragma: String) {
                        val cursor = db.query("PRAGMA $pragma")
                        cursor.moveToFirst()
                        cursor.close()
                    }
                }
            )
        )
    }
}