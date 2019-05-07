package cn.bobdeng.smartlock.lockuser;

public class Assign {
    LockPrivilege privilege;
    UserName userName;

    public static Assign newOwner() {
        Assign assign=new Assign();
        assign.privilege=LockPrivilege.newOwner();
        return assign;
    }

    public static Assign newUser(UserLevel userLevel) {
        Assign assign=new Assign();
        assign.privilege=LockPrivilege.newPrivilege(userLevel);
        return assign;
    }

    public static Assign newUser(UserLevel level, Long start, Long end) {
        Assign assign=new Assign();
        assign.privilege=LockPrivilege.newPrivilege(level,start,end);
        return assign;
    }

    public boolean isOwner() {
        return privilege.isOwner();
    }

    public boolean canManage(UserLevel userLevel) {
        return privilege.canManage(userLevel);
    }

    public boolean notExpire() {
        return privilege.notExpire();
    }

    public boolean timeRangeOverflow(Long start, Long end) {
        return privilege.timeRangeOverflow(start,end);
    }

    public boolean is(UserLevel level) {
        return privilege.is(level);
    }

    public boolean canManage(Assign assign) {
        return privilege.canManage(assign.privilege);
    }
}
