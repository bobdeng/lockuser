package cn.bobdeng.smartlock.lockuser;


import java.util.stream.Stream;

public enum UserLevel {
    OWNER(1000, "拥有者", 10), ADMIN(100, "管理员", 5), ADVANCED(50, "高级用户",2), NORMAL(10, "普通用户",0);
    int value;
    String description;
    int maxFingerPass;

    public String getDescription() {
        return description;
    }

    UserLevel(int value, String description, int maxFingerPass) {
        this.value = value;
        this.description = description;
        this.maxFingerPass = maxFingerPass;
    }


    public static UserLevel of(int level) {
        return Stream.of(values())
                .filter(userLevel -> userLevel.value == level)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("wrong level " + level));
    }
}
