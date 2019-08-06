package cn.bobdeng.smartlock.lockuser;


import java.util.stream.Stream;

public enum UserLevel {
    OWNER(1000,"拥有者"), ADMIN(100,"管理员"), ADVANCED(50,"高级用户"), NORMAL(10,"普通用户");
    int value;
    String description;

    UserLevel(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static UserLevel of(int level) {
        return Stream.of(values())
                .filter(userLevel -> userLevel.value == level)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("wrong level " + level));
    }
}
