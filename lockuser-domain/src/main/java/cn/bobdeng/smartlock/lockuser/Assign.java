package cn.bobdeng.smartlock.lockuser;

public class Assign {
    LockPrivilege privilege;
    UserName userName;

    public Assign(LockPrivilege privilege, UserName userName) {
        this.privilege = privilege;
        this.userName = userName;
    }

    public static Assign newOwner() {
        return new Assign(LockPrivilege.newPrivilege(UserLevel.OWNER),new UserName(""));
    }

    public boolean canManage(UserLevel userLevel) {
        return privilege.canManage(userLevel);
    }

    public boolean notExpire() {
        return privilege.notExpire();
    }

    public boolean timeRangeOverflow(TimeRange timeRange) {
        return privilege.timeRangeOverflow(timeRange);
    }

    public boolean is(UserLevel level) {
        return privilege.is(level);
    }

    public boolean canManage(Assign assign) {
        return privilege.canManage(assign.privilege);
    }

    public boolean isBigger(UserLevel level) {
        return privilege.isBigger(level);
    }
}
