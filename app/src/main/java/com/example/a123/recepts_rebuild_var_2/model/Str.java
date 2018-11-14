package com.example.a123.recepts_rebuild_var_2.model;

import java.util.Vector;

public class Str {
    public static String[] splitString(String str, String delim) {
        if (str == null) {
            return null;
        } else if (str.equals("") || delim == null || delim.length() == 0) {
            return new String[] { str };
        }
        String[] s;
        Vector v = new Vector();
        int pos, newpos;

        pos = 0;
        newpos = str.indexOf(delim, pos);

        while (newpos != -1) {
            v.addElement(str.substring(pos, newpos));
            pos = newpos + delim.length();
            newpos = str.indexOf(delim, pos);
        }
        v.addElement(str.substring(pos));

        s = new String[v.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = (String) v.elementAt(i);
        }
        s[s.length-1]=s[s.length-1].substring(0, s[s.length-1].length()-1);
        return s;
    }
}
