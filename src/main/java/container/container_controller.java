package container;


import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.loginController;
import mongo.employee;
import mongo.order;
import mongo.product;
import mongo.subblier;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class container_controller implements Initializable {


    @FXML
    private Label products_name_label;
    @FXML
    private Label employee_name_label;
    @FXML
    private Label subblier_name_label;
    @FXML
    private Label order_name_label;
    @FXML
    public Label search_name_label;


    @FXML
    void sign_out_action_employees(ActionEvent event) {
     System.exit(0);

    }

    @FXML
    void sign_out_action_order(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void sign_out_action_products(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void sign_out_action_search(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void sign_out_action_supplier(ActionEvent event) {
        System.exit(0);
    }


    public static String Username;
    public static String Userid;
    public static String Usertype;
    public static String phone;
    public static String address;


    //////////////////////////////////////////////////////



    ObservableList<SupplierTable> supplierTable_Data = FXCollections.observableArrayList();

    ObservableList<String> subblier_names = FXCollections.observableArrayList();

    ObservableList<EmployeeTable> EmployeeTable_data = FXCollections.observableArrayList();

    ObservableList<String> employee_names_names = FXCollections.observableArrayList();

    ObservableList<ProductTable> Product_Table_Data = FXCollections.observableArrayList();

    ObservableList<String> Product_names = FXCollections.observableArrayList();

    ObservableList<Order_Table> Order_table_data = FXCollections.observableArrayList();

    ObservableList<Search_Table> Search_Table_Data = FXCollections.observableArrayList();

    ObservableList<String> order_names = FXCollections.observableArrayList();

    ArrayList<String> employee_Ids = new ArrayList<>();

    ArrayList<String> subblier_Ids = new ArrayList<>();

    ArrayList<String> p_ids = new ArrayList<>();




    /////////////////////////////////////////// Subblier ////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


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


        if (supplierName.getText().trim().isEmpty()
                || supplierAddress.getText().trim().isEmpty()
                || supplierPhone.getText().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data  ");

        } else {

            // set fields

            SupplierTable supplierTableSelected = supplierTable.getSelectionModel().getSelectedItem().getValue();

            // insert subblier_updated into database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", supplierName.getText());
            basicDBObject.put("address", supplierAddress.getText());
            basicDBObject.put("phone", supplierPhone.getText());


            BasicDBObject basicDBObjectUpdated = subblier.updateSupplier(supplierTableSelected.id.get(), basicDBObject);
            if (basicDBObjectUpdated != null) {

                // remove row from table
                supplierTable_Data.remove(supplierTableSelected);


                // add new to table
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
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

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


                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "error", "  new supplier is added");


                // reset Fields
                this.supplierPhone.setText("");
                this.supplierName.setText("");
                this.supplierAddress.setText("");


            }


        }


    }



    /////////////////////////////////////////// EMPLOYEE ////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


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




    @FXML
    void add_employee_action(ActionEvent event) {


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

            if (basicDBObject2 != null) {

                // add to table
                EmployeeTable_data.add(new EmployeeTable(basicDBObject2.get("_id").toString(),
                        basicDBObject2.get("name").toString(),
                        basicDBObject2.get("address").toString(),
                        basicDBObject2.get("phone").toString(),
                        Double.parseDouble(basicDBObject2.get("salary").toString())

                ));

                final TreeItem<EmployeeTable> rootemp = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
                employee_table.setRoot(rootemp);


                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "Done", "  new employee is added");


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


    ////////////////////////////////////////// PRODUCT /////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

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




    @FXML
    void refresh_product_action(ActionEvent event) {
        this.product_name.setText("");
        this.product_arrival_date.getEditor().setText(null);
        this.product_request_date.getEditor().setText(null);
        this.product_sell_price.setText("");
        this.product_buy_price.setText("");
        this.product_subblier_name.getEditor().setText("");
        this.product_employee_name.getEditor().setText("");


        add_product.setDisable(false);
        update_product.setDisable(true);

    }

    @FXML
    void add_product_action(ActionEvent event) {


        if (product_name.getText().trim().isEmpty()
                || product_buy_price.getText().trim().isEmpty()
                || product_sell_price.getText().trim().isEmpty()
                || product_subblier_name.getSelectionModel().getSelectedItem() == null
                || product_employee_name.getSelectionModel().getSelectedItem() == null
                || product_arrival_date.getValue()==null
                || product_request_date.getValue()==null

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {


            Date arrival_date = Date.from(product_arrival_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date request_date = Date.from(product_request_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            // select Supplier Id From Combobox how ?
            int supplierIndex = product_subblier_name.getSelectionModel().getSelectedIndex();
            String subblierId = subblier_Ids.get(supplierIndex);
            // select Employee Id From Combobox how ?
            int employeeIndex = product_employee_name.getSelectionModel().getSelectedIndex();
            String employeeId = employee_Ids.get(employeeIndex);


            // insert into product database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", product_name.getText());
            basicDBObject.put("product_subblier_id", subblierId);
            basicDBObject.put("product_employee_id", employeeId);
            basicDBObject.put("sellprice", product_sell_price.getText());
            basicDBObject.put("buyprice", product_buy_price.getText());
            basicDBObject.put("arr_date", new SimpleDateFormat("yyyy-MM-dd").format(arrival_date));
            basicDBObject.put("req_date", new SimpleDateFormat("yyyy-MM-dd").format(request_date));


            //

            BasicDBObject basicDBObject2 = product.insertproduct(basicDBObject);


            if (basicDBObject2 != null) {

                // add to table
                Product_Table_Data.add(new ProductTable(basicDBObject2.get("_id").toString(),
                        basicDBObject2.get("name").toString(),
                        basicDBObject2.get("product_subblier_id").toString(),
                        basicDBObject2.get("product_employee_id").toString(),
                        basicDBObject2.get("sellprice").toString(),
                        basicDBObject2.get("buyprice").toString(),
                        basicDBObject2.get("arr_date").toString(),
                        basicDBObject2.get("req_date").toString()));

                final TreeItem<EmployeeTable> rootemp = new RecursiveTreeItem<EmployeeTable>(EmployeeTable_data, RecursiveTreeObject::getChildren);
                employee_table.setRoot(rootemp);
            }


            dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "Done", "  new product is added");


            this.product_name.setText("");
            this.product_employee_name.setValue("");
            this.product_subblier_name.setValue("");
            this.product_sell_price.setText("");
            this.product_buy_price.setText("");
            this.product_request_date.setValue(null);
            this.product_arrival_date.setValue(null);


        }
    }


    @FXML
    void update_product_action(ActionEvent event) {


        if (product_name.getText().trim().isEmpty()
                || product_buy_price.getText().trim().isEmpty()
                || product_sell_price.getText().trim().isEmpty()
                || product_subblier_name.getSelectionModel().getSelectedItem() == null
                || product_employee_name.getSelectionModel().getSelectedItem() == null
                || product_arrival_date.getValue()==null
                || product_request_date.getValue()==null
                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", " enter all data ");

        } else {


            ProductTable produc_selected = product_table_view.getSelectionModel().getSelectedItem().getValue();

            // select Supplier Id From Combobox
            int supplierIndex = product_subblier_name.getSelectionModel().getSelectedIndex();
            String subblierId = subblier_Ids.get(supplierIndex);
            // select Employee Id From Combobox
            int employeeIndex = product_employee_name.getSelectionModel().getSelectedIndex();
            String employeeId = employee_Ids.get(employeeIndex);



            Date arrival_date = Date.from(product_arrival_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date request_date = Date.from(product_request_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


            // insert into product database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", product_name.getText());
            basicDBObject.put("product_subblier_id",subblierId);
            basicDBObject.put("product_employee_id", employeeId);
            basicDBObject.put("sellprice", product_sell_price.getText());
            basicDBObject.put("buyprice", product_buy_price.getText());
            basicDBObject.put("arr_date", new SimpleDateFormat("yyyy-MM-dd").format(arrival_date));
            basicDBObject.put("req_date", new SimpleDateFormat("yyyy-MM-dd").format(request_date));

            BasicDBObject b_updated = product.updateproduct(produc_selected.id.get(), basicDBObject);
            if (b_updated != null) {

                // remove row from table
                Product_Table_Data.remove(produc_selected);


                // add updated to table
                Product_Table_Data.add(new ProductTable(produc_selected.id.get(),
                        product_name.getText(),
                        product_subblier_name.getSelectionModel().getSelectedItem(),
                        product_employee_name.getSelectionModel().getSelectedItem(),
                        product_sell_price.getText(),
                        product_buy_price.getText(),
                        product_arrival_date.getValue().toString(),
                        product_request_date.getValue().toString()
                ));

                final TreeItem<ProductTable> rootproduct = new RecursiveTreeItem<ProductTable>(Product_Table_Data, RecursiveTreeObject::getChildren);
                product_table_view.setRoot(rootproduct);


                // reset Fields
                this.product_name.setText("");
                this.product_employee_name.setValue("");
                this.product_subblier_name.setValue("");
                this.product_sell_price.setText("");
                this.product_buy_price.setText("");
                this.product_request_date.setValue(null);
                this.product_arrival_date.setValue(null);


            }


        }


    }

    @FXML
    void remove_product_action(ActionEvent event) {


        // check Select from table

        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) product_table_view.getSelectionModel().getSelectedItem();

        if (recursiveTreeItem == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "choose product to delete  ");


        } else {

            ProductTable productTableselected = (ProductTable) recursiveTreeItem.getValue();


            // reomve From database

            BasicDBObject basicDBObject = product.deleteproduct(productTableselected.id.get());


            if (basicDBObject != null) {

                // remove from table
                boolean t = Product_Table_Data.remove(productTableselected);
                if (t) {

                    final TreeItem<ProductTable> rootproduct = new RecursiveTreeItem<ProductTable>(Product_Table_Data, RecursiveTreeObject::getChildren);
                    product_table_view.setRoot(rootproduct);

                }


            }


        }


    }


    @FXML
    void product_employee_nameMouseClick(MouseEvent event) {
        List<DBObject> dbObjects3 = employee.selectemployees();
        employee_names_names.clear();
        employee_Ids.clear();
        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);
            employee_names_names.add(obj.get("name").toString());
            employee_Ids.add(obj.get("_id").toString());

        }

    }


    @FXML
    void product_subblier_nameMouseClick(MouseEvent event) {
        List<DBObject> dbObjects2 = subblier.selectAllSubbliser();
        subblier_names.clear();
        subblier_Ids.clear();

        for (int i = 0; i < dbObjects2.size(); i++) {

            DBObject obj = dbObjects2.get(i);
            subblier_names.add(obj.get("name").toString());
            subblier_Ids.add(obj.get("_id").toString());

        }
    }


    /////////////////////////////////////////////// ORDER ////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////




    @FXML
    private ComboBox<String> order_product_name_wanted;

    @FXML
    private TreeTableView<Order_Table> order_table_view;

    @FXML
    private TreeTableColumn<Order_Table, String> order_name_col;

    @FXML
    private TreeTableColumn<Order_Table, Double> order_amount_col;

    @FXML
    private TreeTableColumn<Order_Table, Double> order_cost_col;

    @FXML
    private TreeTableColumn<Order_Table, String> order_date_col;

    @FXML
    void mouse(MouseEvent event) {

        List<DBObject> dbObjects3 = product.selectproducts();
        Product_names.clear();
        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);
            Product_names.add(obj.get("name").toString());


        }
    }

    @FXML
    private Button make_order;

    @FXML
    private Button cancel_order;

    @FXML
    private TextField order_amount_text;

    @FXML
    private DatePicker order_date1;

    @FXML
    private Button remove_order;

    @FXML
    private ComboBox<String> order_product_name_unwanted;


    @FXML
    void make_order_action(ActionEvent event) {


        if (order_amount_text.getText().trim().isEmpty()
                || order_date1.getValue().equals(null)
                || order_product_name_wanted.getSelectionModel().getSelectedItem().trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {


            LocalDate date = order_date1.getValue();
            Date orderDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

            int index = order_product_name_wanted.getSelectionModel().getSelectedIndex();
            String Id = p_ids.get(index);

            DBObject dbObjectRetrieved = product.selectby_productId(Id);
            String price = dbObjectRetrieved.get("buyprice").toString();

            // insert into order database


            BasicDBObject user = new BasicDBObject();
            user.put("id",Userid);
            user.put("name" , Username);
            user.put("phone" , phone);
            user.put("address" ,address);


            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", order_product_name_wanted.getSelectionModel().getSelectedItem());
            basicDBObject.put("amount", order_amount_text.getText());
            basicDBObject.put("cost", Double.parseDouble(price) * Double.parseDouble(order_amount_text.getText()));
            basicDBObject.put("date", new SimpleDateFormat("yyyy-MM-dd").format(orderDate));
            basicDBObject.put("user_data",user);



            BasicDBObject basicDBObject2 = order.insertorder(basicDBObject);


            if (basicDBObject2 != null) {

                // add to table
                Order_table_data.add(new Order_Table(basicDBObject2.get("_id").toString(),
                        basicDBObject2.get("name").toString(),
                        Double.parseDouble(basicDBObject2.get("amount").toString()),
                        Double.parseDouble(basicDBObject2.get("cost").toString()),
                        basicDBObject2.get("date").toString()

                ));

                final TreeItem<Order_Table> rootorder = new RecursiveTreeItem<Order_Table>(Order_table_data, RecursiveTreeObject::getChildren);
                order_table_view.setRoot(rootorder);


                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "error", "  new order is added");


            }


        }


        // reset fielsd
        order_amount_text.setText("");
        order_product_name_wanted.setValue("");
        order_date1.setValue(null);

    }

    @FXML
    void cancel_order_action(ActionEvent event) {

        List<String> ids = new ArrayList<>();

        List<DBObject> db =order.getByUserId(Userid);
        for (int i = 0;i<db.size();i++){
            DBObject object = db.get(i);
            String id = object.get("_id").toString();
            ids.add(id);
        }


        int index = order_product_name_unwanted.getSelectionModel().getSelectedIndex();
        String idd = ids.get(index);

        BasicDBObject basicDBObject =  order.deleteorder(idd);
        order_names.remove(index);

    }

    @FXML
    void remove_order_action(ActionEvent event) {

        // check Select from table

        RecursiveTreeItem recursiveTreeItem = (RecursiveTreeItem) order_table_view.getSelectionModel().getSelectedItem();

        if (recursiveTreeItem == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "choose order to delete  ");


        } else {

            Order_Table order_selected = (Order_Table) recursiveTreeItem.getValue();


            // reomve From database

            BasicDBObject basicDBObject = order.deleteorder(order_selected.order_id.get());


            if (basicDBObject != null) {

                // remove from table

                boolean t = Order_table_data.remove(order_selected);
                if (t) {

                    final TreeItem<Order_Table> root_order = new RecursiveTreeItem<Order_Table>(Order_table_data, RecursiveTreeObject::getChildren);
                    order_table_view.setRoot(root_order);

                }


            }


        }



    }

    @FXML
    void cancel_order_mouse_clicked(MouseEvent event) {

order_names.clear();
       List<DBObject> db =order.getByUserId(Userid);
       if (db==null){
           dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "not found ");
       }
       else {
           for (int i = 0; i < db.size(); i++) {
               DBObject object = db.get(i);
               order_names.add(object.get("name").toString());
           }
       }
    }


    ////////////////////////////////////////////////SEARCH//////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @FXML
    private ComboBox<String> search_name;

    @FXML
    private ComboBox<String> search_employee_name;

    @FXML
    private ComboBox<String> search_subblier_name;


    @FXML
    private DatePicker search_arr_date;

    @FXML
    private DatePicker search_req_date;


    @FXML
    private TreeTableView<Search_Table> search_tableview;

    @FXML
    private TreeTableColumn<Search_Table, String> search_name_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_sell_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_buy_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_emp_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_sup_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_arr_col;

    @FXML
    private TreeTableColumn<Search_Table, String> search_req_col;








    @FXML
    void search_arr_date_action(ActionEvent event) {


        Search_Table_Data.clear();
        String arr_date = search_arr_date.getValue().toString();
        List<DBObject> dbObject_p = product.selectproductby_arrdate(arr_date);


        //
        if (dbObject_p == null) {

            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "not found");


        } else {
            for (int i = 0; i < dbObject_p.size(); i++) {

                DBObject ee = dbObject_p.get(i);


                // select SupplierName
                String subblierId = ee.get("product_subblier_id").toString();

                // select EmployeeName
                String employeeId = ee.get("product_employee_id").toString();


                DBObject subblierObject = subblier.selectSupplierById(subblierId);
                DBObject employeeObject = employee.selectEmployeeById(employeeId);


                Search_Table_Data.add(new Search_Table(ee.get("_id").toString(),
                        ee.get("name").toString(),
                        subblierObject.get("name").toString(),
                        employeeObject.get("name").toString(),
                        ee.get("sellprice").toString(),
                        ee.get("buyprice").toString(),
                        ee.get("arr_date").toString(),
                        ee.get("req_date").toString()));


            }

        }
        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);


    }

    @FXML
    void search_req_date_action(ActionEvent event) {
        Search_Table_Data.clear();
        String req_date = search_req_date.getValue().toString();
        List<DBObject> dbObject_p = product.selectproductby_reqdate(req_date);


        //
        if (dbObject_p == null) {

            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "not found");


        } else {
            for (int i = 0; i < dbObject_p.size(); i++) {

                DBObject ee = dbObject_p.get(i);


                // select SupplierName
                String subblierId = ee.get("product_subblier_id").toString();

                // select EmployeeName
                String employeeId = ee.get("product_employee_id").toString();


                DBObject subblierObject = subblier.selectSupplierById(subblierId);
                DBObject employeeObject = employee.selectEmployeeById(employeeId);


                Search_Table_Data.add(new Search_Table(ee.get("_id").toString(),
                        ee.get("name").toString(),
                        subblierObject.get("name").toString(),
                        employeeObject.get("name").toString(),
                        ee.get("sellprice").toString(),
                        ee.get("buyprice").toString(),
                        ee.get("arr_date").toString(),
                        ee.get("req_date").toString()));


            }

        }
        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);

    }

    @FXML
    void search_by_name_action(ActionEvent event) {
        Search_Table_Data.clear();


        String name = search_name.getSelectionModel().getSelectedItem().toString();
        List<DBObject> dbObject_p = product.selectproduct_byname(name);

        for (int i = 0; i < dbObject_p.size(); i++) {

            DBObject ee = dbObject_p.get(i);


            // select SupplierName
            String subblierId = ee.get("product_subblier_id").toString();

            // select EmployeeName
            String employeeId = ee.get("product_employee_id").toString();


            DBObject subblierObject = subblier.selectSupplierById(subblierId);
            DBObject employeeObject = employee.selectEmployeeById(employeeId);


            Search_Table_Data.add(new Search_Table(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    subblierObject.get("name").toString(),
                    employeeObject.get("name").toString(),
                    ee.get("sellprice").toString(),
                    ee.get("buyprice").toString(),
                    ee.get("arr_date").toString(),
                    ee.get("req_date").toString()));


        }
        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);


    }

    @FXML
    void search_employee_name_action(ActionEvent event) {


        Search_Table_Data.clear();


        List<DBObject> dbObjects3 = employee.selectemployees();
        ArrayList<String> ids = new ArrayList<>();

        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);

            ids.add(obj.get("_id").toString());


        }


        int index = search_employee_name.getSelectionModel().getSelectedIndex();
        String id = ids.get(index);


        ////////////////


        List<DBObject> dbObject_p = product.selectproductby_employeeid(id);


        if (dbObject_p == null) {

            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "not found");


        } else {


            for (int i = 0; i < dbObject_p.size(); i++) {

                DBObject ee = dbObject_p.get(i);


                // select SupplierName
                String subblierId = ee.get("product_subblier_id").toString();

                // select EmployeeName
                String employeeId = ee.get("product_employee_id").toString();


                DBObject subblierObject = subblier.selectSupplierById(subblierId);
                DBObject employeeObject = employee.selectEmployeeById(employeeId);


                Search_Table_Data.add(new Search_Table(ee.get("_id").toString(),
                        ee.get("name").toString(),
                        subblierObject.get("name").toString(),
                        employeeObject.get("name").toString(),
                        ee.get("sellprice").toString(),
                        ee.get("buyprice").toString(),
                        ee.get("arr_date").toString(),
                        ee.get("req_date").toString()));


            }
        }

        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);


    }

    @FXML
    void search_subblier_name_action(ActionEvent event) {

        Search_Table_Data.clear();

        List<DBObject> dbObjects3 = subblier.selectAllSubbliser();
        ArrayList<String> ids = new ArrayList<>();

        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);

            ids.add(obj.get("_id").toString());


        }

        int index = search_subblier_name.getSelectionModel().getSelectedIndex();

        String id = ids.get(index);

        ////////////////


        List<DBObject> dbObject_p = product.selectproductby_subblierid(id);

        System.out.println(dbObject_p);

        if (dbObject_p == null) {

            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "not found");

        } else {

            for (int i = 0; i < dbObject_p.size(); i++) {

                DBObject ee = dbObject_p.get(i);


                // select SupplierName
                String subblierId = ee.get("product_subblier_id").toString();

                // select EmployeeName
                String employeeId = ee.get("product_employee_id").toString();


                DBObject subblierObject = subblier.selectSupplierById(subblierId);
                DBObject employeeObject = employee.selectEmployeeById(employeeId);


                Search_Table_Data.add(new Search_Table(ee.get("_id").toString(),
                        ee.get("name").toString(),
                        subblierObject.get("name").toString(),
                        employeeObject.get("name").toString(),
                        ee.get("sellprice").toString(),
                        ee.get("buyprice").toString(),
                        ee.get("arr_date").toString(),
                        ee.get("req_date").toString()));


            }
        }
        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);


    }


    @FXML
    void search_by_name_mouse_clicked(MouseEvent event) {

        List<DBObject> names = product.selectproducts();
        Product_names.clear();
        for (int i = 0; i < names.size(); i++) {
            DBObject o = names.get(i);
            Product_names.add(o.get("name").toString());
        }


    }


    @FXML
    void search_by_subname_mouse_clicked(MouseEvent event) {

        List<DBObject> names = subblier.selectAllSubbliser();
        subblier_names.clear();
        for (int i = 0; i < names.size(); i++) {
            DBObject o = names.get(i);
            subblier_names.add(o.get("name").toString());
        }

    }

    @FXML
    void search_by_empname_mouse_clicked(MouseEvent event) {

        List<DBObject> names = employee.selectemployees();
        employee_names_names.clear();
        for (int i = 0; i < names.size(); i++) {
            DBObject o = names.get(i);
            employee_names_names.add(o.get("name").toString());
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {


          if(Usertype.equals("user")){
              order_table_view.setDisable(true);
              remove_order.setDisable(true);

          }
          if(Usertype.equals("admin")){
              make_order.setDisable(true);
              cancel_order.setDisable(true);
          }

      //  System.out.println(order.select_order_by_user_id(Userid));
        search_name_label.setText(Username);
        products_name_label.setText(Username);
        employee_name_label.setText(Username);
        subblier_name_label.setText(Username);
        order_name_label.setText(Username);
        // ------------ set text To Labels


        // --------------------------------
        List<DBObject> dbObjects3 = product.selectproducts();
        Product_names.clear();
        for (int i = 0; i < dbObjects3.size(); i++) {

            DBObject obj = dbObjects3.get(i);
            p_ids.add(obj.get("_id").toString());

        }


        supplierUpdate.setDisable(true);
        update_employee.setDisable(true);
        update_product.setDisable(true);
        //
        product_employee_name.setItems(employee_names_names);
        product_subblier_name.setItems(subblier_names);
        order_product_name_wanted.setItems(Product_names);
        search_name.setItems(Product_names);
        search_employee_name.setItems(employee_names_names);
        search_subblier_name.setItems(subblier_names);
        order_product_name_unwanted.setItems(order_names);


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


        // ***********************  select all employee database ***********************

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


            // select SupplierName
            String subblierId = ee.get("product_subblier_id").toString();

            // select EmployeeName
            String employeeId = ee.get("product_employee_id").toString();


            DBObject subblierObject = subblier.selectSupplierById(subblierId);
            DBObject employeeObject = employee.selectEmployeeById(employeeId);


            Product_Table_Data.add(new ProductTable(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    subblierObject.get("name").toString(),
                    employeeObject.get("name").toString(),
                    ee.get("sellprice").toString(),
                    ee.get("buyprice").toString(),
                    ee.get("arr_date").toString(),
                    ee.get("req_date").toString()));


        }

        // *********************************************************


        final TreeItem<ProductTable> rootproduct = new RecursiveTreeItem<ProductTable>(Product_Table_Data, RecursiveTreeObject::getChildren);
        product_table_view.setRoot(rootproduct);
        product_table_view.setShowRoot(false);


        /////////////////////////////////////////// initialize order table //////////////////////////////////////////


        order_name_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Order_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Order_Table, String> param) {
                return param.getValue().getValue().order_name;
            }

        });
        order_amount_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Order_Table, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<Order_Table, Double> param) {
                return param.getValue().getValue().amount.asObject();
            }

        });

        order_cost_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Order_Table, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<Order_Table, Double> param) {
                return param.getValue().getValue().cost.asObject();
            }

        });
        order_date_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Order_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Order_Table, String> param) {
                return param.getValue().getValue().date;
            }

        });


        // ***********************  select all supplier database ***********************

        List<DBObject> dbObj = order.selectorders();
        System.out.println(dbObj);

        for (int i = 0; i < dbObj.size(); i++) {

            DBObject ee = dbObj.get(i);
            Order_table_data.add(new Order_Table(ee.get("_id").toString(),
                    ee.get("name").toString(),
                    Double.parseDouble(ee.get("amount").toString()),
                    Double.parseDouble(ee.get("cost").toString()),
                    ee.get("date").toString()

            ));

        }
        // *********************************************************


        final TreeItem<Order_Table> rootorder = new RecursiveTreeItem<Order_Table>(Order_table_data, RecursiveTreeObject::getChildren);
        order_table_view.setRoot(rootorder);
        order_table_view.setShowRoot(false);


        // ----------------------------------------- search Table ------------------------------------


        search_name_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().name;
            }

        });

        search_sell_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().sell_price;
            }

        });
        search_buy_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().buy_price;
            }

        });
        search_arr_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().arr_date;
            }

        });
        search_req_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().req_date;
            }

        });
        search_sup_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().p_subbliername;
            }

        });

        search_emp_col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Search_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Search_Table, String> param) {
                return param.getValue().getValue().p_employeename;
            }

        });
        final TreeItem<Search_Table> rootsearch = new RecursiveTreeItem<Search_Table>(Search_Table_Data, RecursiveTreeObject::getChildren);
        search_tableview.setRoot(rootsearch);
        search_tableview.setShowRoot(false);


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

        // double Click Action product

        product_table_view.setRowFactory(tv -> {
            TreeTableRow<ProductTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    // action Double Click
                    add_product.setDisable(true);
                    update_product.setDisable(false);


                    ProductTable product_selected = product_table_view.getSelectionModel().getSelectedItem().getValue();


                    this.product_name.setText(product_selected.name.getValue());
                    this.product_buy_price.setText(product_selected.buy_price.getValue());
                    this.product_sell_price.setText(product_selected.sell_price.getValue());



                }
            });
            return row;
        });


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


    class Order_Table extends RecursiveTreeObject<Order_Table> {
        SimpleStringProperty order_id;
        SimpleStringProperty order_name;
        SimpleDoubleProperty amount;
        SimpleDoubleProperty cost;
        SimpleStringProperty date;


        public Order_Table(String id, String name, Double amount, Double cost, String date) {
            this.order_id = new SimpleStringProperty(id);
            this.order_name = new SimpleStringProperty(name);
            this.amount = new SimpleDoubleProperty(amount);
            this.cost = new SimpleDoubleProperty(cost);
            this.date = new SimpleStringProperty(date);


        }

    }


    class Search_Table extends RecursiveTreeObject<Search_Table> {
        SimpleStringProperty id;
        SimpleStringProperty name;
        SimpleStringProperty p_subbliername;
        SimpleStringProperty p_employeename;
        SimpleStringProperty sell_price;
        SimpleStringProperty buy_price;
        SimpleStringProperty arr_date;
        SimpleStringProperty req_date;


        public Search_Table(String id, String name, String p_subbliername, String p_employeename, String sellprice, String buyprice, String arr_date, String req_date) {
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






