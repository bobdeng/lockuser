package cn.bobdeng.smartlock.lockuser;

public class LockUserManageRuleChecker {
    static boolean canManage(LockUser operator, UserLevel userLevel) {
        return operator.assign.canManage(userLevel);
    }


    static void checkAssign(LockUser operator, String lockId, String userId, UserLevel level, Long start, Long end) throws NoPrivilegeException, TimeRangeInvalidException {
        LockUser existLockUser = LockUserRepositories.find(lockId, userId);
        checkAssignLevel(operator, level, existLockUser);
        checkAssignTimeRange(operator, start, end);
    }

    static void checkAssignTimeRange(LockUser operator, Long start, Long end) throws TimeRangeInvalidException {
        if (operator.overAssignTimeRange(start, end)) {
            throw new TimeRangeInvalidException();
        }
    }

    static void checkAssignLevel(LockUser operator, UserLevel level, LockUser existLockUser) throws NoPrivilegeException {
        if (!canManage(operator,level)) {
            throw new NoPrivilegeException();
        }
        checkUserManage(operator, existLockUser);
    }

    static void checkUserManage(LockUser operator, LockUser existLockUser) throws NoPrivilegeException {
        if (!canManage(operator, existLockUser)) {
            throw new NoPrivilegeException();
        }
    }

    static boolean canManage(LockUser operator, LockUser user) {
        if (user == null) {
            return true;
        }
        return operator.assign.canManage(user.assign);
    }
}
