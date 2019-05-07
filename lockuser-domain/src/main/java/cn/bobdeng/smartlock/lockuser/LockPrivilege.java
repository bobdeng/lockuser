package cn.bobdeng.smartlock.lockuser;

class LockPrivilege {
    PrivilegeLevel level;
    TimeRange timeRange;

    public static LockPrivilege newOwner() {
        LockPrivilege lockPrivilege = new LockPrivilege();
        lockPrivilege.level = PrivilegeLevel.newOwner();
        return lockPrivilege;
    }

    public static LockPrivilege newPrivilege(UserLevel userLevel) {
        LockPrivilege lockPrivilege = new LockPrivilege();
        lockPrivilege.level = new PrivilegeLevel(userLevel);
        return lockPrivilege;
    }

    public boolean isOwner() {
        return level.isOwner();
    }

    public boolean canManage(UserLevel userLevel) {
        return level.canManage(userLevel);
    }
}
