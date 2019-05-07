package cn.bobdeng.smartlock.lockuser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LockUserTest {

    public static final String LOCK_ID = "lock_123";
    public static final String USER_OWNER = "user_123";
    public static final String USER_ADMIN = "user_002";

    @Before
    public void setUp() throws Exception {
        LockUserRepositories.lockUserRepository = new LockUserRepositoryImpl();
    }

    @Test
    public void testOwnerAssign() {
        LockUser owner = createOwner();
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN);
        assertNotNull(adminUser);
        assertFalse(adminUser.isOwner());
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_ADMIN), adminUser);
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_ADMIN).assign, adminUser.assign);
        assertTrue(adminUser.canManage(UserLevel.ADVANCED));
        assertFalse(adminUser.canManage(UserLevel.ADMIN));
        LockUserRepositoryImpl.time = 10000;
        assertTrue(adminUser.notExpire());
    }

    @Test
    public void testOwnerAssignHasExpire() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
        assertTrue(adminUser.notExpire());
        LockUserRepositoryImpl.time = start - 1000;
        assertFalse(adminUser.notExpire());
        LockUserRepositoryImpl.time = end + 1000;
        assertFalse(adminUser.notExpire());
    }

    @Test(expected = TimeRangeInvalidException.class)
    public void adminWithExpireAssign() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
        adminUser.createUser(LOCK_ID, "user_normal", UserLevel.ADVANCED, start - 1, end);
    }

    @Test(expected = NoPrivilegeException.class)
    public void assignWithNoPrivilege() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
        adminUser.createUser(LOCK_ID, "user_normal", UserLevel.ADMIN, start , end);

    }
    @Test(expected = NoPrivilegeException.class)
    public void cantAssignOwner() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_OWNER, UserLevel.ADMIN, start, end);
    }
    @Test(expected = NoPrivilegeException.class)
    public void cantAssignHigher() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser1 = owner.createUser(LOCK_ID, "user_admin_1", UserLevel.ADMIN, start, end);
        LockUser adminUser2 = owner.createUser(LOCK_ID, "user_admin_2", UserLevel.ADMIN, start, end);
        adminUser1.createUser(LOCK_ID, "user_admin_2", UserLevel.NORMAL, start, end);
    }

    @Test
    public void reAssign() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
        adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.NORMAL, start, end);
        assertTrue(adminUser.is(UserLevel.NORMAL));
    }

    @Test
    public void removeAssign() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
        owner.removeUser(adminUser);
        assertNull(LockUserRepositories.find(LOCK_ID,USER_ADMIN));
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantRemoveOwner() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        owner.removeUser(owner);
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantRemoveHigher() throws TimeRangeInvalidException,NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser1 = owner.createUser(LOCK_ID, "user_003", UserLevel.ADMIN, start, end);
        LockUser adminUser2 = owner.createUser(LOCK_ID, "user_003", UserLevel.ADMIN, start, end);
        adminUser1.removeUser(adminUser2);
    }


    private LockUser createOwner() {
        LockUser owner = LockUserFactory.createOwner(LOCK_ID, USER_OWNER);
        assertTrue(owner.isOwner());
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_OWNER), owner);
        return owner;
    }
}
