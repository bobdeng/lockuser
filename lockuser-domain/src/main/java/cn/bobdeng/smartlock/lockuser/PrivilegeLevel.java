package cn.bobdeng.smartlock.lockuser;

class PrivilegeLevel {
    UserLevel level;

    public PrivilegeLevel(UserLevel level) {
        this.level = level;
    }

    public static PrivilegeLevel newOwner() {
        return new PrivilegeLevel(UserLevel.OWNER);
    }

    public boolean isOwner() {
        return level==UserLevel.OWNER;
    }

    public boolean canManage(UserLevel userLevel) {
        return level.value>userLevel.value;
    }

    public boolean canManage(PrivilegeLevel level) {
        return this.canManage(level.level);
    }
}
