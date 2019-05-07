package cn.bobdeng.smartlock.lockuser;

public enum UserLevel {
    OWNER(1000),ADMIN(100), ADVANCED(50), NORMAL(10);
    int value;

    UserLevel(int value) {
        this.value = value;
    }
}
