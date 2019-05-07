package cn.bobdeng.smartlock.lockuser;

class LockPrivilege {
    PrivilegeLevel level;
    TimeRange timeRange;

    public LockPrivilege(PrivilegeLevel level, TimeRange timeRange) {
        this.level = level;
        this.timeRange = timeRange;
    }

    public static LockPrivilege newPrivilege(UserLevel userLevel) {
        LockPrivilege lockPrivilege = new LockPrivilege(new PrivilegeLevel(userLevel),new TimeRange());
        return lockPrivilege;
    }

    public static LockPrivilege newPrivilege(UserLevel level, Long start, Long end) {
        LockPrivilege lockPrivilege = newPrivilege(level);
        lockPrivilege.timeRange = new TimeRange(start, end);
        return lockPrivilege;
    }

    public boolean canManage(UserLevel userLevel) {
        return level.canManage(userLevel);
    }

    public boolean notExpire() {
        return timeRange.notExpire();
    }

    public boolean timeRangeOverflow(Long start, Long end) {
        return timeRange.overflow(start,end);
    }

    public boolean is(UserLevel level) {
        return this.level.level==level;
    }

    public boolean canManage(LockPrivilege privilege) {
        return level.canManage(privilege.level);
    }

    public boolean isBigger(UserLevel level) {
        return level.value>=level.value;
    }
}
