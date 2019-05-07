package cn.bobdeng.smartlock.lockuser;


import java.util.stream.Stream;

public enum UserLevel {
    OWNER(1000), ADMIN(100), ADVANCED(50), NORMAL(10);
    int value;

    UserLevel(int value) {
        this.value = value;
    }

    public static UserLevel of(int level) {
        return Stream.of(values())
                .filter(userLevel -> userLevel.value == level)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("wrong level " + level));
    }
}
