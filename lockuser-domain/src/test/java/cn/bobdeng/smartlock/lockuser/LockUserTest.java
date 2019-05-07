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
        LockUserRepositories.lockUserRepository=new LockUserRepositoryImpl();
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
    }

    @Test
    public void testOwnerAssignHasExpire() {
        LockUser owner = createOwner();
        LockUser adminUser = owner.createUser(LOCK_ID, USER_ADMIN, UserLevel.ADMIN);
    }

    private LockUser createOwner() {
        LockUser owner = LockUserFactory.createOwner(LOCK_ID, USER_OWNER);
        assertTrue(owner.isOwner());
        assertEquals(LockUserRepositories.find(LOCK_ID, USER_OWNER), owner);
        return owner;
    }
}
