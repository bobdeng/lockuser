package cn.bobdeng.smartlock.lockuser;

public class LockUserFactory {
    public static LockUser createOwner(String lockId, String ownerId) {
        Assign assign = Assign.newOwner();
        LockUser lockUser = new LockUser(new LockUserId(lockId,ownerId), assign);
        LockUserRepositories.saveLockUser(lockUser);
        return lockUser;
    }

}
