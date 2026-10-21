package me.zhenxin.zmusic.api;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version {

    // 只取开头的数字段：Spigot/旧版 Paper 为 1.21.4-R0.1-SNAPSHOT，Paper 26.x 为 26.3.build.209-stable
    private static final Pattern NUMERIC_PREFIX = Pattern.compile("\\d+(\\.\\d+)*");

    public String getRawVersion() {
        return Bukkit.getBukkitVersion();
    }

    public String getVersion() {
        String raw = getRawVersion();
        Matcher matcher = NUMERIC_PREFIX.matcher(raw);
        return matcher.lookingAt() ? matcher.group() : raw.split("-")[0];
    }

    //比目标版本低
    public boolean isLowerThan(String ver) {
        return compare(ver) < 0;
    }

    //比目标版本高
    public boolean isHigherThan(String ver) {
        return compare(ver) > 0;
    }

    // 与目标版本比较: 负数=低于, 0=相等, 正数=高于
    // 按点分段逐段比较，长度不足按 0 处理，兼容 CalVer (如 26.1.2) 与旧语义版本 (如 1.9)
    private int compare(String ver) {
        String[] cur = getVersion().split("\\.");
        String[] target = ver.split("\\.");
        int len = Math.max(cur.length, target.length);
        for (int i = 0; i < len; i++) {
            int c = i < cur.length ? Integer.parseInt(cur[i]) : 0;
            int t = i < target.length ? Integer.parseInt(target[i]) : 0;
            if (c != t) {
                return c - t;
            }
        }
        return 0;
    }

    //与目标版本相等
    public boolean isEquals(String ver) {
        return getVersion().equals(ver);
    }
}
