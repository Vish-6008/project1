
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 *
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button salary_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private TableView<employeeData> addEmployee_tableView;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_firstName;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_lastName;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_gender;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_position;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_date;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private ComboBox<?> addEmployee_gender;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<?> addEmployee_position;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private Label salary_firstName;

    @FXML
    private Label salary_lastName;

    @FXML
    private Label salary_position;

    @FXML
    private TextField salary_salary;

    @FXML
    private Button salary_updateBtn;
    
    @FXML
    private Label t_f;

    @FXML
    private Label t_i_e;

    @FXML
    private Label t_p;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableView<employeeData> salary_tableView;

    @FXML
    private TableColumn<employeeData, String> salary_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> salary_col_firstName;

    @FXML
    private TableColumn<employeeData, String> salary_col_lastName;

    @FXML
    private TableColumn<employeeData, String> salary_col_position;

    @FXML
    private TableColumn<employeeData, String> salary_col_salary;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
   

    private Image image;
    
    /*
     *Below  homeTotalEmployees method connects to a database, executes an SQL query 
     */
 

    public void homeTotalEmployees() {
    	/*
    	 * This below line created for sql query string that counts the number of rows in the--
    	 * --'employee_info' table and COUNT(id) function returns the number of non-null values in the 'id' column.
    	 */

    	//Below line of code connects database by calling .connectDb() method of the database object.
    	

        String sql = "SELECT COUNT(id) as employee_count FROM employee_info";

        connect = database.connectDb();
        
      //Below countData initializes an integer variable countData to [zero]0.

        int countData = 0 ;
        
        //Below try block is used to handle any unwanted exceptions that may occur during database operations.

        try {
        	//Below prepare statement is for execution and returns Object of preparedStatement object.
            prepare = connect.prepareStatement(sql);
            
          //result variable holds the execution of the query.
            result = prepare.executeQuery();

            /*
             * Below loop iterates through the result set. In this case there is only one row with--
             *  one column (the count of employee IDs).
             *   The result.getInt("COUNT(id)") retrieves the count and assigns it to--
             *   -- the countData variable.
             */

            while (result.next()) {
                countData = result.getInt("employee_count");
            }
            /*
             * Below  line converts the count data to a string and sets the text of the--
             * -- home_totalEmployees.
             */


            home_totalEmployees.setText(String.valueOf(countData));

        }
        //Below catch block handles any exceptions.

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * The homeEmployeeTotalPresent method connects to a database, executes an SQL query to count the--
     * -- number of employees in the employee_info table and  retrieves the result-- 
     * --and updates a UI component to display the total number of employees.
     */

    public void homeEmployeeTotalPresent() {

        String sql = "SELECT COUNT(id) as present_count FROM employee_info";

        connect = database.connectDb();
        int countData = 0;
        try {
            statement = connect.createStatement();
            
          //This executes the SQL query and stores the result in the result object, which is a ResultSet
            result = statement.executeQuery(sql);

            /*
             * Below loop iterates through the result set.In this case there is only one row with one column i,e employee IDs.
             *  The result.getInt("COUNT(id)") retrieves the count and assigns it to the countData variable
             */

            while (result.next()) {
                countData = result.getInt("present_count");
            }
            /*
             * This line converts the count data to a string and sets the text of the--
             * -- home_totalPresents UI component [text field] to display the count.
             */

            home_totalPresents.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Below block of method counts the In-Active employees.
     * 
     * This defines an SQL query string that counts the number of rows in the employee_info table--
     * -- where the salary column has a value of '0.0'.
     * The COUNT(id) function returns the number of non-null values in the id column for employees with--
     * --a salary of '0.0'.
     */

    public void homeTotalInactive() {

        String sql = "SELECT COUNT(id) as inactive_count FROM employee_info WHERE salary = '0.0'";

        connect = database.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("inactive_count");
            }
            home_totalInactiveEm.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Below method connects to a database, executes an SQL query to retrieve the count of employees--
     * --grouped by date, processes the query results to create data points for the last 7 dates--
     * --and updates an XY chart with this data.
     */

    public void homeChart() {

        home_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) as count FROM employee_info GROUP BY date ORDER BY date ASC LIMIT 7";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
          //Below line executes the SQL query and stores the result in the result object.
            result = prepare.executeQuery();
            
            /*
             * Below loop iterates through the result set--
             * --For each row, it retrieves the date (as a string) and the count of id values,--
             * --then creates a new data point (XYChart.Data) with these values and--
             * --adds it to the chart series.
             */

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            //This below line adds the chart series, which now contains the data points, to the home_chart object.
            home_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Below block of code add's the data of the each n every employee
     */

    public void addEmployeeAdd() {

    	/*
    	 * Below lines create a new Date object representing the current date and time--
    	 * -- then convert it to a java.sql.Date object suitable for SQL operations.


    	 */

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        //Below line defines an SQL insert statement to add a new employee record to the employee table.

        String sql = "INSERT INTO employee_info "
                + "(employee_id,firstName,lastName,gender,phoneNum,position,image,date,salary) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

      //Below line establishes a connection to database by calling the connectDb() method of the database object.
        connect = database.connectDb();

        try {
        	//Below line check for Empty Fields:
        	/*
        	 * This block checks if any of the required fields are empty.
        	 * If any are empty, it creates an error alert, sets the alert message and--
        	 * -- content and shows the alert.
        	 */

            Alert alert;
            if (addEmployee_employeeID.getText().isEmpty()
                    || addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phoneNum.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } 
            
          //Below line Checks for Existing employee ID:
            /*
             * This block constructs an SQL query to check if the employee ID already exists in the employee table. 
             * If the employee ID exists, it creates an error alert, sets the alert's title and content,--
             * --and shows the alert.
             */

            else {

                String check = "SELECT employee_id FROM employee WHERE employee_id = '"
                        + addEmployee_employeeID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + addEmployee_employeeID.getText() + " was already exist!");
                    alert.showAndWait();
                }
                
              //Below lines of code Insert's a new Employee :
                /*
                 * This below  block prepares the SQL insert statement and sets the values for each placeholder-- 
                 * --in the statement using the data from the input fields. 
                 * It then executes the statement to insert the new employee record into the--
                 * -- employee table.
                 * */

                else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addEmployee_employeeID.getText());
                    prepare.setString(2, addEmployee_firstName.getText());
                    prepare.setString(3, addEmployee_lastName.getText());
                    prepare.setString(4, (String) addEmployee_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addEmployee_phoneNum.getText());
                    prepare.setString(6, (String) addEmployee_position.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);
                    prepare.setString(8, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    
                    //Insert into 'employee_info' Table-
                    
                    /*
                     * Below block constructs a second SQL insert statement to add a corresponding--
                     * -- record to the employee_info table. 
                     * It sets the values for each placeholder using the data from the input fields --
                     * --and a default salary of "0.0", then executes the statement.
                     */


                    String insertInfo = "INSERT INTO employee_info "
                            + "(employee_id,firstName,lastName,position,salary,date) "
                            + "VALUES(?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertInfo);
                    prepare.setString(1, addEmployee_employeeID.getText());
                    prepare.setString(2, addEmployee_firstName.getText());
                    prepare.setString(3, addEmployee_lastName.getText());
                    prepare.setString(4, (String) addEmployee_position.getSelectionModel().getSelectedItem());
                    prepare.setString(5, "0.0");
                    prepare.setString(6, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                  //Below lines if code show's pop-up of Success Alert.
                    
                    /*
                     * This block creates an information alert to notify the user that the employee--
                     * -- has been successfully added. It sets the alert's title and content--
                     * --and shows the alert.
                     */

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeUpdate() {

    	//Getting the Path
    	
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        //Getting the Current Date
        /*
         * This Date object is then used to create a java.sql.Date object, sqlDate, which represents--
         * --  the current date in SQL format.
         */
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        //Below lines if code constructing the query for Postgresql
        String sql = "UPDATE employee SET firstName = '"
                + addEmployee_firstName.getText() + "', lastName = '"
                + addEmployee_lastName.getText() + "', gender = '"
                + addEmployee_gender.getSelectionModel().getSelectedItem() + "', phoneNum = '"
                + addEmployee_phoneNum.getText() + "', position = '"
                + addEmployee_position.getSelectionModel().getSelectedItem() + "', image = '"
                + uri + "', date = '" + sqlDate + "' WHERE employee_id ='"
                + addEmployee_employeeID.getText() + "'";

        connect = database.connectDb();

        try {
        	
        	//Alert Initialization
        	/*
        	 *Below Alert Declares a variable alert of type Alert. 
        	 *This will be used to show different alert messages to the user.
        	 */
            Alert alert;
            
            /*
             * Below condition Checks if any of the required fields (employeeID, firstName, lastName,--
             * --gender, phoneNum, position, and path) are empty or null.
             */
            if (addEmployee_employeeID.getText().isEmpty()
                    || addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phoneNum.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
            	
            	//Error Alert for Missing Fields
            	/*
            	 * If any required field is empty or null, an error alert is shown with the--
            	 * -- message "Please fill all blank fields".
            	 */
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
            	//Confirmation Alert for Updating Employee
            	/*
            	 * If all required fields are filled, a confirmation alert is shown asking the user if they are--
            	 * -- sure about updating the employee's information. The response is stored in option.
            	 */
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Employee ID: " + addEmployee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                //Checking User Confirmation,If the user clicks "OK" in the confirmation alert, the code inside this block will execute
                if (option.get().equals(ButtonType.OK)) {
                	
                	//A statement is created using the connect object, and the SQL update query constructed earlier is executed.
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    //Retrieving Current Salary
                    /*
                     * The current salary of the employee is retrieved from the employee_info table using a SELECT query.
                     * The result is processed in a while loop to get the salary value.
                     */
                    double salary = 0;

                    String checkData = "SELECT * FROM employee_info WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

                    while (result.next()) {
                        salary = result.getDouble("salary");
                    }

                    //Updating Employee Info
                    /*
                     * Another SQL UPDATE query is constructed and executed to update the employee_info table--
                     * -- with the new first name, last name, and position.
                     */
                    String updateInfo = "UPDATE employee_info SET firstName = '"
                            + addEmployee_firstName.getText() + "', lastName = '"
                            + addEmployee_lastName.getText() + "', position = '"
                            + addEmployee_position.getSelectionModel().getSelectedItem()
                            + "' WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(updateInfo);
                    prepare.executeUpdate();

                    //An informational alert is shown to inform the user that the update was successful.
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    //These methods are called to refresh the list of employees and reset the input fields.
                    addEmployeeShowListData();
                    addEmployeeReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeDelete() {

    	/*
    	 * Constructs an SQL DELETE query to remove the employee record from the employee table based on--
    	 * -- the employee_id provided in the text field addEmployee_employeeID.
    	 */
        String sql = "DELETE FROM employee WHERE employee_id = '"
                + addEmployee_employeeID.getText() + "'";

        //Establishes a connection to the database using the connectDb method from the database object
        connect = database.connectDb();

        try {

            Alert alert;
            /*
             * Below condition Checks if any of the required fields (employeeID, firstName, lastName, gender,--
             * -- phoneNum, position, and path) are empty or null.
             */
            if (addEmployee_employeeID.getText().isEmpty()
                    || addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phoneNum.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
            	
            	//If any required field is empty or null, an error alert is shown with the message "Please fill all blank fields"
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
            	/*
            	 * Confirmation Alert for Deleting Employee means If all required fields are filled,--
            	 * -- a confirmation alert is shown asking the user if they are sure about deleting--
            	 * -- the employee's information. The response is stored in option.
            	 * 
            	 */
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + addEmployee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                //Checking User Confirmation
                if (option.get().equals(ButtonType.OK)) {
                	
                	/*
                	 * A statement is created using the connect object, and the SQL delete query (sql)--
                	 *  constructed earlier is executed to delete the employee from the employee table.
                	 */
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    /*
                     * Deleting Employee Info. 
                     * Means Another SQL DELETE query is constructed and executed to delete the employee's info from the employee_info table.
                     */
                    String deleteInfo = "DELETE FROM employee_info WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    //Success Alert : means an informational alert is shown to inform the user that the deletion was successful
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    //These methods are called to refresh the list of employees and reset the input fields.
                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /*
     * The  below  addEmployeeReset method is designed to clear and reset all input fields in a--
     * -- form used for adding employee information
     */

    public void addEmployeeReset() {
    	//Resetting employee_ID field
        addEmployee_employeeID.setText("");
        
      //Resetting firstName field
        addEmployee_firstName.setText("");
        
      //Resetting Last name  field
        addEmployee_lastName.setText("");
        
      //Resetting gender field
        addEmployee_gender.getSelectionModel().clearSelection();
        
      //Resetting position field
        addEmployee_position.getSelectionModel().clearSelection();
        
      //Resetting phone number field
        addEmployee_phoneNum.setText("");
        
      //Resetting image field
        addEmployee_image.setImage(null);
        
      //Resetting the Data path.
        getData.path = "";
    }

    //The addEmployeeInsertImage method is used to allow the user to select and insert an image for the employee record.
    public void addEmployeeInsertImage() {

        FileChooser open = new FileChooser();
        
        //Opening the File Dialog
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        //Checks if a file was selected by the user. If no file was selected (the user canceled the dialog), file will be null
        if (file != null) {
        	
        	//[Creating and Displaying the Image]If a file was selected, the absolute path of the file is stored in getData.path.
            getData.path = file.getAbsolutePath();

            /*Creates a new Image object from the selected file. The toURI().toString() method converts the--
             * -- file path to a URI string, which is required by the Image constructor.
             * 
             */
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            addEmployee_image.setImage(image);
        }
    }

    //Below line declares and initializes an array of strings named positionList. This array contains job titles for employees.
    private String[] positionList = {"Marketer Coordinator", "Web Developer (Back End)", "Web Developer (Front End)", "App Developer"};

    public void addEmployeePositionList() {
    	/*
         * This defines a public method named addEmployeePositionList. 
         * This method will populate a combo box with the job titles from positionList.
         */
        List<String> listP = new ArrayList<>();
        
        /*
         * This for-each loop iterates over each element (data) in the positionList array.
         * Each job title from positionList is added to the listP array list
         */
        
        for (String data : positionList) {
            listP.add(data);
        }

        /*
         * FXCollections.observableArrayList(listP) converts the listP array list into an ObservableList,--
         * -- which is a list that can be observed for changes, suitable for use in JavaFX UI components.
         * addEmployee_position.setItems(listData) sets the items of the combo box (addEmployee_position) to --
         * --the ObservableList, effectively populating the combo box with the job titles.
         */
        ObservableList listData = FXCollections.observableArrayList(listP);
        addEmployee_position.setItems(listData);
    }

    //Gender List Declaration
    private String[] listGender = {"Male", "Female", "Others"};

    //Method to Add Gender List
    public void addEmployeeGendernList() {
    	//initializes a new ArrayList of strings named listG.
        List<String> listG = new ArrayList<>();

        
        //This for-each loop iterates over each element (data) in the listGender array.
        //Each gender option from listGender is added to the listG array list
        for (String data : listGender) {
            listG.add(data);
        }

        /*FXCollections.observableArrayList(listG) converts the listG array list into an ObservableList,
         *  which is a list that can be observed for changes, suitable for use in JavaFX UI components.
         */
        ObservableList listData = FXCollections.observableArrayList(listG);
        
        /*
         * addEmployee_gender.setItems(listData) sets the items of the combo box (addEmployee_gender) to--
         * -- the ObservableList, effectively populating the combo box with the gender options.
         */
        addEmployee_gender.setItems(listData);
    }

    //Below block of method defines that implements a search functionality for a list of employees
    public void addEmployeeSearch() {

    	//FilteredList is a wrapper around addEmployeeList that allows for dynamically filtering its content
        FilteredList<employeeData> filter = new FilteredList<>(addEmployeeList, e -> true);

      /*
       * addEmployee_search is a text field (probably a TextField) where the user types the search query.
       * textProperty().addListener(...) adds a listener to the text property of addEmployee_search,--
       * -- which triggers whenever the text changes.
       * Observable is the observable value (the text property), oldValue is the previous text, and newValue is the new text entered by the user.
       */
        addEmployee_search.textProperty().addListener((Observable, oldValue, newValue) -> {

        	//The predicate e -> true means initially all elements in addEmployeeList are included in the filter.
        	//predicateEmployeeData represents each item in the FilteredList during evaluation
            filter.setPredicate(predicateEmployeeData -> {

            	/*
            	 * If the search query (newValue) is null or empty, the predicate returns true,--
            	 * -- meaning all items are included (no filtering).
            	 */
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //Converts the search query to lowercase to enable case-insensitive search
                String searchKey = newValue.toLowerCase();

                /*Each if statement checks if the search query (searchKey) is contained within the--
                 * -- respective field of predicateEmployeeData (employee data)
                 */
                if (predicateEmployeeData.getEmployeeID().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPhoneNum().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<employeeData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addEmployee_tableView.comparatorProperty());
        addEmployee_tableView.setItems(sortList);
    }

    /*
     * The addEmployeeListData method retrieves data from a database table named employee--
     * -- and stores it in an ObservableList of employeeData objects.
     */
    
    public ObservableList<employeeData> addEmployeeListData() {
    	
    	/*
         * Below line initializes an ObservableList named listData which will store employeeData objects.
         */

        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        
        //This line select's all the columns from the employee table.
        String sql = "SELECT * FROM employee";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeD;

            while (result.next()) {
            	
            	/*
            	 *As below shown for each row in the result set, a new employeeData object (employeeD) is created and--
            	 * -- populated with the data from the current row.
            	 * The employeeData constructor is called with the values retrieved from the result set,--
            	 * -- matching the columns employee_id, firstName, lastName, gender, phoneNum, position, image, and date.
            	 * The newly created employeeData object is then added to the listData.
            	 */
                employeeD = new employeeData(result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("phoneNum"),
                        result.getString("position"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(employeeD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    /*
     * Below line declares a private ObservableList named addEmployeeList which will store employeeData objects.
     *  also this  list will be used to populate a TableView as well.
     */
    private ObservableList<employeeData> addEmployeeList;

    public void addEmployeeShowListData() {
    	//Fetching the data.
        addEmployeeList = addEmployeeListData();
        
        //Below mentioned  lines configure how each column in the TableView will retrieve data from the employeeData objects.
        /*
         * Also 
         * setCellValueFactory is called on each TableColumn to specify which property of the employeeData--
         * -- objects should be displayed in that column.
         * A 'new PropertyValueFactory<>("propertyName")' binds the column to the propertyName property of employeeData. 
         */
        

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        //This line sets the items property of the TableView to the addEmployeeList
        addEmployee_tableView.setItems(addEmployeeList);

    }

    public void addEmployeeSelect() {
    	
    	//employeeD is assigned the currently selected item (an employeeData object) from the TableView.
    	//and num is also  assigned the index of the currently selected item.
        employeeData employeeD = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        /*
         * Below condition  checks if the selected index minus one is less than -1.
         *  This is essentially a check to ensure that the selected index is valid. If it's invalid,--
         *  -- the method returns immediately, preventing further execution.
         */
        if ((num - 1) < -1) {
            return;
        }

        /*
         * Below lines set the text of various text fields in the UI to the corresponding properties of the selected employeeData object.
         * String.valueOf(employeeD.getEmployeeID()) converts the employeeId to a string before setting it in the text field.

         */
        addEmployee_employeeID.setText(String.valueOf(employeeD.getEmployeeID()));
        addEmployee_firstName.setText(employeeD.getFirstName());
        addEmployee_lastName.setText(employeeD.getLastName());
        addEmployee_phoneNum.setText(employeeD.getPhoneNum());

        //Setting the image path
        getData.path = employeeD.getImage();

        //This uri is created by prefixing the image path with "file:", which is required to load a local file as an image
        String uri = "file:" + employeeD.getImage();

        image = new Image(uri, 101, 127, false, true);
        addEmployee_image.setImage(image);
    }

    public void salaryUpdate() {

        String sql = "UPDATE employee_info SET salary = '" + salary_salary.getText()
                + "' WHERE employee_id = '" + salary_employeeID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (salary_employeeID.getText().isEmpty()
                    || salary_firstName.getText().isEmpty()
                    || salary_lastName.getText().isEmpty()
                    || salary_position.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select item first");
                alert.showAndWait();
            } else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                salaryShowListData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salaryReset() {
        salary_employeeID.setText("");
        salary_firstName.setText("");
        salary_lastName.setText("");
        salary_position.setText("");
        salary_salary.setText("");
    }

    public ObservableList<employeeData> salaryListData() {

        ObservableList<employeeData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee_info";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            employeeData employeeD;

            while (result.next()) {
                employeeD = new employeeData(result.getInt("employee_id"),
                         result.getString("firstName"),
                         result.getString("lastName"),
                         result.getString("position"),
                         result.getDouble("salary"));

                listData.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<employeeData> salaryList;

    public void salaryShowListData() {
        salaryList = salaryListData();

        salary_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salary_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salary_tableView.setItems(salaryList);

    }

    public void salarySelect() {

        employeeData employeeD = salary_tableView.getSelectionModel().getSelectedItem();
        int num = salary_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        salary_employeeID.setText(String.valueOf(employeeD.getEmployeeID()));
        salary_firstName.setText(employeeD.getFirstName());
        salary_lastName.setText(employeeD.getLastName());
        salary_position.setText(employeeD.getPosition());
        salary_salary.setText(String.valueOf(employeeD.getSalary()));

    }

    public void defaultNav() {
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            homeTotalEmployees();
            homeEmployeeTotalPresent();
            homeTotalInactive();
            homeChart();

        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            addEmployeeGendernList();
            addEmployeePositionList();
            addEmployeeSearch();

        } else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

            salaryShowListData();

        }

    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        defaultNav();

        homeTotalEmployees();
        homeEmployeeTotalPresent();
        homeTotalInactive();
        homeChart();

        addEmployeeShowListData();
        addEmployeeGendernList();
        addEmployeePositionList();

        salaryShowListData();
    }

}
