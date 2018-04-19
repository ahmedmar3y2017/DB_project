package container;


import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import dialog.dialog;
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

    ObservableList<SupplierTable> supplierTable_Data = FXCollections.observableArrayList();

    @FXML
    void supplierRefreshAction(ActionEvent event) {

        // reset Fields
        this.supplierPhone.setText("");
        this.supplierName.setText("");
        this.supplierAddress.setText("");

        this.supplierAdd.setDisable(false);
        this.supplierUpdate.setDisable(true);


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


}


