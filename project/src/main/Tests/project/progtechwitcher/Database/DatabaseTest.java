package project.progtechwitcher.Database;

import org.junit.jupiter.api.Test;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.UserBase;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    //tests could fail in case of adding the same element as once before

    @Test
    void connectToDb() {
        Connection con = null;
        con = Database.ConnectToDb();
        assertNotNull(con);
    }

    @Test
    void getUsers() {
        Database.GetUsers(0);
        assertNotNull(Database.users);
    }

    @Test //already inserted
    void registrate1() {
        Database.GetUsers(0);
        ArrayList<UserBase> users = new ArrayList<>(Database.users);
        Database.Registrate("admin1", "password", Role.EMPLOYEE);
        assertEquals(Database.users.size(), users.size());
    }
    @Test // can fail if already inserted (username has unique constraint)
    void registrate2() {
        Database.GetUsers(0);
        ArrayList<UserBase> users = new ArrayList<>(Database.users);
        Database.Registrate("newUsername002", "password", Role.EMPLOYEE);
        assertNotEquals(Database.users.size(), users.size());
    }

    @Test
    void advertiseJob() {
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.ALL);
        ArrayList<Jobs> jobs = new ArrayList<>(Database.jobs);
        Database.AdvertiseJob(4, "title", "description", 0, 1);
        assertNotEquals(Database.jobs.size(), jobs.size());
    }

    @Test
    void getJobs1() {
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.ALL);
        assertNotEquals(0, Database.jobs.size());
    }
    @Test
    void getJobs2() {
        Database.GetJobs(2, Database.jobs, TypeForReadingJobs.CREATED);
        assertNotEquals(0, Database.jobs.size());
    }

    @Test
    void getJobs3() {
        Database.GetJobs(2, Database.jobs, TypeForReadingJobs.ACCEPTED);
        assertNotEquals(0, Database.jobs.size());
    }

    @Test
    void getJobs4() {
        Database.jobs.clear();
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.CREATED);
        assertEquals(0, Database.jobs.size());
    }

    @Test
    void getJobs5() {
        Database.GetJobs(9, Database.jobs, TypeForReadingJobs.ALL);
        assertNotEquals(0, Database.jobs.size());
    }
}