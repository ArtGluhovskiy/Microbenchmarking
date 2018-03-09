package org.art.performance.models.codewars;

import java.util.Set;
import java.util.TreeSet;

public class TwoToOne {

    public static String longest1(String s1, String s2) {
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Set<Character> chars = new TreeSet<>();
        for (char anArr1 : arr1) {
            chars.add(anArr1);
        }
        for (char anArr2 : arr2) {
            chars.add(anArr2);
        }
        StringBuilder sb = new StringBuilder(arr1.length + arr2.length);
        chars.forEach(sb::append);
        return sb.toString();
    }

    public static String longest2(String s1, String s2) {
        String s = s1 + s2;
        return s.chars().distinct().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
