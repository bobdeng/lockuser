package cn.bobdeng.smartlock.lockuser;

public class LockUserManageRuleChecker {
    static boolean canManage(LockUser operator, PrivilegeLevel userLevel) {
        return operator.assign.canManage(userLevel.level);
    }


    static void checkAssign(LockUser operator, LockUserId id,LockPrivilege privilege) throws NoPrivilegeException, TimeRangeInvalidException {
        LockUser existLockUser = LockUserRepositories.find(id);
        checkAddUser(operator);
        checkAssignLevel(operator, privilege.level, existLockUser);
        checkAssignTimeRange(operator,privilege.timeRange);
    }

    private static void checkAddUser(LockUser operator) throws NoPrivilegeException {
        if (!operator.isBigger(UserLevel.ADMIN)) {
            throw new NoPrivilegeException();
        }
    }

    static void checkAssignTimeRange(LockUser operator, TimeRange timeRange) throws TimeRangeInvalidException {
        if (operator.overAssignTimeRange(timeRange)) {
            throw new TimeRangeInvalidException();
        }
    }

    static void checkAssignLevel(LockUser operator, PrivilegeLevel privilegeLevel, LockUser existLockUser) throws NoPrivilegeException {
        if (!canManage(operator,privilegeLevel)) {
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
