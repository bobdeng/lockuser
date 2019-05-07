package cn.bobdeng.smartlock.lockuser;

class LockPrivilege {
    PrivilegeLevel level;
    TimeRange timeRange;

    public LockPrivilege(PrivilegeLevel level, TimeRange timeRange) {
        this.level = level;
        this.timeRange = timeRange;
    }

    public static LockPrivilege newOwner() {
        LockPrivilege lockPrivilege = newPrivilege(UserLevel.OWNER);
        return lockPrivilege;
    }

    public static LockPrivilege newPrivilege(UserLevel userLevel) {
        LockPrivilege lockPrivilege = new LockPrivilege(new PrivilegeLevel(userLevel),new TimeRange());
        return lockPrivilege;
    }

    public static LockPrivilege newPrivilege(UserLevel level, long start, long end) {
        LockPrivilege lockPrivilege = newPrivilege(level);
        lockPrivilege.timeRange = new TimeRange(start, end);
        return lockPrivilege;
    }

    public boolean isOwner() {
        return level.isOwner();
    }

    public boolean canManage(UserLevel userLevel) {
        return level.canManage(userLevel);
    }

    public boolean notExpire() {
        return timeRange.notExpire();
    }

    public boolean timeRangeOverflow(long start, long end) {
        return timeRange.overflow(start,end);
    }

    public boolean is(UserLevel level) {
        return this.level.level==level;
    }

    public boolean canManage(LockPrivilege privilege) {
        return level.canManage(privilege.level);
    }
}
