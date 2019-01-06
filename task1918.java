package com.javarush.task.task19.task1918;

/* 
Знакомство с тегами
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) throws IOException {
        args = new String[]{"a"};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileReader fr = new FileReader(br.readLine());
        br.close();
        br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder("");
        while (br.ready())
            sb.append(br.readLine());                       //StringBuilder object includes all chars in the given file.
        br.close();
    //A key stores the beginning index, a value stores the ending index or -1 when the ending index hasn't been found.
        Map <Integer, Integer> tagIndices = new TreeMap<>();
        final String fileString = sb.toString().replaceAll("[\\n\\r]", "");
        Matcher m = Pattern.compile("(<\\s*/?\\s*" + args[0] + ".*?>)").matcher(fileString);
        while (m.find()){ //if an opening OR closing tag has been found we go inside while loop
            if (m.group().matches("<\\s*" + args[0] + ".*?>")) //if found group is an opening tag then
                tagIndices.put(m.start(), null);                 //we create new pair: {found index, null}
            else {                                               // if found group is a closing tag then
                Integer openingTag = null;                       // we search for last beginning index, which has null value.
                for (Integer i : tagIndices.keySet())            //here we're going over all found beginning indices
                    if (tagIndices.get(i) == null)
                        openingTag = i;                          //and saving the last of those, which value equals null.
                tagIndices.put(openingTag, m.end());
            }
        }
        for (Map.Entry<Integer, Integer> entry : tagIndices.entrySet())
            System.out.println(fileString.substring(entry.getKey(), entry.getValue()));
    }
}
