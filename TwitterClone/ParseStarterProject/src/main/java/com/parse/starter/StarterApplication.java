/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  // http://34.201.249.157:80/apps/
  // username: user
  // password: Fljik8n5j1Lc

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("cba969f97d528c42cd09597e42742b25651ad271")
            .clientKey("86431f0ae2dda1ec757581cf4c8b152368f106a5")
            .server("http://34.201.249.157:80/parse/") // remember to add '/' at the end of parse
            .build()
    );

    ParseUser.logOut();

//    ParseUser.enableAutomaticUser();
    // the above line must be commented out because it makes the user log in every time we run this app

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
