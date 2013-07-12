package com.chris.algo.search;

public abstract class PatternMatcher {
    private String pattern;
    public long comparetimes;

    public PatternMatcher(String pattern) {
        this.pattern = pattern;
    }

    protected String getPattern() {
        return pattern;
    }

    public long getComparetimes() {
        return comparetimes * (getPattern().length());
    }

    public abstract int match(String text);
}
