package cn.bobdeng.smartlock.lockuser;

public class LockUserFactory {
    public static LockUser createOwner(LockUserId lockUserId) {
        Assign assign = Assign.newOwner();
        LockUser lockUser = new LockUser(lockUserId, assign);
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

    public static LockUser newInstance(LockUserId id, UserName userName, LockPrivilege lockPrivilege) {
        return new LockUser(id, new Assign(lockPrivilege, userName));
    }

}
