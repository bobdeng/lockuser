package cn.bobdeng.smartlock.lockuser;

import java.util.Objects;

public class LockUser {
    LockUserId id;
    Assign assign;

    LockUser(LockUserId id, Assign assign) {
        this.id = id;
        this.assign = assign;
    }

    public LockUser createUser(String lockId, String userId, UserLevel level, Long start, Long end) throws TimeRangeInvalidException, NoPrivilegeException {
        LockUserManageRuleChecker.checkAssign(this, lockId, userId, level, start, end);
        LockUser lockUser = new LockUser(new LockUserId(lockId, userId), Assign.newUser(level, start, end));
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

    public boolean notExpire() {
        return assign.notExpire();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockUser lockUser = (LockUser) o;
        return Objects.equals(id, lockUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    boolean overAssignTimeRange(Long start, Long end) {
        return assign.timeRangeOverflow(start, end);
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
}
