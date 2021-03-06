/*
 * Copyright (C) 2018 Eton Otieno Oboch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edoubletech.journalapp;

import com.edoubletech.journalapp.injection.ApplicationComponent;
import com.edoubletech.journalapp.injection.ApplicationModule;
import com.edoubletech.journalapp.injection.DaggerApplicationComponent;
import com.google.firebase.FirebaseApp;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyJournal extends MultiDexApplication {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        JournalSettings.initialize(this);

        FirebaseApp.initializeApp(this);

        appComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
