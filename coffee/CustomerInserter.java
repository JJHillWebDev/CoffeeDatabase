import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.*;

/**
	This application connects to the CoffeeDB database, and allows the user to
	insert a new row into the Customer table. It will also print the Customer
	Table to the console after adding the new customer.
    @author Jeremy Hill
    @version 1.8.0_271
 */
public class CustomerInserter extends Application
{
	//Fields
	TextField customerNumberText;
	TextField nameText;
	TextField addressText;
	TextField cityText;
	TextField stateText;
	TextField zipText;

	/**
	* The main method calls the Application class launch
	* @param args the command line arguments
	*/
	public static void main(String[] args) 
	{
		launch(args);
	}

	/**
	* The start method creates the GUI that the user will use to enter the
	* customer information to de added to the CoffeeDB.
	* @param stage Stage object to display scene
	*/
	@Override
	public void start(Stage stage) 
	{
		//Labels for the GUI
		Label customerNumberLabel = new Label("Customer Number");
		Label nameLabel = new Label("Name");
		Label addressLabel = new Label("Address");
		Label cityLabel = new Label("City");
		Label stateLabel = new Label("State");
		Label zipLabel = new Label("ZIP Code");

		//TextFields for user to enter Customer data
		customerNumberText = new TextField();
		nameText = new TextField();
		addressText = new TextField();
		cityText = new TextField();
		stateText = new TextField();
		zipText = new TextField();

		//Button to add new customer to the table
		Button addButton = new Button("Add");

		//Create VBoxes to format the GUI
		VBox textLabelBox = new VBox(5, customerNumberLabel,customerNumberText,
								nameLabel, nameText, addressLabel, addressText,
								cityLabel, cityText, stateLabel, stateText,
								zipLabel, zipText);

		VBox vbox = new VBox(10, textLabelBox, addButton);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		// Register an event handler for the addButton
		addButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event) 
			{
				//Create a named constant for the URL
				final String DB_URL = "jdbc:derby:CoffeeDB";
      
			    try
			    {
			    	//Get the customer data entered by the user
			    	String customerNumber = customerNumberText.getText();
			    	String name = nameText.getText();
			    	String address = addressText.getText();
			    	String city = cityText.getText();
			    	String state = stateText.getText();
			    	String zip = zipText.getText();

			        //Create a connection to the database.
			        Connection conn = DriverManager.getConnection(DB_URL);
			         
			        //Get a Statement object.
			        Statement stmt = conn.createStatement();
			        
			        //SQL statment to enter the customer data
			        String sql = "INSERT INTO Customer VALUES" +
			               "('" + customerNumber + "', '" + name + 
			               "', '" + address + "', '" + city + 
			               "', '" + state + "', '" + zip + "')";

			        //Console Debug statments
			        System.out.println("DEBUG------------------------");
			        System.out.println(sql);
			        System.out.println("END-DEBUG--------------------");
			        System.out.println("Print the customer table: ");
			        System.out.println("");
			        stmt.executeUpdate(sql);

			        //SQL statment to print Customer Table to the console
			        String sqlStatement = "SELECT * FROM Customer";
			        ResultSet result = stmt.executeQuery(sqlStatement);

			        //Print the Customer Table to the console
			        while (result.next())
			        {
			        	System.out.printf("%10s %25s %25s %12s %2s %5s\n",
			        					result.getString("Name"),
			        					result.getString("CustomerNumber"),
			        					result.getString("Address"),
			        					result.getString("City"),
			        					result.getString("State"),
			        					result.getString("Zip"));
			        }

			        //Close the connection.
			        conn.close();
			    }

			    catch (Exception ex)
			    {
			        System.out.println("ERROR: " + ex.getMessage());
			    }
			}
		});

	  	//Create the scene
		Scene scene = new Scene(vbox);
		//Set the scene
		stage.setScene(scene);
		//Display the scene
		stage.show();
	}
}