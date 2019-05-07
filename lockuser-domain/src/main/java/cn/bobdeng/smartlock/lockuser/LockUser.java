package cn.bobdeng.smartlock.lockuser;

import java.util.Objects;

public class LockUser {
    LockUserId id;
    Assign assign;

    public LockUser(LockUserId id, Assign assign) {
        this.id = id;
        this.assign = assign;
    }

    public boolean isOwner() {
        return assign.isOwner();
    }

    public LockUser createUser(String lockId, String userId, UserLevel userLevel) {
        LockUser lockUser = new LockUser(new LockUserId(lockId, userId), Assign.newUser(userLevel));
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
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

    public boolean canManage(UserLevel userLevel) {
        return assign.canManage(userLevel);
    }

    public LockUser createUser(String lockId, String userId, UserLevel level, long start, long end) throws TimeRangeInvalidException, NoPrivilegeException {
        checkAssign(lockId, userId, level, start, end);
        LockUser lockUser = new LockUser(new LockUserId(lockId, userId), Assign.newUser(level, start, end));
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

    private void checkAssign(String lockId, String userId, UserLevel level, long start, long end) throws NoPrivilegeException, TimeRangeInvalidException {
        LockUser existLockUser = LockUserRepositories.find(lockId, userId);
        checkAssignLevel(level, existLockUser);
        checkAssignTimeRange(start, end);
    }

    private void checkAssignTimeRange(long start, long end) throws TimeRangeInvalidException {
        if (this.overAssignTimeRange(start, end)) {
            throw new TimeRangeInvalidException();
        }
    }

    private void checkAssignLevel(UserLevel level, LockUser existLockUser) throws NoPrivilegeException {
        if (!this.canManage(level)) {
            throw new NoPrivilegeException();
        }
        checkUserManage(existLockUser);
    }

    private void checkUserManage(LockUser existLockUser) throws NoPrivilegeException {
        if (!this.canManage(existLockUser)) {
            throw new NoPrivilegeException();
        }
    }

    private boolean overAssignTimeRange(long start, long end) {
        return assign.timeRangeOverflow(start, end);
    }

    public boolean notExpire() {
        return assign.notExpire();
    }

    public boolean is(UserLevel normal) {
        return assign.is(normal);
    }

    public void removeUser(LockUser user) throws NoPrivilegeException {
        checkUserManage(user);
        LockUserRepositories.remove(user);
    }

    private boolean canManage(LockUser user) {
        if(user==null){
            return true;
        }
        return this.assign.canManage(user.assign);
    }
}
