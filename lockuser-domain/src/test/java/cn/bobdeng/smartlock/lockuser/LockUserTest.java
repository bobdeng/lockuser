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
    long start;
    long end;

    @Before
    public void setUp() throws Exception {
        LockUserRepositories.lockUserRepository = new LockUserRepositoryImpl();
    }

    @Test
    public void testOwnerAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, null, null);
        assertNotNull(adminUser);
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_ADMIN), adminUser);
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_ADMIN).assign, adminUser.assign);
        LockUserRepositoryImpl.time = 10000;
        assertTrue(adminUser.notExpire());
    }

    @Test
    public void testOwnerAssignHasExpire() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        assertTrue(adminUser.notExpire());
        LockUserRepositoryImpl.time = start - 1000;
        assertFalse(adminUser.notExpire());
        LockUserRepositoryImpl.time = end + 1000;
        assertFalse(adminUser.notExpire());
    }

    private LockUser ownerCreateAdmin(LockUser owner) throws TimeRangeInvalidException, NoPrivilegeException {
        start = System.currentTimeMillis();
        end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        return owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN, start, end);
    }
    @Test
    public void onlyAdminCanCreate() throws TimeRangeInvalidException, NoPrivilegeException{
        LockUser owner = createOwner();
        LockUser advancedUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADVANCED, null, null);
        advancedUser.createUser(LOCK_ID,"user_004",UserLevel.NORMAL,null,null);
    }

    @Test(expected = TimeRangeInvalidException.class)
    public void adminWithExpireAssign1() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.createUser(LOCK_ID, "user_normal", UserLevel.ADVANCED, start - 1, end);
    }

    @Test(expected = TimeRangeInvalidException.class)
    public void adminWithExpireAssign2() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.createUser(LOCK_ID, "user_normal", UserLevel.ADVANCED, null, null);
    }

    @Test(expected = NoPrivilegeException.class)
    public void assignWithNoPrivilege() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser.createUser(LOCK_ID, "user_normal", UserLevel.ADMIN, start, end);

    }

    @Test(expected = NoPrivilegeException.class)
    public void cantAssignOwner() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        long start = System.currentTimeMillis();
        long end = start + 100000;
        LockUserRepositoryImpl.time = start + 1000;
        LockUser adminUser = owner.createUser(LOCK_ID, USER_OWNER, UserLevel.ADMIN, start, end);
    }

    @Test(expected = NoPrivilegeException.class)
    public void cantAssignHigher() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser1 = ownerCreateAdmin(owner);
        LockUser adminUser2 = owner.createUser(LOCK_ID, "user_admin_2", UserLevel.ADMIN, start, end);
        adminUser1.createUser(LOCK_ID, "user_admin_2", UserLevel.NORMAL, start, end);
    }

    @Test
    public void reAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.NORMAL, start, end);
        assertTrue(adminUser.is(UserLevel.NORMAL));
    }

    @Test
    public void removeAssign() throws TimeRangeInvalidException, NoPrivilegeException {
        LockUser owner = createOwner();
        LockUser adminUser = ownerCreateAdmin(owner);
        owner.removeUser(adminUser);
        assertNull(LockUserRepositories.find(LOCK_ID, USER_ADMIN));
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
        LockUser adminUser2 = owner.createUser(LOCK_ID, "user_003", UserLevel.ADMIN, start, end);
        adminUser1.removeUser(adminUser2);
    }

    private LockUser createOwner() {
        LockUser owner = LockUserFactory.createOwner(LOCK_ID, USER_OWNER);
        assertTrue(owner.is(UserLevel.OWNER));
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_OWNER), owner);
        return owner;
    }
}
