package dao.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class SimilarNameCreator {

    private SimilarNameCreator(){}

    public static Collection<String> createSimilarStrings(String s) {
        Objects.requireNonNull(s);
        int capacity = (int) ((s.length() + 1) * 1.5);
        Collection<String> out = new ArrayList<>(capacity);
        out.add(s);
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            char prev = sb.charAt(i);
            sb.setCharAt(i, '_');
            out.add(sb.toString());
            sb.setCharAt(i, prev);
        }
        return out;
    }

}
