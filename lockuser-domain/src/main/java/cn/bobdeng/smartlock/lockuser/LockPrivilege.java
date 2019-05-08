package cn.bobdeng.smartlock.lockuser;

public class LockPrivilege {
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

    public static LockPrivilege newPrivilege(UserLevel level, TimeRange timeRange) {
        return new LockPrivilege(new PrivilegeLevel(level),timeRange);
    }

    public boolean canManage(UserLevel userLevel) {
        return level.canManage(userLevel);
    }

    public boolean notExpire() {
        return timeRange.notExpire();
    }

    public boolean timeRangeOverflow(TimeRange timeRange) {
        return this.timeRange.overflow(timeRange);
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
