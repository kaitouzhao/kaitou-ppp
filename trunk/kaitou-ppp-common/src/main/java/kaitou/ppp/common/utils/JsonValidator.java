package kaitou.ppp.common.utils;

import com.womai.bsp.tool.utils.CollectionUtil;

import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

import static kaitou.ppp.common.utils.FileUtil.readLines;

/**
 * JSON校验器.
 * User: 赵立伟
 * Date: 2015/2/11
 * Time: 14:34
 */
public abstract class JsonValidator {

    private static CharacterIterator it;
    private static char c;

    /**
     * 验证json文件是否合法
     * <p>
     * 验证每一行是否合法
     * </p>
     *
     * @param path 文件路径
     * @return true-合法 ，false-非法
     */
    public static boolean validateFile(String path) {
        List<String> lines;
        try {
            lines = readLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (CollectionUtil.isEmpty(lines)) {
            return true;
        }
        for (String line : lines) {
            if (!validate(line)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param input 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public static boolean validate(String input) {
        String temp = input.trim();
        return valid(temp);
    }

    private static boolean valid(String input) {
        if ("".equals(input)) return true;
        it = new StringCharacterIterator(input);
        c = it.first();
        if (!value()) {
            return false;
        } else {
            skipWhiteSpace();
            if (c != CharacterIterator.DONE) {
                return false;
            }
        }
        return true;
    }

    private static boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }

    private static boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (c != t) return false;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        return ret;
    }

    private static boolean array() {
        return aggregate('[', ']', false);
    }

    private static boolean object() {
        return aggregate('{', '}', true);
    }

    private static boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (c != entryCharacter) return false;
        nextCharacter();
        skipWhiteSpace();
        if (c == exitCharacter) {
            nextCharacter();
            return true;
        }
        for (; ; ) {
            if (prefix) {
                if (!string()) return false;
                skipWhiteSpace();
                if (c != ':') return false;
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (c == ',') {
                    nextCharacter();
                } else if (c == exitCharacter) {
                    break;
                } else {
                    return false;
                }
            } else {
                return false;
            }
            skipWhiteSpace();
        }
        nextCharacter();
        return true;
    }

    private static boolean number() {
        if (!Character.isDigit(c) && c != '-') return false;
        if (c == '-') nextCharacter();
        if (c == '0') {
            nextCharacter();
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c))
                nextCharacter();
        } else {
            return false;
        }
        if (c == '.') {
            nextCharacter();
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return false;
            }
        }
        if (c == 'e' || c == 'E') {
            nextCharacter();
            if (c == '+' || c == '-') {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean string() {
        if (c != '"') return false;
        boolean escaped = false;
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && c == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (c == '"') {
                nextCharacter();
                return true;
            }
        }
        return false;
    }

    private static boolean escape() {
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return false;
        }
        if (c == 'u') {
            if (!isHex()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isHex() {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    private static char nextCharacter() {
        c = it.next();
        return c;
    }

    private static void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }

}
