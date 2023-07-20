package com.devheat.billbot;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The class that stores data to be stored locally
 */
public class User{
    public static String name;
    public static String address;
    public static String phone;
    public static String email;
    public static String GSTIN;
    public static String invoice_num;

    /**
     * Opens file writes to it then closes it
     */
    public static String AddUserData(String json_string, Context context){
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("user.json", Context.MODE_PRIVATE));
                outputStreamWriter.write(json_string);
                outputStreamWriter.close();
                return "Success";
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
                return "failed";

        }
    }

    /**
     * Opens file reads it to a buffer and returns the buffer as a string
     */
    public static String LoadUserData(Context context){
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("user.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
