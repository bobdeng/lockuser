package cn.bobdeng.smartlock.lockuser;


public class LockUser {
    LockUserId id;
    Assign assign;

    LockUser(LockUserId id, Assign assign) {
        this.id = id;
        this.assign = assign;
    }

    public LockUser assignUser(LockUserId id, UserName userName, LockPrivilege lockPrivilege) throws TimeRangeInvalidException, NoPrivilegeException {
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
    public void remove(){
        LockUserRepositories.remove(this);
    }

    public boolean isBigger(UserLevel level) {
        return assign.isBigger(level);
    }
    public boolean canManage(UserLevel level) {
        return assign.canManage(level);
    }

    public String getName() {
        return assign.userName.name;
    }

    public String getUserId(){
        return id.userId.id;
    }

    public Long getStart() {
        return assign.privilege.timeRange.start;
    }

    public Long getEnd() {
        return assign.privilege.timeRange.end;
    }

}
