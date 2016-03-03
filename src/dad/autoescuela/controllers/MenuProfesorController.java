package dad.autoescuela.controllers;

import java.sql.Connection;
import java.sql.ResultSet;

import dad.autoescuela.Main;
import dad.autoescuela.connection.BDconnection;
import dad.autoescuela.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.Callback;

public class MenuProfesorController {

private Main main;
	
	@FXML
	private ToggleButton profesorButton;
	@FXML
	private Button guardarUsuarioButton;
	@FXML
	private TextField nombreTextField;
	@FXML
	private TextField dniTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private TableView tablaUsuarios;
	
	private static ObservableList<ObservableList> datos;  

	@FXML
	private void initialize(){
		
		/////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO USUARIO /////
		profesorButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
			
			if(newValue){
				profesorButton.setText("SI");
			}
			else
				profesorButton.setText("NO");
				
		});

		guardarUsuarioButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

				System.out.println("guardando..." + nombreTextField.textProperty().getValue());
				System.out.println("guardando..." + dniTextField.textProperty().getValue());
				System.out.println("guardando..." + passwordTextField.textProperty().getValue());
				System.out.println("guardando..." + profesorButton.selectedProperty().getValue());
			}
		});
		///////////////////////////////////////////////////////////////////////////////////////////TODO VER USUARIOS /////
		
		Connection con;  
		datos = FXCollections.observableArrayList(); 
		
		try{  
			con = BDconnection.connect();  
			String consulta = "SELECT * from usuarios";    
			ResultSet rs = con.createStatement().executeQuery(consulta); 

			// crear columnas
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
				final int j = i;                
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
						return new SimpleStringProperty(param.getValue().get(j).toString());                        
					}                    
				});

				tablaUsuarios.getColumns().addAll(col); 
				System.out.println("Column ["+i+"] ");
			}

			//crear filas
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                datos.add(row);

            }

            tablaUsuarios.setItems(datos);
			
		}catch(Exception e){  
			e.printStackTrace();      
		}  
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}



///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//boton.selectedProperty().addListener(new ChangeListener<Boolean>() {
//@Override
//public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//	System.out.println(newValue);
//	
//	if(newValue){
//		
//	}
//	
//}
//});
