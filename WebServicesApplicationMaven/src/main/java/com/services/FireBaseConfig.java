/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author mohamad
 */

public class FireBaseConfig {

    private FirebaseDatabase firebaseDatabase;

    public static void main(String[] args) throws IOException {

         new FireBaseConfig().saveUsingPush();
    }

    /**
     * initialize firebase.
     */
    private void initFirebase() throws IOException {
        try {
            FileInputStream serviceAccount = new FileInputStream("C:/Users/mohamad/Desktop/intpub/cnam/vc/WebServicesApplicationMaven/stockproject-ef626-firebase-adminsdk-hh1l4-c5a1324cd7.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://stockproject-ef626.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
             firebaseDatabase = FirebaseDatabase.getInstance();
           // firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        } catch (FileNotFoundException ex) {
            System.out.println("dfssdfsdfsdf");
            ex.printStackTrace();
        }
    }

    /**
     * Save item object in Firebase.
     *
     * @param item
     */
    private void saveUsingPush() throws IOException {
        
            initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");

            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("users");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             * wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            final CountDownLatch countDownLatch = new CountDownLatch(1);

            /**
             * push() Add to a list of data in the database. Every time you push
             * a new node onto a list, your database generates a unique key,
             * like items/unique-item-id/data
             */
            childReference.push().setValue("dsdsd", new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    System.out.println("Record saved!");
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                }
            });
            try {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

