/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // put things into parse server
//      ParseObject score = new ParseObject("Score"); // the name of the class is "Score"
//      score.put("username", "Sean");
//      score.put("score", 80);
//      score.saveInBackground(new SaveCallback() {
//          @Override
//          public void done(ParseException e) {
//              if (e == null) { // everything is ok
//                  Log.i("Success", "we saved the score");
//              } else { // sth has gone wrong
//                  e.printStackTrace();
//              }
//          }
//      });

      // get things from parse server
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      query.getInBackground("nTjwKkONfT", new GetCallback<ParseObject>() { // id of Nick
//          @Override
//          public void done(ParseObject parseObject, ParseException e) {
//              if (e == null && parseObject != null) {
//
//                  // update an object
//                  parseObject.put("score", 95);
//                  parseObject.saveInBackground();
//
//                  String username = parseObject.getString("username");
//                  int score = parseObject.getInt("score");
//                  Log.i("username", username);
//                  Log.i("score", String.valueOf(score));
//              }
//          }
//      });

      // grab all things in a particular class
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> list, ParseException e) { // notice that this time we get a list of ParseObjects
//              if (e == null ) {
//                  if (list.size() > 0) {
//                      for (ParseObject object : list) {
//                          Log.i("username", object.getString("username"));
//                          Log.i("score", String.valueOf(object.getInt("score")));
//                      }
//                  }
//              }
//          }
//      });

      // search for things
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      query.whereEqualTo("username", "Sean");
//      query.setLimit(1); // get one result back if there are multiple objects with username "Sean"
//      query.findInBackground(new FindCallback<ParseObject>() { // this time we only get "Sean"
//          @Override
//          public void done(List<ParseObject> list, ParseException e) { // notice that this time we get a list of ParseObjects
//              if (e == null ) {
//                  if (list.size() > 0) {
//                      for (ParseObject object : list) {
//                          Log.i("username", object.getString("username"));
//                          Log.i("score", String.valueOf(object.getInt("score")));
//                      }
//                  }
//              }
//          }
//      });

      // add 20 to whoever with score > 50
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      query.whereGreaterThan("score", 50);
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> list, ParseException e) { // notice that this time we get a list of ParseObjects
//              if (e == null ) {
//                  if (list.size() > 0) {
//                      for (ParseObject object : list) {
//                          object.put("score", object.getInt("score") + 20);
//                          object.saveInBackground();
//                          Log.i("score", String.valueOf(object.getInt("score")));
//                      }
//                  }
//              }
//          }
//      });

      // Parse User
      // sign up a user
//      ParseUser user = new ParseUser();
//      user.setUsername("nick");
//      user.setPassword("myPass");
//      user.signUpInBackground(new SignUpCallback() {
//          @Override
//          public void done(ParseException e) {
//              if (e == null) {
//                  // OK
//                  Log.i("Sign Up OK!", "We did it");
//              } else {
//                  e.printStackTrace();
//              }
//          }
//      });

      // login a user
//      ParseUser.logInInBackground("nick", "myPass", new LogInCallback() {
//          @Override
//          public void done(ParseUser parseUser, ParseException e) {
//              if (parseUser != null) { // success
//                  Log.i("Success", "We logged in.");
//              } else {
//                  e.printStackTrace();
//              }
//          }
//      });

      ParseUser.logOut();

      // check if there is a currently logged in user
      if (ParseUser.getCurrentUser() != null) { // have a user logged in
          Log.i("Signed In", ParseUser.getCurrentUser().getUsername());
      } else {
          Log.i("Not Luck", "Not signed in.");
      }

      // log a user out
//      ParseUser.logOut();

      ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}