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
import mongo.product;
import mongo.subblier;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class container_controller implements Initializable {


    @FXML
    private Button supplierAdd;


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

    ObservableList<SupplierTable> supplierTable_Data = FXCollections.observableArrayList();
    ObservableList<String> subblier_names = FXCollections.observableArrayList();
    ObservableList<String> employee_names_names = FXCollections.observableArrayList();


    @FXML
    void supplierRefreshAction(ActionEvent event) {

        this.supplierPhone.setText("");
        this.supplierName.setText("");
        this.supplierAddress.setText("");
        this.supplierAdd.setDisable(false);
        this.supplierUpdate.setDisable(true);


    }


    @FXML
    void supplierDeleteAction(ActionEvent event) {

        // check Select from table

        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) supplierTable.getSelectionModel().getSelectedItem();

        if (recursiveTreeItem == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "choose subblier to delete  ");


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
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data  ");

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
    private Button update_employee;

    @FXML
    private TreeTableView<EmployeeTable> employee_table;

    @FXML
    private TreeTableColumn<EmployeeTable, String> employee_name_col;

    @FXML
    private TreeTableColumn<EmployeeTable, String> employee_phone_col;

    @FXML
    private TreeTableColumn<EmployeeTable, String> employee_address_col;

    @FXML
    private TreeTableColumn<EmployeeTable, Double> employee_salary_col;

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
        this.employee_name.setText("");
        this.employee_address.setText("");
        this.employee_phone.setText("");

        this.add_employee.setDisable(false);
        this.update_employee.setDisable(true);

    }

    @FXML
    void remove_employee_action(ActionEvent event) {


        // check Select from table

        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) employee_table.getSelectionModel().getSelectedItem();

        if (recursiveTreeItem == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "choose subblier to delete  ");


        } else {

            EmployeeTable emp_selected = (EmployeeTable) recursiveTreeItem.getValue();


            // reomve From database

            BasicDBObject basicDBObject = employee.deleteemployee(emp_selected.id.get());


            if (basicDBObject != null) {

                // remove from table

                boolean t = EmployeeTable_data.remove(emp_selected);
                if (t) {

                    final TreeItem<EmployeeTable> root_emp = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
                    employee_table.setRoot(root_emp);

                }


            }


        }


    }

    @FXML
    void update_employee_action(ActionEvent event) {
// add emp Table
        if (employee_name.getText().trim().isEmpty()
                || employee_address.getText().trim().isEmpty()
                || employee_phone.getText().trim().isEmpty()
                || employee_salary.getText().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", " enter all data ");

        } else {

            EmployeeTable emp_selected = employee_table.getSelectionModel().getSelectedItem().getValue();

            // insert into database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", employee_name.getText());
            basicDBObject.put("address", employee_address.getText());
            basicDBObject.put("phone", employee_phone.getText());
            basicDBObject.put("salary", employee_salary.getText());


            BasicDBObject b_updated = employee.updateemployee(emp_selected.id.get(), basicDBObject);
            if (b_updated != null) {

                // remove row from table
                EmployeeTable_data.remove(emp_selected);


                // add updated to table
                EmployeeTable_data.add(new EmployeeTable(emp_selected.id.get(),
                        employee_name.getText(),
                        employee_address.getText(),
                        employee_phone.getText(),
                        Double.parseDouble(employee_salary.getText())
                ));

                final TreeItem<EmployeeTable> rootemployee = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
                employee_table.setRoot(rootemployee);


                // reset Fields
                this.employee_name.setText("");
                this.employee_address.setText("");
                this.employee_phone.setText("");

                this.add_employee.setDisable(false);
                this.update_employee.setDisable(true);


            }


        }
    }


    ///////////////////////////////// PRODUCT //////////////////////////////////////

    @FXML
    private Button add_product;

    @FXML
    private Button update_product;

    @FXML
    private TreeTableView<ProductTable> product_table_view;

    @FXML
    private TreeTableColumn<ProductTable, String> product_name_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_sellprice_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_buyprice_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_arrivaldate_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_requestdate_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_subbliername_col;

    @FXML
    private TreeTableColumn<ProductTable, String> product_employeename_col;

    @FXML
    private TextField product_sell_price;

    @FXML
    private TextField product_buy_price;

    @FXML
    private ComboBox<String> product_subblier_name;

    @FXML
    private ComboBox<String> product_employee_name;

    @FXML
    private DatePicker product_arrival_date;

    @FXML
    private DatePicker product_request_date;

    @FXML
    private TextField product_name;

    ObservableList<ProductTable> Product_Table_Data = FXCollections.observableArrayList();


    @FXML
    void refresh_product_action(ActionEvent event) {

    }

    @FXML
    void add_product_action(ActionEvent event) {


        if (product_name.getText().trim().isEmpty()
                || product_buy_price.getText().trim().isEmpty()
                || product_sell_price.getText().trim().isEmpty()
                || product_subblier_name.getSelectionModel().getSelectedItem() == null
                || product_employee_name.getSelectionModel().getSelectedItem() == null
                || product_arrival_date.getValue().equals("")
                || product_request_date.getValue().equals("")

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {


            Date arrival_date = Date.from(product_arrival_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date request_date = Date.from(product_request_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


            // insert into product database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", product_name.getText());
            basicDBObject.put("product_subblier_name", product_subblier_name.getSelectionModel().getSelectedItem());
            basicDBObject.put("product_employee_name", product_employee_name.getSelectionModel().getSelectedItem());
            basicDBObject.put("sellprice", product_sell_price.getText());
            basicDBObject.put("buyprice", product_buy_price.getText());
            basicDBObject.put("arr_date", arrival_date);
            basicDBObject.put("req_date", request_date);


            //

            BasicDBObject basicDBObject2 = product.insertproduct(basicDBObject);


            this.product_name.setText("");
            this.product_employee_name.getEditor().setText("");
            this.product_subblier_name.getEditor().setText("");
            this.product_sell_price.setText("");
            this.product_buy_price.setText("");
            product_request_date.getEditor().clear();
            product_arrival_date.getEditor().clear();


        }
    }


    @FXML
    void update_product_action(ActionEvent event) {

    }

    @FXML
    void remove_product_action(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        supplierUpdate.setDisable(true);
        update_employee.setDisable(true);
        update_product.setDisable(true);
        //
        product_employee_name.setItems(employee_names_names);
        product_subblier_name.setItems(subblier_names);


        // initialize subblier table
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


        // ***********************  select all supplier database ***********************

        List<DBObject> dbObjects = subblier.selectAllSubbliser();

        for (int i = 0; i < dbObjects.size(); i++) {

            DBObject ee = dbObjects.get(i);
            supplierTable_Data.add(new SupplierTable(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    ee.get("address").toString(),
                    ee.get("phone").toString()));

        }
        // *********************************************************


        final TreeItem<SupplierTable> rootSupplier = new RecursiveTreeItem<SupplierTable>(supplierTable_Data, RecursiveTreeObject::getChildren);
        supplierTable.setRoot(rootSupplier);
        supplierTable.setShowRoot(false);

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


        // ***********************  select all supplier database ***********************

        List<DBObject> objects = employee.selectemployees();

        for (int i = 0; i < objects.size(); i++) {

            DBObject ee = objects.get(i);
            EmployeeTable_data.add(new EmployeeTable(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    ee.get("address").toString(),
                    ee.get("phone").toString(),
                    Double.parseDouble(ee.get("salary").toString())));

        }
        // *********************************************************


        final TreeItem<EmployeeTable> rootEmployee = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
        employee_table.setRoot(rootEmployee);
        employee_table.setShowRoot(false);


        // initialize Product table
        product_name_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().name;
            }

        });

        product_sellprice_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().sell_price;
            }

        });
        product_buyprice_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().buy_price;
            }

        });
        product_arrivaldate_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().arr_date;
            }

        });
        product_requestdate_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().req_date;
            }

        });
        product_subbliername_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().p_subbliername;
            }

        });
        product_employeename_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTable, String> param) {
                return param.getValue().getValue().p_employeename;
            }

        });


        // ***********************  select all product database ***********************

        List<DBObject> dbObject_p = product.selectproducts();

        for (int i = 0; i < dbObject_p.size(); i++) {

            DBObject ee = dbObject_p.get(i);

            String arr_date = ee.get("arr_date").toString();
            String req_date = ee.get("req_date").toString();

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

            try {
                Date arriveDate = formatter.parse(arr_date);
                Date reqDate = formatter.parse(req_date);

                Product_Table_Data.add(new ProductTable(ee.get("_id").toString(),
                        ee.get("name").toString(),
                        ee.get("product_subblier_name").toString(),
                        ee.get("product_employee_name").toString(),
                        ee.get("sellprice").toString(),
                        ee.get("buyprice").toString(),
                        formatter.format(arriveDate),
                        formatter.format(reqDate)));
            } catch (ParseException e) {

                e.printStackTrace();
            }


        }
        // *********************************************************


        final TreeItem<ProductTable> rootproduct = new RecursiveTreeItem<ProductTable>(Product_Table_Data, RecursiveTreeObject::getChildren);
        product_table_view.setRoot(rootproduct);
        product_table_view.setShowRoot(false);

        // double Click Action subblier

        supplierTable.setRowFactory(tv -> {
            TreeTableRow<SupplierTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    // action Double Click
                    supplierAdd.setDisable(true);
                    supplierUpdate.setDisable(false);

                    // set fields

                    SupplierTable supplierTableSelected = supplierTable.getSelectionModel().getSelectedItem().getValue();


                    this.supplierName.setText(supplierTableSelected.name.getValue());
                    this.supplierAddress.setText(supplierTableSelected.address.getValue());
                    this.supplierPhone.setText(supplierTableSelected.phone.getValue());


                }
            });
            return row;
        });

        // double click employee table
        employee_table.setRowFactory(tv -> {
            TreeTableRow<EmployeeTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    // action Double Click
                    add_employee.setDisable(true);
                    update_employee.setDisable(false);

                    // set filesd

                    EmployeeTable empselected = employee_table.getSelectionModel().getSelectedItem().getValue();


                    this.employee_name.setText(empselected.name.getValue());
                    this.employee_address.setText(empselected.address.getValue());
                    this.employee_phone.setText(empselected.phone.getValue());
                    this.employee_salary.setText(empselected.phone.getValue());


                }
            });
            return row;
        });


        // ***********************  select name of subblier ***********************

        List<DBObject> dbObjects2 = subblier.selectAllSubbliser();

        for (int i = 0; i < dbObjects2.size(); i++) {

            DBObject obj = dbObjects2.get(i);
            subblier_names.add(obj.get("name").toString());


        }
        System.out.println(subblier_names);
        // *********************************************************


        // ***********************  select name of employee ***********************

        List<DBObject> dbObjects3 = employee.selectemployees();

        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);
            employee_names_names.add(obj.get("name").toString());


        }
        System.out.println(employee_names_names);


    }


    // -------------------- Supplier table class ----------------------------


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


    // -------------------- employee table class ----------------------------


    class EmployeeTable extends RecursiveTreeObject<EmployeeTable> {
        SimpleStringProperty id;
        SimpleStringProperty name;
        SimpleStringProperty address;
        SimpleStringProperty phone;
        SimpleDoubleProperty salary;

        public EmployeeTable(String id, String name, String address, String phone, double salary) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
            this.phone = new SimpleStringProperty(phone);
            this.salary = new SimpleDoubleProperty(salary);
        }
    }


    // -------------------- product table class ----------------------------


    class ProductTable extends RecursiveTreeObject<ProductTable> {
        SimpleStringProperty id;
        SimpleStringProperty name;
        SimpleStringProperty p_subbliername;
        SimpleStringProperty p_employeename;
        SimpleStringProperty sell_price;
        SimpleStringProperty buy_price;
        SimpleStringProperty arr_date;
        SimpleStringProperty req_date;


        public ProductTable(String id, String name, String p_subbliername, String p_employeename, String sellprice, String buyprice, String arr_date, String req_date) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.p_subbliername = new SimpleStringProperty(p_subbliername);
            this.p_employeename = new SimpleStringProperty(p_employeename);
            this.sell_price = new SimpleStringProperty(sellprice);
            this.buy_price = new SimpleStringProperty(buyprice);
            this.arr_date = new SimpleStringProperty(arr_date);
            this.req_date = new SimpleStringProperty(req_date);


        }
    }
}






