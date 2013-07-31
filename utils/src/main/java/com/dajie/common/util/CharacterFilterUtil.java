package com.dajie.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * User: jiangxu.qiu
 * Date: 13-2-18
 * Time: 下午2:29
 */
public final class CharacterFilterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterFilterUtil.class);

    private CharacterFilterUtil() {
    }


    /**
     * 根据指定的规则过滤字符串
     *
     * @param s    需要过滤的原始输入字符串
     * @param rule 需要使用的过滤规则
     * @return 使用过滤规则过滤后的字符串
     */
    public static String filter(String s, CharacterFilterRuleEnum rule) {
        if (s == null || s.isEmpty() || rule == null) {
            return s;
        }
        return rule.getFilter().filter(s);
    }

    /**
     * 过滤规则枚举
     * <p>r0&nbsp;-&nbsp;不进行任何过滤</p>
     * <p>r1&nbsp;-&nbsp;过滤ASCII码为0-8,10-31,127的字符</p>
     * <p>r2&nbsp;-&nbsp;过滤r1中的字符以及ASCII码为
     * 33-!&nbsp;38-&&nbsp;94-^&nbsp;126-~&nbsp;36-$&nbsp;
     * 37-%&nbsp;34-"&nbsp;39-'&nbsp;96-`&nbsp;123-{&nbsp;
     * 125-}&nbsp;60-<&nbsp;62->&nbsp;92-\&nbsp;</p>
     */
    public static enum CharacterFilterRuleEnum {
        None("r0", new R0Filter()), First("r1", new R1Filter()), Second("r2", new R2Filter());

        private final String ruleName;

        private final Filter filter;

        private CharacterFilterRuleEnum(String ruleName, Filter filter) {
            this.ruleName = ruleName;
            this.filter = filter;
        }

        public String getRuleName() {
            return ruleName;
        }

        public Filter getFilter() {
            return filter;
        }

        public static CharacterFilterRuleEnum parse(String ruleName) {
            for (CharacterFilterRuleEnum rule : CharacterFilterRuleEnum.values()) {
                if (rule.getRuleName().equalsIgnoreCase(ruleName)) {
                    return rule;
                }
            }
            return null;
        }

        private interface Filter {
            String filter(String s);
        }

        private static class R0Filter implements Filter {
            @Override
            public String filter(String s) {
                return s;
            }
        }

        private static class R1Filter implements Filter {
            @Override
            public String filter(String s) {
                if (s == null || s.isEmpty()) {
                    return s;
                }
                StringBuilder sb = new StringBuilder();
                for (int c : s.toCharArray()) {
                    if (c <= 8 || (c >= 10 && c <= 31) || c == 127) {
                        continue;
                    }
                    sb.append((char) c);
                }
                String result = sb.toString();
                if (!result.equals(s)) {
                    LOGGER.warn("r1 filtered : [{}] -> [{}]", s, result);
                }
                return result;
            }
        }

        private static class R2Filter implements Filter {
            private static final int[] FORBIDDEN = {33, 34, 36, 37, 38, 39, 60, 62, 92, 94, 96, 123, 125, 126};

            @Override
            public String filter(String s) {
                String r1Result = First.getFilter().filter(s);
                if (r1Result == null || r1Result.isEmpty()) {
                    return r1Result;
                }
                StringBuilder sb = new StringBuilder();
                for (int c : r1Result.toCharArray()) {
                    if (Arrays.binarySearch(FORBIDDEN, c) < 0) {
                        sb.append((char) c);
                    }
                }
                String result = sb.toString();
                if (!result.equals(s)) {
                    LOGGER.warn("r2 filtered : [{}] -> [{}]", s, result);
                }
                return result;
            }
        }
    }

}
