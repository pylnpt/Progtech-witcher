package project.progtechwitcher.Database;
import java.sql.*;
import java.util.ArrayList;


import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.*;

public class Database {

    private Database() {
    }

    private static String url = "jdbc:mysql://localhost:3306/progtech";
    private static String user = "root";
    private static String pwd = "";
    private static String driver= "com.mysql.cj.jdbc.Driver";

    public static ArrayList<Jobs> jobs = new ArrayList<Jobs>();
    public static ArrayList<UserBase> users = new ArrayList<UserBase>();

    protected static Connection ConnectToDb(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            Log.Error(Database.class, e.getMessage());
        }

        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(url,user, pwd);

        } catch (SQLException e) {
            Log.Error(Database.class,e.getMessage());
        }
        if(connection != null)
        {
            Log.Info(Database.class, "Connected to Database");
        }
        return connection;
    }

    //Read
    public static final void GetJobs(int inputId, ArrayList<Jobs> jobs, TypeForReadingJobs type){

        Connection connection = ConnectToDb();

        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "";
                switch (type) {
                    case ALL -> {
                        query = "select * from jobs";
                    }
                    case CREATED -> {
                        query = String.format("select * from jobs where created_by = " + inputId);
                    }
                    case ACCEPTED -> {
                        query = String.format("select * from jobs where id = " + inputId);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
                ResultSet rs = st.executeQuery(query);
                while(rs.next())//column index 1-től
                {
                    int id = rs.getInt("id");
                    String title=rs.getString("title");
                    String description = rs.getString("description");
                    int reward = rs.getInt("reward");
                    int requiredLevel= rs.getInt("required_level");
                    boolean isSomeoneAssigned = rs.getBoolean("is_someone_assigned");//igazából ez egy felesleges mező, de már így marad
                    int createdBy = rs.getInt("created_by");
                    int acceptedBy = 0;
                    boolean isDone = false;

                    Statement st2 = connection.createStatement();
                    ResultSet rs2 = st2.executeQuery("select user_id, is_done from work_user_connection where job_id = " + id);
                    if(rs2.next() != false)
                    {
                        acceptedBy = rs2.getInt(1);
                        isDone = rs2.getBoolean(2);
                    }
                    st2.close();

                    Jobs j = new Jobs(title, description,
                            reward, requiredLevel);
                    j.setAcceptedBy(acceptedBy);
                    j.setId(id);
                    j.setCreatedBy(createdBy);
                    j.setDone(isDone);
                    j.setSomeoneAssigned(isSomeoneAssigned);

                    jobs.add(j);
                }
                st.close();
                connection.close();
                Log.Info(Database.class, "Getting data from Jobs table was successful");
            }
            catch (SQLException e)
            {
                System.out.println(e);
                Log.Error(Database.class,e.getMessage());
            }
            catch (Exception e)
            {
                System.out.println(e);
                Log.Error(Database.class,"Some error occured while getting data from Jobs table");
            }
        }
    }
    public static void GetUsers(){

        Connection connection = ConnectToDb();

        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "select * from users";
                ResultSet rs = st.executeQuery(query);

                while(rs.next())
                {
                    UserBase user = null;
                    Role role= RoleConverter.StringToRole(rs.getString("role"));
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String passwordmd5 = rs.getString("password");
                    int level = rs.getInt("level");
                    ArrayList<Jobs> UsersJobs = null;

                    switch (role) {
                        case ADMIN -> {
                            user = new Admin(username);
                            user.setPassword(passwordmd5);
                            //user.setLevel(level);
                            user.setId(id);
                        }
                        case EMPLOYER -> {
                            user = new CanAdvertiseJobs(new Employer(username));
                            user.setLevel(level);
                            user.setId(id);
                            user.setPassword(passwordmd5);

                            UsersJobs = new ArrayList<>();
                            GetJobs(id, UsersJobs, TypeForReadingJobs.CREATED);
                            user.advertisedJobs = UsersJobs;
                        }
                        case EMPLOYEE -> {
                            user = new CanTakeJobs(new Employee(username));
                            user.setLevel(level);
                            user.setId(id);
                            user.setPassword(passwordmd5);
                            
                            UsersJobs = new ArrayList<>();
                            Statement st2 = connection.createStatement();
                            query = String.format("select job_id from work_user_connection where user_id =" + id);
                            ResultSet rs2 = st2.executeQuery(query);
                            while(rs2.next())
                            {
                                GetJobs(rs2.getInt("job_id"), UsersJobs, TypeForReadingJobs.ACCEPTED);
                            }
                            user.takenJobs = UsersJobs;
                        }
                    }
                    users.add(user);
                }
                st.close();
                connection.close();
                Log.Info(Database.class, "Getting data from Users table was successful");
            }catch (SQLException e)
            {
                System.out.println(e);
                Log.Error(Database.class,e.getMessage());
            }
            catch (Exception e)
            {
                System.out.println(e);
                Log.Error(Database.class,"Some error occured while getting data from Users table");
            }
        }

    }

    //Create
    public static void Registrate(String username, String password, Role role) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try
            {
                Statement st = connection.createStatement();
                String query = String.format("Insert into users(username, password, role, level) " +
                        "values(\'"+username+"\', md5(\'"+password+"\'), \'"+RoleConverter.RoleToString(role)+"\', 1);");
                int rs = st.executeUpdate(query);
                st.close();
                connection.close();
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            }
            catch (Exception e)
            {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
    public static void AdvertiseJob(int createdBy, String title, String description, int reward, int requiredLevel) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try
            {
                Statement st = connection.createStatement();
                String query = String.format("Insert into jobs(title, description, reward, required_level, is_someone_assigned, created_by) " +
                        "values(\'"+title+"\', \'"+description+"\', "+reward+", "+requiredLevel+", 0, "+createdBy+");");
                int rs = st.executeUpdate(query);
                st.close();
                connection.close();
                jobs=null;
                jobs = new ArrayList<Jobs>();
                GetJobs(0, jobs, TypeForReadingJobs.ALL);
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            }
            catch (Exception e)
            {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }

    //Update
    public static void ApplyToJob(int jobId, int userId) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try
            {
                String query = String.format("Update jobs set is_someone_assigned = 1 where id = " + jobId);
                Statement preparedStmt = connection.createStatement();
                preparedStmt.executeUpdate(query);

                Statement st = connection.createStatement();
                query = String.format("Insert into work_user_connection(user_id, job_id) values("+userId+","+jobId+")");
                st.executeUpdate(query);
                preparedStmt.close();
                st.close();
                connection.close();
                jobs=null;
                jobs = new ArrayList<Jobs>();
                GetJobs(0, jobs, TypeForReadingJobs.ALL);
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            }
            catch (Exception e)
            {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
    public static void SetJobDone(int jobId, int userId) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try {
                String query = String.format("Update work_user_connection set is_done = 1 where user_id = "+userId+" and job_id = "+jobId);
                Statement preparedStmt = connection.createStatement();
                preparedStmt.executeUpdate(query);
                preparedStmt.close();
                connection.close();
                jobs=null;
                jobs = new ArrayList<Jobs>();
                GetJobs(0, jobs, TypeForReadingJobs.ALL);
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            } catch (Exception e) {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
    public static void AddReward2User(int jobId, int userId) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try {
                String query = String.format("Select reward from jobs where id = "+jobId);
                Statement preparedStmt = connection.createStatement();
                ResultSet rs = preparedStmt.executeQuery(query);
                int reward = rs.getInt("reward");
                preparedStmt.close();

                query = String.format("Select level from users where id = "+userId);
                Statement st = connection.createStatement();
                ResultSet rs2 = st.executeQuery(query);
                int level = rs2.getInt("level");
                st.close();
                level +=reward;

                Statement st2 = connection.createStatement();
                query = String.format("Update users set level = "+level+" where id ="+userId);
                st2.executeUpdate(query);
                st2.close();
                connection.close();
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            } catch (Exception e) {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
    public static void ModifyPassword(int userId, String password) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try {
                String query = String.format("Update users set password = md5("+password+") where user_id = "+userId);
                Statement preparedStmt = connection.createStatement();
                preparedStmt.executeUpdate(query);
                preparedStmt.close();
                connection.close();
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            } catch (Exception e) {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }

    //Delete
    public static void DeleteJob(int jobId) {
        //ha nincs senki jelentkezve
        Connection connection = ConnectToDb();
        if (connection != null) {
            try {
                String query = String.format("Delete from jobs where id = "+jobId);
                Statement st = connection.createStatement();
                st.execute(query);
                st.close();
                connection.close();


                jobs=null;
                jobs = new ArrayList<Jobs>();
                GetJobs(0, jobs, TypeForReadingJobs.ALL);
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            } catch (Exception e) {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
    public static void DeleteUser(int userId) {
        Connection connection = ConnectToDb();
        if (connection != null) {
            try {
                String query = String.format("Delete from users where id = "+userId);
                Statement st = connection.createStatement();
                st.execute(query);
                st.close();
                query = String.format("Delete from jobs where created_by = "+userId);
                Statement st2 = connection.createStatement();
                st2.execute(query);
                st2.close();

                ArrayList<Jobs> jobsOfDeleted = new ArrayList<>();
                GetJobs(userId, jobsOfDeleted, TypeForReadingJobs.CREATED);
                for(Jobs x: jobsOfDeleted)
                {
                    query = String.format("Delete from work_user-connection where job_id = "+x.getId());
                    Statement st3 = connection.createStatement();
                    st3.execute(query);
                    st3.close();
                }

                connection.close();
                jobs=null;
                jobs = new ArrayList<Jobs>();
                GetJobs(0, jobs, TypeForReadingJobs.ALL);
                users = null;
                users = new ArrayList<UserBase>();
                GetUsers();
            } catch (Exception e) {
                Log.Error(Database.class, e.getMessage());
            }
        }
    }
}