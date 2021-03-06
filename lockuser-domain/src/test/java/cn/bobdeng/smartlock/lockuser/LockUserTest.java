package cn.bobdeng.smartlock.lockuser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LockUserTest {

    public static final LockUserId OwnerLockId = new LockUserId("lock_123", "user_001");
    public static final LockUserId User1LockId = new LockUserId("lock_123", "user_100");
    public static final LockUserId User2LockId = new LockUserId("lock_123", "user_101");
    TimeRange timeRange;

    @Before
    public void setUp() throws Exception {
        LockUserRepositories.lockUserRepository = new LockUserRepositoryImpl();
    }

    @Test
    public void testOwnerAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = owner.assignUser(User1LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN));
        assertEquals(LockUserRepositories.find(User1LockId), adminUser);
        assertEquals(LockUserRepositories.find(User1LockId).assign, adminUser.assign);
        LockUserRepositoryImpl.time = 10000;
        assertTrue(adminUser.notExpire());
        assert owner.uniqueId!=null;
        LockUsers lockUsers = LockUsers.loadByLock("lock_123");
        assertEquals(lockUsers.size(),2);
    }

    @Test
    public void testOwnerAssignHasExpire() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        assertTrue(adminUser.notExpire());
        LockUserRepositoryImpl.time = timeRange.beforeStart(1000);
        assertFalse(adminUser.notExpire());
        LockUserRepositoryImpl.time = timeRange.afterEnd(1000);
        assertFalse(adminUser.notExpire());
    }
    @Test
    public void admin_assign_normal() throws NoPrivilegeException, TimeRangeInvalidException {
        LockUser owner = createOwner();
        LockUser admin = owner.assignUser(User1LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN, new TimeRange(0l,0l)));
        long now=System.currentTimeMillis();
        admin.assignUser(User2LockId,new UserName(null),LockPrivilege.newPrivilege(UserLevel.NORMAL,new TimeRange(now,now+100000)));
    }

    private LockUser ownerCreateAdmin(LockUser owner) throws TimeRangeInvalidException, NoPrivilegeException {
        long start = System.currentTimeMillis();
        long end = start + 100000;
        timeRange = new TimeRange(start, end);
        LockUserRepositoryImpl.time = start + 1000;
        return owner.assignUser(User1LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN, timeRange));
    }

    @Test(expected = NoPrivilegeException.class)
    public void 高级用户不能创建新用户() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUserRepositoryImpl.time=System.currentTimeMillis();
        LockUser advancedUser = owner.assignUser(User1LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADVANCED,new TimeRange(0l,0l)));
        advancedUser.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.NORMAL));
        assertTrue(advancedUser.notExpire());
    }

    @Test(expected = TimeRangeInvalidException.class)
    public void 不能超出自己授权的时间1() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADVANCED,new TimeRange(timeRange.beforeStart(1),timeRange.afterEnd(0))));
    }

    @Test(expected = TimeRangeInvalidException.class)
    public void 不能超出自己授权的时间2() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADVANCED));
    }

    @Test(expected = NoPrivilegeException.class)
    public void 管理员不能授权超过管理员() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN,timeRange));

    }

    @Test(expected = NoPrivilegeException.class)
    public void 不能给拥有者授权() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        owner.assignUser(OwnerLockId,new UserName(""),LockPrivilege.newPrivilege(UserLevel.ADMIN,timeRange));
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantAssignHigher() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser1 = ownerCreateAdmin(owner);
        LockUser adminUser2 = owner.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN,timeRange));
        adminUser1.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.NORMAL,timeRange));
    }

    @Test
    public void reAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        ownerCreateAdmin(owner);
        LockUser adminUser = owner.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.NORMAL,timeRange));
        assertTrue(adminUser.is(UserLevel.NORMAL));
    }

    @Test
    public void removeAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        owner.removeUser(adminUser);
        assertNull(LockUserRepositories.find(User1LockId));
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantRemoveOwner() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        owner.removeUser(owner);
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantRemoveHigher() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser1 = ownerCreateAdmin(owner);
        LockUser adminUser2 = owner.assignUser(User2LockId, new UserName(""), LockPrivilege.newPrivilege(UserLevel.ADMIN,timeRange));
        adminUser1.removeUser(adminUser2);
    }

    private LockUser createOwner() {
        LockUser owner = LockUserFactory.createOwner(OwnerLockId);
        assertTrue(owner.is(UserLevel.OWNER));
        assertEquals(LockUserRepositories.find(OwnerLockId), owner);
        return owner;
    }
}
