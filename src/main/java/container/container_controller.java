package container;


import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import dialog.dialog;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import mongo.employee;
import mongo.subblier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class container_controller implements Initializable {


//    @FXML
//    private TreeTableView<SupplierTable> product_table;

    @FXML
    private Tab employee_tab;

    @FXML
    private Tab subblier_tab;

    @FXML
    private Button supplierAdd;

    @FXML
    private Button supplierDelete;

    @FXML
    private Button supplierUpdate;


    @FXML
    private TreeTableView<SupplierTable> supplierTable;

    @FXML
    private TreeTableColumn<SupplierTable, String> supplierTableName;

    @FXML
    private TreeTableColumn<SupplierTable, String> supplierTablePhone;

    @FXML
    private TreeTableColumn<SupplierTable, String> supplierTableAddress;

    @FXML
    private TextField supplierName;

    @FXML
    private TextField supplierAddress;

    @FXML
    private TextField supplierPhone;
    @FXML
    private Button supplierRefresh;
    @FXML
    void supplierRefreshAction(ActionEvent event) {

        // reset Fields
        this.supplierPhone.setText("");
        this.supplierName.setText("");
        this.supplierAddress.setText("");

        this.supplierAdd.setDisable(false);
        this.supplierUpdate.setDisable(true);


    }

    ObservableList<SupplierTable> supplierTable_Data = FXCollections.observableArrayList();




    @FXML
    private TextField employee_name;

    @FXML
    private TextField employee_phone;

    @FXML
    private TextField employee_address;

    @FXML
    private TextField employee_salary;

    @FXML
    private Button add_employee;

    @FXML
    private Button remove_employee;

    @FXML
    private Button update_employee;

    @FXML
    private TreeTableView<EmployeeTable> employee_table;

    @FXML
    private TreeTableColumn<EmployeeTable, String> employee_name_col;

    @FXML
    private TreeTableColumn<EmployeeTable, String> employee_phone_col;

    @FXML
    private TreeTableColumn<EmployeeTable, String>employee_address_col;

    @FXML
    private TreeTableColumn<EmployeeTable, Double>employee_salary_col;

    ObservableList<EmployeeTable> EmployeeTable_data = FXCollections.observableArrayList();


    @FXML
    void add_employee_action(ActionEvent event) {

        // add Supplier Table
        if (employee_name.getText().trim().isEmpty()
                || employee_address.getText().trim().isEmpty()
                || employee_phone.getText().trim().isEmpty()
                || employee_salary.getText().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {

            // insert into emp database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", employee_name.getText());
            basicDBObject.put("address", employee_address.getText());
            basicDBObject.put("phone", employee_phone.getText());
            basicDBObject.put("salary", employee_salary.getText());


            BasicDBObject basicDBObject2 = employee.insertemployee(basicDBObject);
            System.out.println(basicDBObject2.get("name").toString());

            if (basicDBObject2 != null) {

                // add
                EmployeeTable_data.add(new EmployeeTable(basicDBObject2.get("_id").toString(),
                        basicDBObject2.get("name").toString(),
                        basicDBObject2.get("address").toString(),
                        basicDBObject2.get("phone").toString(),
                        Double.parseDouble(basicDBObject2.get("salary").toString())

                ));

                final TreeItem<EmployeeTable> rootemp = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
                employee_table.setRoot(rootemp);


                // reset Fields
                this.employee_name.setText("");
                this.employee_address.setText("");
                this.employee_phone.setText("");
                this.employee_salary.setText("");


            }
        }
    }

    @FXML
    void refresh_btn_emp_action(ActionEvent event) {

    }

    @FXML
    void remove_employee_action(ActionEvent event) {

    }

    @FXML
    void update_employee_action(ActionEvent event) {

    }




    @FXML
    void supplierDeleteAction(ActionEvent event) {

        // check Select from table

        // set filesd

        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) supplierTable.getSelectionModel().getSelectedItem();

        if (recursiveTreeItem == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "خطأ", "اختر مورد للمسح");


        } else {

            SupplierTable supplierTableSelected = (SupplierTable) recursiveTreeItem.getValue();


            // reomve From database

            BasicDBObject basicDBObject = subblier.deleteSupplier(supplierTableSelected.id.get());


            if (basicDBObject != null) {

                // remove from table
                boolean t = supplierTable_Data.remove(supplierTableSelected);
                if (t) {

                    final TreeItem<SupplierTable> rootSupplier = new RecursiveTreeItem<SupplierTable>(supplierTable_Data, RecursiveTreeObject::getChildren);
                    supplierTable.setRoot(rootSupplier);

                }


            }


        }


    }

    @FXML
    void supplierUpdateAction(ActionEvent event) {


        // add Supplier Table
        if (supplierName.getText().trim().isEmpty()
                || supplierAddress.getText().trim().isEmpty()
                || supplierPhone.getText().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "خطأ", "ادخل جميع البيانات");

        } else {

            // set filesd

            SupplierTable supplierTableSelected = supplierTable.getSelectionModel().getSelectedItem().getValue();

            // insert into database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", supplierName.getText());
            basicDBObject.put("address", supplierAddress.getText());
            basicDBObject.put("phone", supplierPhone.getText());


            // problem Method
            BasicDBObject basicDBObjectUpdated = subblier.updateSupplier(supplierTableSelected.id.get(), basicDBObject);
            if (basicDBObjectUpdated != null) {

                // remove row from table
                supplierTable_Data.remove(supplierTableSelected);

                // add new
                // add to table
                supplierTable_Data.add(new SupplierTable(supplierTableSelected.id.get(),
                        supplierName.getText(),
                        supplierAddress.getText(),
                        supplierPhone.getText()));

                final TreeItem<SupplierTable> rootSupplier = new RecursiveTreeItem<SupplierTable>(supplierTable_Data, RecursiveTreeObject::getChildren);
                supplierTable.setRoot(rootSupplier);


                // reset Fields
                this.supplierPhone.setText("");
                this.supplierName.setText("");
                this.supplierAddress.setText("");

                this.supplierAdd.setDisable(false);
                this.supplierUpdate.setDisable(true);


            }


        }


    }


    @FXML
    void supplierAddAction(ActionEvent event) {

        // add Supplier Table
        if (supplierName.getText().trim().isEmpty()
                || supplierAddress.getText().trim().isEmpty()
                || supplierPhone.getText().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "خطأ", "ادخل جميع البيانات");

        } else {

            // insert into database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", supplierName.getText());
            basicDBObject.put("address", supplierAddress.getText());
            basicDBObject.put("phone", supplierPhone.getText());


            BasicDBObject basicDBObject1 = subblier.insertSupplier(basicDBObject);

            if (basicDBObject1 != null) {

                // add to table
                supplierTable_Data.add(new SupplierTable(basicDBObject1.get("_id").toString(),
                        basicDBObject1.get("name").toString(),
                        basicDBObject1.get("address").toString(),
                        basicDBObject1.get("phone").toString()

                ));

                final TreeItem<SupplierTable> rootSupplier = new RecursiveTreeItem<SupplierTable>(supplierTable_Data, RecursiveTreeObject::getChildren);
                supplierTable.setRoot(rootSupplier);


                // reset Fields
                this.supplierPhone.setText("");
                this.supplierName.setText("");
                this.supplierAddress.setText("");


            }


        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        supplierUpdate.setDisable(true);
        update_employee.setDisable(true);


        // initialize table column
        supplierTableName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SupplierTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SupplierTable, String> param) {
                return param.getValue().getValue().name;
            }

        });

        supplierTableAddress.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SupplierTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SupplierTable, String> param) {
                return param.getValue().getValue().address;
            }

        });
        supplierTablePhone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SupplierTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SupplierTable, String> param) {
                return param.getValue().getValue().phone;
            }

        });

       /////////////  initiate employee table //////////////

        employee_name_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTable, String> param) {
                return param.getValue().getValue().name;
            }

        });
        employee_address_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTable, String> param) {
                return param.getValue().getValue().address;
            }

        });

        employee_phone_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EmployeeTable, String> param) {
                return param.getValue().getValue().phone;
            }

        });
        employee_salary_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EmployeeTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<EmployeeTable, Double> param) {
                return param.getValue().getValue().salary.asObject();
            }

        });




        // ***********************  select all database ***********************

        List<DBObject> dbObjects = subblier.selectAllSubbliser();

        for (int i = 0; i < dbObjects.size(); i++) {

            DBObject ee = dbObjects.get(i);
            supplierTable_Data.add(new SupplierTable(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    ee.get("address").toString(),
                    ee.get("phone").toString()));

        }
        // *********************************************************



        // ***********************  select all database ***********************

        List<DBObject> dbObjects2 = employee.selectemployees();

        for (int i = 0; i < dbObjects2.size(); i++) {

            DBObject ee = dbObjects2.get(i);
            EmployeeTable_data.add(new EmployeeTable(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    ee.get("address").toString(),
                    ee.get("phone").toString(),
                    Double.parseDouble(ee.get("salary").toString())));

        }
        // *********************************************************





        final TreeItem<SupplierTable> rootSupplier = new RecursiveTreeItem<SupplierTable>(supplierTable_Data, RecursiveTreeObject::getChildren);
        supplierTable.setRoot(rootSupplier);
        supplierTable.setShowRoot(false);


        // double Click Action Table

        // double click Event
        supplierTable.setRowFactory(tv -> {
            TreeTableRow<SupplierTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    // action Double Click
                    supplierAdd.setDisable(true);
                    supplierUpdate.setDisable(false);

                    // set filesd

                    SupplierTable supplierTableSelected = supplierTable.getSelectionModel().getSelectedItem().getValue();


                    this.supplierName.setText(supplierTableSelected.name.getValue());
                    this.supplierAddress.setText(supplierTableSelected.address.getValue());
                    this.supplierPhone.setText(supplierTableSelected.phone.getValue());


                }
            });
            return row;
        });


    }


    // -------------------- Supplier table ----------------------------


    class SupplierTable extends RecursiveTreeObject<SupplierTable> {

        SimpleStringProperty id;
        SimpleStringProperty name;
        SimpleStringProperty address;
        SimpleStringProperty phone;


        public SupplierTable(String id, String name, String address, String phone) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
            this.phone = new SimpleStringProperty(phone);

        }
    }


    // -------------------- Supplier table ----------------------------


    class EmployeeTable extends RecursiveTreeObject<EmployeeTable>{
        SimpleStringProperty id;
        SimpleStringProperty name;
        SimpleStringProperty address;
        SimpleStringProperty phone;
        SimpleDoubleProperty salary;

      public   EmployeeTable (String id, String name, String address, String phone,double salary){
          this.id = new SimpleStringProperty(id);
          this.name = new SimpleStringProperty(name);
          this.address = new SimpleStringProperty(address);
          this.phone = new SimpleStringProperty(phone);
          this.salary=new SimpleDoubleProperty(salary);
      }
    }



}


