package cn.bobdeng.smartlock.lockuser;

import java.util.Optional;

public class LockUserFactory {
    public static LockUser createOwner(LockUserId lockUserId) {
        Assign assign = Assign.newOwner();
        return createLockUser(lockUserId, assign);
    }

    public static LockUser createUser(LockUserId lockUserId, UserName name, LockPrivilege privilege) {
        Assign assign = new Assign(privilege, name);
        return createLockUser(lockUserId, assign);
    }

    private static LockUser createLockUser(LockUserId lockUserId, Assign assign) {
        LockUser lockUser = new LockUser(lockUserId, assign);
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

    public static LockUser newInstance(LockUserId id, UserName userName, LockPrivilege lockPrivilege) {
        return new LockUser(id, new Assign(lockPrivilege, userName));
    }

}
