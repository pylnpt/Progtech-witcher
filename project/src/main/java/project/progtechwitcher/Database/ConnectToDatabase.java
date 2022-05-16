package project.progtechwitcher.Database;
import java.sql.*;
import java.util.ArrayList;


import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.*;

public class ConnectToDatabase {

    private ConnectToDatabase() {
    }

    private static String url = "jdbc:mysql://localhost:3306/progtech";
    private static String user = "root";
    private static String pwd = "";
    private static String driver= "com.mysql.cj.jdbc.Driver";

    public static ArrayList<Jobs> jobs = new ArrayList<Jobs>();
    public static ArrayList<UserBase> users = new ArrayList<UserBase>();

    private static Connection ConnectToDb()
    {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            Log.Error(ConnectToDatabase.class, e.getMessage());
        }

        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(url,user, pwd);

        } catch (SQLException e) {
            Log.Error(ConnectToDatabase.class,e.getMessage());
        }
        if(connection != null)
        {
            Log.Info(ConnectToDatabase.class, "Connected to Database");
        }
        return connection;
    }

    public static final void GetJobs(int inputId, ArrayList<Jobs> jobs, char type){

        Connection connection = ConnectToDb();

        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query;
                if(inputId == 0 && type == 'a')//all
                {
                    query = "select * from jobs";
                }
                else
                {
                    if(type == 'c')//created
                    {
                        query = String.format("select * from jobs where created_by = " + inputId);
                    }
                    else
                    {
                        query = String.format("select * from jobs where id = " + inputId);
                    }
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
                Log.Info(ConnectToDatabase.class, "Getting data from Jobs table was successful");
            }
            catch (SQLException e)
            {
                System.out.println(e);
                Log.Error(ConnectToDatabase.class,e.getMessage());
            }
            catch (Exception e)
            {
                System.out.println(e);
                Log.Error(ConnectToDatabase.class,"Some error occured while getting data from Jobs table");
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
                    Role role=null;
                    switch(rs.getString("role"))
                    {
                        case "admin" : role = Role.ADMIN; break;
                        case "employer": role = Role.EMPLOYER; break;
                        case "employee": role = Role.EMPLOYEE; break;
                        default: throw new IllegalArgumentException();
                    }
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
                            GetJobs(id, UsersJobs, 'c');
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
                                GetJobs(rs2.getInt("job_id"), UsersJobs, 'i');
                            }
                            user.takenJobs = UsersJobs;
                        }
                    }
                    users.add(user);
                }
                st.close();
                connection.close();
                Log.Info(ConnectToDatabase.class, "Getting data from Users table was successful");
            }catch (SQLException e)
            {
                System.out.println(e);
                Log.Error(ConnectToDatabase.class,e.getMessage());
            }
            catch (Exception e)
            {
                System.out.println(e);
                Log.Error(ConnectToDatabase.class,"Some error occured while getting data from Users table");
            }
        }

    }
}
