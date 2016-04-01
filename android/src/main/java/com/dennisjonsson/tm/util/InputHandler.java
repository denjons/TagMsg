package com.dennisjonsson.tm.util;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dennis on 2016-03-27.
 */
public class InputHandler {


    private static final String LOG_TAG = "InputHandler";
    public ArrayList<String> hanldeTags(String input){
        ArrayList<String> tags = new ArrayList<>();
        Scanner scanner = new Scanner(input);
        while(scanner.hasNext()){
            String str = scanner.nextLine().trim();
            if(str.length() > 0){
                tags.add(str);
            }
        }
        return tags;
    }
}
