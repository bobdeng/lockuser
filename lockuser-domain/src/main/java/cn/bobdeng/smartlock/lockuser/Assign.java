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

    public boolean isOwner() {
        return privilege.isOwner();
    }

    public boolean canManage(UserLevel userLevel) {
        return privilege.canManage(userLevel);
    }
}
