package cn.bobdeng.smartlock.lockuser;


public class LockUser {
    LockUserId id;
    Assign assign;

    LockUser(LockUserId id, Assign assign) {
        this.id = id;
        this.assign = assign;
    }

    public LockUser createUser(LockUserId id, UserName userName, LockPrivilege lockPrivilege) throws TimeRangeInvalidException, NoPrivilegeException {
        LockUserManageRuleChecker.checkAssign(this, id, lockPrivilege);
        LockUser lockUser = new LockUser(id, new Assign(lockPrivilege, userName));
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

    public boolean notExpire() {
        return assign.notExpire();
    }

    boolean overAssignTimeRange(TimeRange timeRange) {
        return assign.timeRangeOverflow(timeRange);
    }

    boolean is(UserLevel normal) {
        return assign.is(normal);
    }

    public void removeUser(LockUser user) throws NoPrivilegeException {
        LockUserManageRuleChecker.checkUserManage(this, user);
        LockUserRepositories.remove(user);
    }

    public boolean isBigger(UserLevel level) {
        return assign.isBigger(level);
    }

    public String getName() {
        return assign.userName.name;
    }

}
